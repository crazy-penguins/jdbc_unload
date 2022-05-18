package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Db2iSchemaUnload extends Unload {
    static final String usage = "Db2iSchemaUnload [username] [password] [host]"
            + " [schema] [tableName] [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length < 5) {
            System.out.println(Db2iSchemaUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String schema = args[3];
        String table = args[4];
        String outFile = args[5];
        Class.forName("com.ibm.as400.access.AS400JDBCDriver");
        //step2 create  the connection object
        String st = String.format("jdbc:as400://%s", host);
        Connection conn = DriverManager.getConnection(st, username, password);
        ResultSet columns = conn.getMetaData().getColumns(null, schema, table, null);
        Db2iSchemaUnload.to_csv(columns, outFile);
        conn.close();
    }
}

