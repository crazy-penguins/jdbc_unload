package ewoks.jdbc_unload;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class TableSelect {
    public static String query(String table, ResultSet columns) throws SQLException {
        return TableSelect.query(table, columns, null);
    }

    public static String buildSelect(String table, ArrayList<String> fields, String where) {
        Collections.sort(fields);
        StringBuilder buff = new StringBuilder("select ");
        String field_list = String.join("\n  , ", fields);
        buff.append(field_list);
        buff.append("\nfrom ");
        buff.append(table);
        if (where != null) {
            buff.append("\n");
            buff.append(where);
        }
        return buff.toString();
    }

    public static String query(String table, ResultSet columns, String where) throws SQLException {
        ArrayList<String> fields = new ArrayList<>();
        while (columns.next()) {
            fields.add(columns.getString("COLUMN_NAME"));
        }
        return TableSelect.buildSelect(table, fields, where);
    }


    public static String query(String table, ArrayList<ColumnInfo> columns, String where) throws SQLException {
        ArrayList<String> fields = new ArrayList<>();
        for (ColumnInfo column : columns) {
            fields.add(column.name);
        }
        return TableSelect.buildSelect(table, fields, where);
    }
}
