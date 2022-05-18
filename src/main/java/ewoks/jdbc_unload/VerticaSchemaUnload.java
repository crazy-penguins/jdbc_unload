package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class VerticaSchemaUnload extends Unload {
    static final String usage = "VerticaSchemaUnload [username] [password] [host]"
            + " [databaseName] [schema] [table] [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length != 7) {
            System.out.println(VerticaSchemaUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String databaseName = args[3];
        String schema = args[4];
        String table = args[5];
        String outFile = args[6];
        Class.forName("com.vertica.jdbc.Driver");
        //step2 create  the connection object
        String st = String.format("jdbc:vertica://%s:5433/%s", host, databaseName);
        Connection conn = DriverManager.getConnection(st, username, password);
        ResultSet columns = conn.getMetaData().getColumns(null, schema, table, null);
        VerticaSchemaUnload.to_csv(columns, outFile);
        conn.close();
    }
}
