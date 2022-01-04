package ewoks.jdbc_unload;
import com.opencsv.CSVWriter;
import com.opencsv.ResultSetHelperService;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class Unload {
    static final String usage = "Unload [username] [password] [connectionString]"
            + " [inFile] [outFile]";
    public static void main(String[] args) throws Exception {

        if (args.length < 5) {
            System.out.println(Unload.usage);
            System.exit(0);
        }
        // write your code here
        //step1 load the driver class
        String username = args[0];
        String password = args[1];
        String connectionString = args[2];
        String inFile = args[3];
        String outFile = args[4];
        String className = null;
        if (connectionString.contains("oracle")) {
            className = "oracle.jdbc.driver.OracleDriver";
        }
        Class.forName(className);
        String query = Files.readString(Path.of(inFile));

        //step2 create  the connection object
        Connection conn;
        conn = DriverManager.getConnection(connectionString, username, password);
        Unload.unload(conn, query, outFile);
        conn.close();
    }

    public static void unload(Connection conn, String query, String outFile) throws SQLException, IOException, Exception {
        //step3 create the statement object
        Statement stmt = conn.createStatement();

        //step4 execute query
        ResultSet rs;
        rs = stmt.executeQuery(query);
        //step5 close the connection object
        boolean async = true;
        try (CSVWriter writer = new CSVWriter(outFile)) {
            //Define fetch size(default as 30000 rows), higher to be faster performance but takes more memory
            ResultSetHelperService.RESULT_FETCH_SIZE = 50000;
            //Define MAX extract rows, -1 means unlimited.
            ResultSetHelperService.MAX_FETCH_ROWS = -1;
            writer.setAsyncMode(async);
            int result = writer.writeAll(rs, true);
            //return result - 1;
            System.out.println("Result: " + (result - 1));
        }
        conn.close();
    }
}
