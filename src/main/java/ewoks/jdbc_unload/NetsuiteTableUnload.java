package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.ResultSet;

public class NetsuiteTableUnload extends NetsuiteUnload {
    static final String usage =
              "JDBC_UNLOAD_USERNAME=username JDBC_UNLOAD_PASSWORD=password"
            + "NetsuiteTableUnload [account] [roleId] table [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        // step1 load the driver class
        if (args.length != 4 && args.length != 5) {
            System.out.printf("args.length: %s%n", args.length);
            System.out.println(NetsuiteSchemaUnload.usage);
            System.exit(0);
        }
        String username = System.getenv("JDBC_UNLOAD_USERNAME");
        String password = System.getenv("JDBC_UNLOAD_PASSWORD");
        String account = args[0];
        String roleId = args[1];
        String table = args[2];
        String outFile = args[3];
        String where = null;
        if (args.length == 5) {
            where = args[4];
        }
        try (Connection conn = NetsuiteTableUnload.connect(username, password, account, roleId)) {
            String query = null;
            try (ResultSet columns = conn.getMetaData().getColumns(null, null, table, null)) {
                query = TableSelect.query(table, columns, where);
                System.out.println(query);
            }
            NetsuiteUnload.unload(conn, query, outFile);
        }
    }
}
