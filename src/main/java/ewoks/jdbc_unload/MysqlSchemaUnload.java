package ewoks.jdbc_unload;

public class MysqlSchemaUnload extends MysqlUnload {
    public MysqlSchemaUnload(String username, String password, String host, String databaseName) {
        super(username, password, host, databaseName);
    }

    public static void showUsage() {
        System.out.println("gets a csv with the table schema from the provided");
        System.out.println("  host and database");
        System.out.println("  and stores the resulting output");
        System.out.println("  in outFile");
        System.out.println(
              "usage: java ewoks.jdbc_unload.MysqlSchemaUnload "
            + "[host] [databaseName] [table] [outFile]");
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            MysqlSchemaUnload.showUsage();
            System.exit(0);
        }
        String username = System.getenv("JDBC_UNLOAD_USERNAME");
        String password = System.getenv("JDBC_UNLOAD_PASSWORD");
        String host = args[0];
        String databaseName = args[1];
        String table = args[2];
        String outFile = args[3];
        MysqlSchemaUnload unload = new MysqlSchemaUnload(username, password, host, databaseName);
        unload.unloadSchema(table, outFile);
    }
}
