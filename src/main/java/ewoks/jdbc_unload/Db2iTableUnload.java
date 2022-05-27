package ewoks.jdbc_unload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public class Db2iTableUnload extends Db2iUnload {
    static final String usage = "Db2iTableUnload [username] [password] [host]"
            + " [tableName] [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length < 5) {
            System.out.println(Db2iTableUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String table = args[3];
        String outFile = args[4];
        Class.forName("com.ibm.as400.access.AS400JDBCDriver");
        //step2 create  the connection object
        String query = String.format("select * from %s", table);
        try (Connection conn = Db2iTableUnload.connect(host, username, password)) {
            Db2iTableUnload.unload(conn, query, outFile);
        }
    }
}

