package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class OracleSchemaUnload extends OracleUnload {
    static final String usage = "OracleSchemaUnload [username] [password] [host]"
            + " [sid] [table] [outFile]";

    public OracleSchemaUnload(String username, String password, String host, String sid) {
        super(username, password, host, sid);
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        // step1 load the driver class
        if (args.length < 5) {
            System.out.println(OracleSchemaUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String sid = args[3];
        String table = args[5].toUpperCase();
        String outFile = args[6];
        OracleSchemaUnload unload = new OracleSchemaUnload(username, password, host, sid);
        unload.unloadSchema(table, outFile);
    }
}
