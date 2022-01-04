package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;

public class OracleTableUnload extends Unload {
    static final String usage = "OracleTableUnload [username] [password] [host]"
            + " [sid] [table] [outFile]";

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
        String table = args[4];
        String outFile = args[5];
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //step2 create  the connection object
        String st = String.format("jdbc:oracle:thin:@%s:1521/%s", host, sid);
        Connection conn = DriverManager.getConnection(st, username, password);
        String query = String.format("select * from %s", table);
        OracleTableUnload.unload(conn, query, outFile);
        conn.close();
    }
}
