package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class VerticaSchemaUnload extends VerticaUnload {
    static final String usage = "VerticaSchemaUnload [username] [password] [host]"
            + " [databaseName] [schema] [table] [outFile]";

    public VerticaSchemaUnload(String username, String password, String host, String databaseName) {
        super(username, password, host, databaseName);
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length != 6) {
            System.out.println(VerticaSchemaUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String databaseName = args[3];
        String table = args[4];
        String outFile = args[5];
        VerticaSchemaUnload unload = new VerticaSchemaUnload(username, password, host, databaseName);
        unload.unloadSchema(table, outFile);
    }
}
