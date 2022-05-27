package ewoks.jdbc_unload;
import com.opencsv.CSVWriter;
import com.opencsv.ResultSetHelperService;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Unload {
    static final String usage = "Unload [username] [password] [connectionString]"
            + " [inFile] [outFile]";
    String username = null;
    String password = null;
    String host = null;
    String dbType = null;
    int defaultFetchSize = 50000;

    public String driverClass() {
        switch (dbType) {
            case "oracle": return "oracle.jdbc.driver.OracleDriver";
            case "db2i": return "com.ibm.as400.access.AS400JDBCDriver";
            case "mssql": return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            case "netsuite": return "com.netsuite.jdbc.openaccess.OpenAccessDriver";
            case "vertica": return "com.vertica.jdbc.Driver";
            case "mysql": return "com.mysql.jdbc.Driver";
        }
        return null;
    }

    public void loadDriverClass() throws ClassNotFoundException {
        String className = driverClass();
        if (className != null) {
            System.out.printf("loading %s\n", className);
            Class.forName(className);
        }
    }

    public String getConnectionString() {
        return null;
    }

    public Properties getProperties() {
        return null;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        loadDriverClass();
        String st = getConnectionString();
        System.out.println("connecting to database");
        Properties props = getProperties();
        if (props == null) {
            return DriverManager.getConnection(st, username, password);
        }
        return DriverManager.getConnection(st, props);
    }

    public static String fileContents(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }

    public void unload(String query, String outFile, int fetchSize) throws SQLException, IOException, Exception {
        try (Connection conn = getConnection()) {
            System.out.println("unloading");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Unload.to_csv(rs, outFile, fetchSize);
        }
    }

    public void unload(String query, String outFile) throws Exception {
        unload(query, outFile, defaultFetchSize);
    }

    public void unloadSchema(String table, String outFile) throws Exception {
        String catalog = null, schema = null;
        if (table.contains(".")) {
            String[] pieces = table.split("\\.", 3);
            if (pieces.length == 3) {
                catalog = pieces[0];
                schema = pieces[1];
                table = pieces[2];
            }
            else if (pieces.length == 2) {
                schema = pieces[0];
                table = pieces[1];
            }
        }
        try (Connection conn = getConnection()) {
            System.out.println("unloading schema");
            try (ResultSet columns = conn.getMetaData().getColumns(catalog, schema, table, null)) {
                Unload.to_csv(columns, outFile);
            }
        }
    }

    public ArrayList<ColumnInfo> getColumns(Connection conn, String table) throws Exception {
        String catalog = null, schema = null;
        if (table.contains(".")) {
            String[] pieces = table.split("\\.", 3);
            if (pieces.length == 3) {
                catalog = pieces[0];
                schema = pieces[1];
                table = pieces[2];
            }
            else if (pieces.length == 2) {
                schema = pieces[0];
                table = pieces[1];
            }
        }
        System.out.println("grabbing schema");
        try (ResultSet columns = conn.getMetaData().getColumns(catalog, schema, table, null)) {
            ArrayList<ColumnInfo> result = new ArrayList<>();
            while (columns.next()) {
                result.add(new ColumnInfo(columns));
            }
            return result;
        }
    }

    public void unloadTable(String table, String where, String outFile) throws Exception {
        try (Connection conn = getConnection()) {
            ArrayList<ColumnInfo> columns = getColumns(conn, table);
            String query = TableSelect.query(table, columns, where);
            System.out.println(query);
            unload(conn, query, outFile);
        }
    }

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
        int result = Unload.to_csv(rs, outFile);
        System.out.println("Result: " + (result - 1));
        //step5 close the connection object
        conn.close();
    }

    public static int to_csv(ResultSet rs, String outFile) throws SQLException, IOException, Exception {
        return to_csv(rs, outFile, 50000);
    }

    public static int to_csv(ResultSet rs, String outFile, int fetchSize) throws SQLException, IOException, Exception {
        boolean async = true;
        try (CSVWriter writer = new CSVWriter(outFile)) {
            //Define fetch size(default as 30000 rows), higher to be faster performance but takes more memory
            ResultSetHelperService.RESULT_FETCH_SIZE = fetchSize;
            //Define MAX extract rows, -1 means unlimited.
            ResultSetHelperService.MAX_FETCH_ROWS = -1;
            writer.setAsyncMode(async);
            return writer.writeAll(rs, true);
        }
    }
}
