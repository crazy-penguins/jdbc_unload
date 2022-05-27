package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class OracleTableUnload extends OracleSchemaUnload {
    static final String usage = "OracleTableUnload [username] [password] [host]"
            + " [sid] [table] [outFile] [where]";

    public OracleTableUnload(String username, String password, String host, String sid) {
        super(username, password, host, sid);
    }

    public static String constructQuery(String schema, String table, ArrayList<ColumnInfo> columns, String where) {
        StringBuilder query = new StringBuilder("select ");
        int n = 0;
        for (ColumnInfo x: columns) {
            n += 1;
            if (n > 1) {
                query.append("\n  , ");
            }
            switch (x.dataType) {
                case "datetime": {
                    query.append("to_char(cast(");
                    query.append(x.name);
                    query.append(" as timestamp) at time zone 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')");
                }
                break;
                case "timestamp(6)": {
                    query.append("to_char(");
                    query.append(x.name);
                    query.append(" at time zone 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')");
                }
                break;
                case "number": {
                    query.append("to_char(");
                    query.append(x.name);
                    query.append(", 'FM");
                    query.append("9".repeat(x.digits - x.decimalPlaces - 1));
                    query.append("0");
                    if (x.decimalPlaces > 0) {
                        query.append('.');
                        query.append("0".repeat(x.decimalPlaces));
                    }
                    query.append("')");
                }
                break;
                default: {
                    query.append(x.name);
                }
            }
            query.append(" as \"");
            query.append(x.name);
            query.append('"');
        }
        query.append("\nfrom ");
        if (schema != null) {
            query.append(schema.toLowerCase());
            query.append(".");
        }
        query.append(table.toLowerCase());
        if (where != null) {
            query.append('\n');
            query.append(where);
        }
        return query.toString();
    }

    @Override
    public void unloadTable(String table, String where, String outFile) throws Exception {
        String schema = null, query = null;
        if (table.contains(".")) {
            String[] pieces = table.split("\\.", 2);
            schema = pieces[0];
            table = pieces[1];
        }
        HashMap<String, ColumnInfo> columns = new HashMap<>();
        ArrayList<ColumnInfo> rgColumns = new ArrayList<>();
        try (Connection conn = getConnection()) {
            try (ResultSet rs = conn.getMetaData().getColumns(null, schema, table, null)) {
                while (rs.next()) {
                    ColumnInfo x = new ColumnInfo(rs);
                    columns.put(x.name, x);
                    rgColumns.add(x);
                }
            }
            query = OracleTableUnload.constructQuery(schema, table, rgColumns, where);
            System.out.println(query);
        }
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length != 6 && args.length != 7) {
            System.out.println(OracleTableUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String sid = args[3];
        String table = args[4].toUpperCase();
        String outFile = args[5];
        String where = null;
        if (args.length == 7) {
            where = args[6];
        }
        OracleTableUnload unload = new OracleTableUnload(username, password, host, sid);
        unload.unloadTable(table, where, outFile);
    }
}
