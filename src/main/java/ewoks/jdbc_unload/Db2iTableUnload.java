package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.ResultSet;

public class Db2iTableUnload extends Db2iUnload {
    static final String usage = "Db2iTableUnload [username] [password] [host]"
            + " [tableName] [outFile] [where]";


    public Db2iTableUnload(String username, String password, String host) {
        super(username, password, host);
    }


    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length != 5 && args.length != 6) {
            System.out.println(Db2iTableUnload.usage);
            System.exit(0);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        String table = args[3];
        String where = null;
        String outFile = args[4];
        if (args.length == 6) {
            where = args[5];
        }
        Db2iTableUnload unload = new Db2iTableUnload(username, password, host);
        unload.unloadTable(table, where, outFile);
    }
}

