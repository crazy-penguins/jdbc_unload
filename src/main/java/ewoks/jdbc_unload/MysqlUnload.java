package ewoks.jdbc_unload;

public class MysqlUnload extends Unload {
    String databaseName = null;

    public MysqlUnload(String username, String password, String host, String databaseName) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.databaseName = databaseName;
        this.dbType = "mysql";
    }

    @Override
    public String getConnectionString() {
        return String.format("jdbc:mysql://%s/%s", host, databaseName);
    }

    public static void showUsage() {
        System.out.println("runs a query stored in inFile against the mysql ");
        System.out.println("  host and database");
        System.out.println("  and stores the resulting csv / gzipped csv output");
        System.out.println("  in outFile");
        System.out.println(
              "usage: java ewoks.jdbc_unload.MysqlUnload "
            + "[host] [databaseName] [inFile] [outFile]");
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            MysqlUnload.showUsage();
            System.exit(0);
        }
        String username = System.getenv("JDBC_UNLOAD_USERNAME");
        String password = System.getenv("JDBC_UNLOAD_PASSWORD");
        String host = args[0];
        String databaseName = args[1];
        String inFile = args[2];
        String outFile = args[3];
        MysqlUnload unload = new MysqlUnload(username, password, host, databaseName);
        String query = Unload.fileContents(inFile);
        unload.unload(query, outFile);
    }
}
