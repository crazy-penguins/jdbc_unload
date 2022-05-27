package ewoks.jdbc_unload;

public class Db2iSchemaUnload extends Db2iUnload {
    static final String usage = "Db2iSchemaUnload [username] [password] [host]"
            + " [tableName] [outFile]";

    public Db2iSchemaUnload(String username, String password, String host) {
        super(username, password, host);
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length < 5) {
            System.out.println(Db2iSchemaUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String table = args[3];
        String outFile = args[4];
        Db2iSchemaUnload unload = new Db2iSchemaUnload(username, password, host);
        unload.unloadSchema(table, outFile);
    }
}

