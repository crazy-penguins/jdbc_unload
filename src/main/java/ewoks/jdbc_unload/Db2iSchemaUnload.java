package ewoks.jdbc_unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Db2iSchemaUnload extends Db2iUnload {
    static final String usage = "Db2iSchemaUnload [username] [password] [host]"
            + " [schema] [tableName] [outFile]";

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
        String schema = args[3];
        String table = args[4];
        String outFile = args[5];
        try (Connection conn = Db2iSchemaUnload.connect(host, username, password)) {
            try (ResultSet columns = conn.getMetaData().getColumns(null, schema, table, null)) {
                Db2iSchemaUnload.to_csv(columns, outFile);
            }
        }
    }
}

