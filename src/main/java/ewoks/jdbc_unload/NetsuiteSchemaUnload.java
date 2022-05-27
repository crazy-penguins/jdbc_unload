package ewoks.jdbc_unload;

public class NetsuiteSchemaUnload extends NetsuiteUnload {
    static final String usage =
              "JDBC_UNLOAD_USERNAME=username JDBC_UNLOAD_PASSWORD=password"
            + "NetsuiteSchemaUnload [account] [roleId] table [outFile]";

    public NetsuiteSchemaUnload(String username, String password, String account, String roleId) {
        super(username, password, account, roleId);
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        // step1 load the driver class
        if (args.length != 4) {
            System.out.println(NetsuiteSchemaUnload.usage);
            System.exit(0);
        }
        String username = System.getenv("JDBC_UNLOAD_USERNAME");
        String password = System.getenv("JDBC_UNLOAD_PASSWORD");
        String account = args[0];
        String roleId = args[1];
        String table = args[2];
        String outFile = args[3];
        NetsuiteSchemaUnload unload = new NetsuiteSchemaUnload(username, password, account, roleId);
        unload.unloadSchema(table, outFile);
    }
}
