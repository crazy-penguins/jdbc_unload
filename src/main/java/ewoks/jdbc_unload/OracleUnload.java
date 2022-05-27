package ewoks.jdbc_unload;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleUnload extends Unload {
    static final String usage = "Unload [username] [password] [host]"
            + " [sid] [inFile] [outFile]";
    String sid = null;

    public OracleUnload(String username, String password, String host, String sid) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.dbType = "oracle";
    }

    @Override
    public String getConnectionString() {
        return String.format("jdbc:oracle:thin:@%s:1521/%s", host, sid);
    }

    @Override
    public Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        props.setProperty("oracle.jdbc.timezoneAsRegion", "false");
        return props;
    }

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
        String query = Unload.fileContents(inFile);
        OracleUnload unload = new OracleUnload(username, password, host, sid);
        unload.unload(query, outFile);
    }
}
