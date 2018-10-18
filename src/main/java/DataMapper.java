import com.sun.istack.internal.NotNull;
import javafx.scene.Node;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DataMapper {
    private NodeMapper nodeMapper;
    private DataAssigner dataAssigner;

    public DataMapper() {
        nodeMapper = new NodeMapper();
        dataAssigner = new DataAssigner(nodeMapper);
    }

    public DataMapper(@NotNull NodeMapper nodeMapper) {
        setNodeMapper(nodeMapper);
        dataAssigner = new DataAssigner(nodeMapper);
    }
    /**
     * @param mapper instance of NodeMapper supplied by the user.
     * @see NodeMapper
     */
    public void setNodeMapper(NodeMapper mapper) {
        this.nodeMapper = mapper;
    }
    /**
     * @param root instance of Node supplied by the user.
     * @see NodeMapper
     * @see Node
     */
    public void setRoot(Node root) {
        nodeMapper.setRoot(root);
    }
    /**
     * @param resultSet instance of ResultSet supplied by the user.
     * @see ResultSet
     */
    public void setDataFromResultSet(@NotNull ResultSet resultSet) throws SQLException {
        mapDataToNode(resultSet);
    }
    /**
     * @param json JSON String supplied by the user.
     * refer www.json.org
     */
    public void setDataFromJSON(@NotNull String json) {
        //ToDO assign data to fields from json string
        mapDataToNode(new JSONObject(json));
    }

    /**
     * @param xml JSON String supplied by the user.
     * refer www.w3.org/standards/xml/core
     */
    public void setDataFromXML(@NotNull String xml) {
        //ToDO assign data to fields from xml string
        throw new UnsupportedOperationException("Not yet supported by library");
    }

    /**
     * @param csv JSON String supplied by the user.
     *refer  en.wikipedia.org/wiki/Comma-separated_values
     */
    public void assignDataFromCSV(@NotNull String csv) {
        //ToDO assign data to fields from csv string
        throw new UnsupportedOperationException("Not yet supported by library");
    }
    /**
     * @param forId column/key of resulset/json/csv/xml
     * @param trueText text to be replaced with for boolean value true
     * @param falseText text to be replaced with for boolean value false
     * @see DataAssigner
     */
    public void mapBooleanToString(String forId, String trueText, String falseText) {
        dataAssigner.addBooleanMap(trueText, falseText, forId);
    }

    /**
     * @param forId column/key of resulset/json/csv/xml
     * @param seperator character to be used as separator when converting array to String
     * @see DataAssigner
     */
    public void mapArrayToString(String forId, char seperator) {
        dataAssigner.addArrayMap(seperator, forId);
    }
    /**
     * @param forId column/key of resulset/json/csv/xml
     * @param toDateFormat required to format date to String
     * @see DataAssigner
     */
    public void mapDateToString(String forId, String toDateFormat) {
        dataAssigner.addMapDate(forId, toDateFormat);
    }
    /**
     * @param forId column/key of resulset/json/csv/xml
     * @param precisionFormat required to format floating point value to String.
     * @see DataAssigner
     */
    public void mapFloatingPointToString(String forId, String precisionFormat) {
        dataAssigner.addFloatMap(forId, precisionFormat);
    }
    /**
     * @param forId column/key of resulset/json/csv/xml
     * @param listener to handle certain nodes like list,ComboBox,ChoiceBox,TreeView,TableView that
     * may use custom data types.
     * @see DataAssigner
     */
    public void mapToCustomDataType(String forId, CustomMapEventListener listener) {
        dataAssigner.addListener(listener, forId);
    }
    /**
     *To clear Custom map event listeners
     * @see DataAssigner
     */
    public void clearListners() {
        dataAssigner.clearListeners();
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
                        assignToNode(column_name, toArrayList(resultSet.getArray(column_name)));
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
                        assignToNode(column_name, resultSet.getDate(column_name));
                        break;
                    default:
                        assignToNode(column_name, resultSet.getObject(column_name).toString());
                        break;
                }
            }
        }
    }

    private void mapDataToNode(JSONObject json) {
        for (String key : json.keySet()) {
            Object obj = json.get(key);
            if (obj instanceof Boolean) {
                assignToNode(key, (Boolean) obj);
            } else if (obj instanceof Long) {
                assignToNode(key, (Long) obj);
            } else if (obj instanceof Integer) {
                assignToNode(key, (Integer) obj);
            } else if (obj instanceof Double) {
                assignToNode(key, (Double) obj);
            } else if (obj instanceof Float) {
                assignToNode(key, (Float) obj);
            } else if (obj instanceof String) {
                assignToNode(key, (String) obj);
            } else if (obj instanceof JSONArray) {
                assignToNode(key, toArrayList((JSONArray) obj));
            }
        }
    }

    private ArrayList<Object> toArrayList(JSONArray array) {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Object obj : array) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    private ArrayList<Object> toArrayList(Array array) throws SQLException {
        Object[] list = (Object[]) array.getArray();
        return new ArrayList<>(Arrays.asList(list));
    }

    private void assignToNode(String column_name, Date value) {
        dataAssigner.assign(column_name, value);
    }

    private void assignToNode(String column_name, String value) {
        dataAssigner.assign(column_name, value);
    }

    private void assignToNode(String column_name, double value) {
        dataAssigner.assign(column_name, value);
    }

    private void assignToNode(String column_name, boolean value) {
        dataAssigner.assign(column_name, value);
    }

    private void assignToNode(String column_name, ArrayList<?> array) {
        dataAssigner.assign(column_name, array);
    }

    private void assignToNode(String column_name, long value) {
        dataAssigner.assign(column_name, value);
    }


}
