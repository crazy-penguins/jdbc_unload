package ewoks.jdbc_unload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db2iUnload extends Unload {
    static final String usage = "Db2iUnload [username] [password] [host]"
            + " [inFile] [outFile]";

    public static Connection connect(String host, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.ibm.as400.access.AS400JDBCDriver");
        String st = String.format("jdbc:as400://%s", host);
        return DriverManager.getConnection(st, username, password);
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
        String query = Files.readString(Path.of(inFile));
        try (Connection conn = Db2iUnload.connect(host, username, password)) {
            Db2iUnload.unload(conn, query, outFile);
        }
    }
}

