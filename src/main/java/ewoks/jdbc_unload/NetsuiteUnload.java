package ewoks.jdbc_unload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NetsuiteUnload extends Unload {
    static final String usage =
              "JDBC_UNLOAD_USERNAME=username JDBC_UNLOAD_PASSWORD=password"
            + "NetsuiteUnload [account] [roleId] [inFile] [outFile]";
    String account = null;
    String roleId = null;

    public NetsuiteUnload(String username, String password, String account, String roleId) {
        this.username = username;
        this.password = password;
        this.account = account;
        this.roleId = roleId;
        this.dbType = "netsuite";
    }

    @Override
    public String getConnectionString() {
        String host = String.format("%s.connect.api.netsuite.com", account);
        String t = "jdbc:ns://%s:1708;ServerDataSource=NetSuite2.com;encrypted=1;CustomProperties=(AccountID=%s;RoleID=%s);NegotiateSSLClose=false";
        return String.format(t, host, account, roleId);
    }

    public static void main(String[] args) throws Exception {
        // write your code here
        //step1 load the driver class
        if (args.length != 4) {
            System.out.println(usage);
            System.exit(0);
        }
        String username = System.getenv("JDBC_UNLOAD_USERNAME");
        String password = System.getenv("JDBC_UNLOAD_PASSWORD");
        String account = args[0];
        String roleId = args[1];
        String inFile = args[2];
        String outFile = args[3];
        String query = Unload.fileContents(inFile);
        NetsuiteUnload unload = new NetsuiteUnload(username, password, account, roleId);
        unload.unload(query, outFile);
    }
}
