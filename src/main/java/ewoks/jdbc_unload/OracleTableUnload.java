package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class OracleTableUnload extends Unload {
    static final String usage = "OracleTableUnload [username] [password] [host]"
            + " [sid] [table] [outFile]";

    public static String constructQuery(String schema, String table, ArrayList<ColumnInfo> columns) {
        StringBuffer query = new StringBuffer("select ");
        int n = 0;
        for (ColumnInfo x: columns) {
            n += 1;
            if (n > 1) {
                query.append("\n  , ");
            }
            switch (x.dataType) {
                case "datetime":
                    query.append("to_char(cast(");
                    query.append(x.name);
                    query.append(" as timestamp) at time zone 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')");
                    break;
                case "timestamp(6)":
                    query.append("to_char(");
                    query.append(x.name);
                    query.append(" at time zone 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')");
                    break;
                case "number":
                    query.append("to_char(");
                    query.append(x.name);
                    query.append(", 'FM");
                    for (int i = 0; i < x.digits - x.decimalPlaces - 1; ++i) {
                        query.append('9');
                    }
                    query.append("0");
                    if (x.decimalPlaces > 0) {
                        query.append('.');
                        for (int i = 0; i < x.decimalPlaces; ++i) {
                            query.append('0');
                        }
                    }
                    query.append("')");
                    break;
                default:
                    query.append(x.name);
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
        return query.toString();
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length < 6) {
            System.out.println(OracleTableUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String sid = args[3];
        String schema = null;
        String table = args[4].toUpperCase();
        String outFile = args[5];
        if (table.indexOf('.') != -1) {
            String[] pieces = table.split("\\.", 2);
            schema = pieces[0];
            table = pieces[1];
        }
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //step2 create  the connection object
        String st = String.format("jdbc:oracle:thin:@%s:1521/%s", host, sid);
        Connection conn = DriverManager.getConnection(st, username, password);
        ResultSet rs = conn.getMetaData().getColumns(null, schema, table, null);
        HashMap<String, ColumnInfo> columns = new HashMap<String, ColumnInfo>();
        ArrayList<ColumnInfo> rgColumns = new ArrayList<ColumnInfo>();
        while (rs.next()) {
            ColumnInfo x = new ColumnInfo(rs);
            columns.put(x.name, x);
            rgColumns.add(x);
        }
        String query = OracleTableUnload.constructQuery(schema, table, rgColumns);
        System.out.println(query);
        conn.close();
    }
}
