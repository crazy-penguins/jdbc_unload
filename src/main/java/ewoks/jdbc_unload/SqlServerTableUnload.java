package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServerTableUnload extends Unload {
    static final String usage = "SqlServerTableUnload [username] [password] [host]"
            + " [databaseName] [table] [outFile]";

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length < 6) {
            System.out.println(SqlServerTableUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String databaseName = args[3];
        String table = args[4];
        String outFile = args[5];
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //step2 create  the connection object
        String st = String.format(
            "jdbc:sqlserver://%s:1433;databaseName=%s",
            host, databaseName);
        Connection conn = DriverManager.getConnection(st, username, password);
        String query = String.format("select * from %s", table);
        SqlServerTableUnload.unload(conn, query, outFile);
        conn.close();
    }
}
