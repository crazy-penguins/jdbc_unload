package ewoks.jdbc_unload;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ColumnInfo {
    public String name = null;
    public String dataType = null;
    public int dataLength;
    public int digits;
    public int decimalPlaces;
    public int position;
    public int jdbcDataType;

    public ColumnInfo(ResultSet column) throws SQLException {
        name = column.getString("COLUMN_NAME").toLowerCase();
        dataType = column.getString("TYPE_NAME").toLowerCase();
        if (dataType.equals("varchar2")
            || dataType.equals("nvarchar")
            || dataType.equals("char")) {
            dataType = "varchar";
        }
        dataLength = column.getInt("COLUMN_SIZE");
        if (!dataType.equals("varchar")) {
            digits = dataLength;
        }
        decimalPlaces = column.getInt("DECIMAL_DIGITS");
        position = column.getInt("ORDINAL_POSITION");
        jdbcDataType = column.getInt("DATA_TYPE");
    }
}
