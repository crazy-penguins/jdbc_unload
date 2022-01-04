package ewoks.jdbc_unload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public class OracleUnload extends Unload {
    static final String usage = "Unload [username] [password] [host]"
            + " [sid] [inFile] [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String sid = args[3];
        String inFile = args[4];
        String outFile = args[5];
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //step2 create  the connection object
        String st = String.format("jdbc:oracle:thin:@%s:1521/%s", host, sid);
        Connection conn = DriverManager.getConnection(st, username, password);
        String query = Files.readString(Path.of(inFile));
        OracleUnload.unload(conn, query, outFile);
        conn.close();
    }
}
