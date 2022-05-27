package ewoks.jdbc_unload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public class VerticaUnload extends Unload {
    static final String usage = "VerticaUnload [username] [password] [host]"
            + " [databaseName] [inFile] [outFile]";

    String databaseName;

    public VerticaUnload(String username, String password, String host, String databaseName) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.databaseName = databaseName;
        this.dbType = "vertica";
    }

    @Override
    public String getConnectionString() {
        return String.format("jdbc:vertica://%s:5433/%s", host, databaseName);
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length != 6) {
            System.out.println(usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String databaseName = args[3];
        String inFile = args[4];
        String outFile = args[5];
        String query = Unload.fileContents(inFile);
        VerticaUnload unload = new VerticaUnload(username, password, host, databaseName);
        unload.unload(query, outFile);
    }
}
