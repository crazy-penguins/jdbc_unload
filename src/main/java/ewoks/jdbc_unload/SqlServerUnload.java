package ewoks.jdbc_unload;

import java.util.Properties;

public class SqlServerUnload extends Unload {
    static final String usage = "SqlServerUnload [username] [password] [host]"
            + " [databaseName] [inFile] [outFile]";
    String databaseName;

    public SqlServerUnload(String username, String password, String host, String databaseName) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.databaseName = databaseName;
        this.dbType = "mssql";
    }

    @Override
    public Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        props.setProperty("databaseName", databaseName);
        return props;
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String databaseName = args[3];
        String inFile = args[4];
        String outFile = args[5];
        //step2 create  the connection object
        String query = Unload.fileContents(inFile);
        SqlServerUnload unload = new SqlServerUnload(username, password, host, databaseName);
        unload.unload(query, outFile);
    }
}
