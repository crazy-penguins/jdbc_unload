package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.ResultSet;

public class MysqlTableUnload extends MysqlUnload {
    static final String usage = "Db2iTableUnload [username] [password] [host]"
            + " [tableName] [outFile] [where]";


    public MysqlTableUnload(String username, String password, String host, String databaseName) {
        super(username, password, host, databaseName);
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length != 6 && args.length != 7) {
            System.out.println(MysqlTableUnload.usage);
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
        MysqlTableUnload unload = new MysqlTableUnload(username, password, host, databaseName);
        unload.unloadTable(table, where, outFile);
    }
}
