package ewoks.jdbc_unload;


public class SqlServerTableUnload extends SqlServerUnload {
    static final String usage = "SqlServerTableUnload [username] [password] [host]"
            + " [databaseName] [table] [outFile]";

    public SqlServerTableUnload(String username, String password, String host, String databaseName) {
        super(username, password, host, databaseName);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 6 && args.length != 7) {
            System.out.println(SqlServerTableUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String databaseName = args[3];
        String table = args[4];
        String outFile = args[5];
        String where = null;
        if (args.length == 7) {
            where = args[6];
        }
        SqlServerTableUnload unload = new SqlServerTableUnload(username, password, host, databaseName);
        unload.unloadTable(table, where, outFile);
    }
}
