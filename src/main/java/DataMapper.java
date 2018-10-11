
import com.sun.istack.internal.NotNull;
import javafx.scene.Node;

import java.sql.*;

public class DataMapper {
    private NodeMapper nodeMapper;
    public DataMapper(){
        nodeMapper =new NodeMapper();
    }
    public DataMapper(@NotNull NodeMapper nodeMapper){
        setNodeMapper(nodeMapper);
    }
    public void setNodeMapper(NodeMapper mapper){
        this.nodeMapper =mapper;
    }
    public void assignData(ResultSet resultSet, Node root) throws SQLException {
        setRoot(root);
        assignData(resultSet);
    }
    public void setRoot(Node root){
        nodeMapper.setRoot(root);
    }
    public void assignData(ResultSet resultSet) throws SQLException {
        mapDataToNode(resultSet);
    }
    public void assignDataFromJSON(String json){
        //ToDO assign data to fields from json string
        throw new UnsupportedOperationException("Not yet supported by library");
    }
    public void assignDataFromXML(String xml){
        //ToDO assign data to fields from xml string
        throw new UnsupportedOperationException("Not yet supported by library");
    }
    public void assignDataFromCSV(String xml){
        //ToDO assign data to fields from csv string
        throw new UnsupportedOperationException("Not yet supported by library");
    }
    private void mapDataToNode(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int numColumns = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= numColumns; i++) {
                String column_name = rsmd.getColumnName(i);
                column_name = column_name.toLowerCase();
                switch (rsmd.getColumnType(i)) {
                    case java.sql.Types.ARRAY:
                        assignToNode(column_name, resultSet.getArray(column_name));
                        break;
                    case java.sql.Types.TINYINT:
                    case java.sql.Types.SMALLINT:
                    case java.sql.Types.BIGINT:
                    case java.sql.Types.INTEGER:
                        assignToNode(column_name, resultSet.getInt(column_name));
                        break;
                    case java.sql.Types.BOOLEAN:
                        assignToNode(column_name, resultSet.getBoolean(column_name));
                        break;
                    case java.sql.Types.DOUBLE:
                    case Types.DECIMAL:
                    case Types.FLOAT:
                        assignToNode(column_name, resultSet.getFloat(column_name));
                        break;
                    case java.sql.Types.NVARCHAR:
                    case java.sql.Types.VARCHAR:
                        assignToNode(column_name, resultSet.getString(column_name));
                        break;
                    case java.sql.Types.DATE:
                    case java.sql.Types.TIMESTAMP:
                        assignToNode(column_name,resultSet.getDate(column_name) );
                        break;
                    default:
                        assignToNode(column_name, resultSet.getObject(column_name).toString());
                        break;
                }
            }
        }
    }

    private void assignToNode(String column_name, Date value) {
        setValueToDateNode(column_name, value);
    }



    private void assignToNode(String column_name, String value) {
        setValueToStringNode(column_name,value);
    }

    private void assignToNode(String column_name, double value) {
        setValueToDoubleNode();

    }


    private void assignToNode(String column_name, boolean value) {
        setValueToBooleanNode(column_name,value);
    }

    private void assignToNode(String column_name, Array array) {
        setValueToArrayNode(column_name,array);
    }

    private void assignToNode(String column_name, long value) {
        setValueToNumberNode(column_name,value);
    }

    /** @see NodeMapper for to know how HashMap is generated*/
    private void setValueToDateNode(String column_name, Date value) {
        //ToDO get node from HashMap and assign the value to supported node, HashMap key/node id should match column_name
    }
    /** @see NodeMapper for to know how HashMap is generated*/
    private void setValueToStringNode(String column_name, String value) {
        //ToDO get node from HashMap and assign the value to supported node, HashMap key/node id should match column_name
    }
    /** @see NodeMapper for to know how HashMap is generated*/
    private void setValueToDoubleNode() {
        //ToDO get node from HashMap and assign the value to supported node, HashMap key/node id should match column_name
    }
    /** @see NodeMapper for to know how HashMap is generated*/
    private void setValueToBooleanNode(String column_name, boolean value) {
        //ToDO get node from HashMap and assign the value to supported node, HashMap key/node id should match column_name
    }
    /** @see NodeMapper for to know how HashMap is generated*/
    private void setValueToArrayNode(String column_name, Array array) {
        //ToDO get node from HashMap and assign the value to supported node, HashMap key/node id should match column_name
    }
    /** @see NodeMapper for to know how HashMap is generated*/
    private void setValueToNumberNode(String column_name, long value) {
        //ToDO get node from HashMap and assign the value to supported node, HashMap key/node id should match column_name

    }

}
