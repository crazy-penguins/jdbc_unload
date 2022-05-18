package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class OracleSchemaUnload extends Unload {
    static final String usage = "OracleSchemaUnload [username] [password] [host]"
            + " [sid] [schema] [table] [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        // step1 load the driver class
        if (args.length < 6) {
            System.out.println(OracleSchemaUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String sid = args[3];
        String schema = args[4].toUpperCase();
        String table = args[5].toUpperCase();
        String outFile = args[6];
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // step2 create  the connection object
        String st = String.format("jdbc:oracle:thin:@%s:1521/%s", host, sid);
        Connection conn = DriverManager.getConnection(st, username, password);
        ResultSet columns = conn.getMetaData().getColumns(null, schema, table, null);
        OracleSchemaUnload.to_csv(columns, outFile);
        conn.close();
    }
}
