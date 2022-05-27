package ewoks.jdbc_unload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Db2iUnload extends Unload {
    static final String usage = "Db2iUnload [username] [password] [host]"
            + " [inFile] [outFile]";

    public Db2iUnload(String username, String password, String host) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.defaultFetchSize = 1000;
    }

    @Override
    public String getConnectionString() {
        return String.format("jdbc:as400://%s", host);
    }

    public static Connection connect(String host, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.ibm.as400.access.AS400JDBCDriver");
        String st = String.format("jdbc:as400://%s", host);
        return DriverManager.getConnection(st, username, password);
    }

    public static int to_csv(ResultSet rs, String outFile) throws SQLException, IOException, Exception {
        return to_csv(rs, outFile, 1000);
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length < 5) {
            System.out.println(Db2iUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String inFile = args[3];
        String outFile = args[4];
        String query = Unload.fileContents(inFile);
        Db2iUnload unload = new Db2iUnload(username, password, host);
        unload.unload(query, outFile);
    }
}

