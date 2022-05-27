package ewoks.jdbc_unload;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NetsuiteTableUnload extends NetsuiteUnload {
    static final String usage =
              "JDBC_UNLOAD_USERNAME=username JDBC_UNLOAD_PASSWORD=password"
            + "NetsuiteTableUnload [account] [roleId] table [outFile]";

    public NetsuiteTableUnload(String username, String password, String account, String roleId) {
        super(username, password, account, roleId);
    }

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
        NetsuiteTableUnload unload = new NetsuiteTableUnload(username, password, account, roleId);
        unload.unloadTable(table, where, outFile);
    }
}
