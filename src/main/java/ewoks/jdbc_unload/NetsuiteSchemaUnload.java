package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.ResultSet;


public class NetsuiteSchemaUnload extends NetsuiteUnload {
    static final String usage =
              "JDBC_UNLOAD_USERNAME=username JDBC_UNLOAD_PASSWORD=password"
            + "NetsuiteSchemaUnload [account] [roleId] table [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        // step1 load the driver class
        if (args.length != 4) {
            System.out.println(NetsuiteSchemaUnload.usage);
            System.exit(0);
        }
        String username = System.getenv("JDBC_UNLOAD_USERNAME");
        String password = System.getenv("JDBC_UNLOAD_PASSWORD");
        String account = args[0];
        String roleId = args[1];
        String table = args[2];
        String outFile = args[3];
        try (Connection conn = NetsuiteSchemaUnload.connect(username, password, account, roleId)) {
            try (ResultSet columns = conn.getMetaData().getColumns(null, null, table, null)) {
                NetsuiteSchemaUnload.to_csv(columns, outFile);
            }
        }
    }
}
