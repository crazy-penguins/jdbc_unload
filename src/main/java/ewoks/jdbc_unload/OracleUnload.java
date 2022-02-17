package ewoks.jdbc_unload;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class OracleUnload extends Unload {
    static final String usage = "Unload [username] [password] [host]"
            + " [sid] [inFile] [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length != 6) {
            System.out.println(usage);
            System.exit(1);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String sid = args[3];
        String inFile = args[4];
        String outFile = args[5];
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //step2 create  the connection object
        String st = String.format("jdbc:oracle:thin:@%s:1521/%s", host, sid);
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        props.setProperty("oracle.jdbc.timezoneAsRegion", "false");
        Connection conn = DriverManager.getConnection(st, props);
        String query = Files.readString(Path.of(inFile));
        OracleUnload.unload(conn, query, outFile);
        conn.close();
    }
}
