import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

class DataAssigner {
    private HashMap<String, CustomMapEventListener> listeners;
    private HashMap<String, BooleanMap> booleanMap;
    private HashMap<String, Character> arrayMap;
    private HashMap<String, String> dateMap;
    private HashMap<String, String> precisionMap;
    private NodeMapper nodeMapper;

    DataAssigner(NodeMapper nodeMapper) {
        this.nodeMapper = nodeMapper;
        listeners = new HashMap<>();
        booleanMap = new HashMap<>();
        arrayMap = new HashMap<>();
        dateMap = new HashMap<>();
        precisionMap = new HashMap<>();
    }

    /**
     * @param column_name column/key of database/json/csv
     * @param value       value corresponding to the column/key
     */
    void setValueToDateNode(String column_name, Date value) {
        Node node = nodeMapper.getMap().get(column_name);
        if (node != null) {
            if (dateMap.containsKey(column_name)) {
                SimpleDateFormat format = new SimpleDateFormat(dateMap.get(dateMap.get(column_name)));
                setValueToStringNode(column_name, format.format(value.getTime()));
            } else if (node instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) node;
                datePicker.setValue(value.toLocalDate());
            } else {
                throw new UnsupportedOperationException("column " + column_name + "cannot be assigned to " + node.toString());
            }
        }
    }

    /**
     * @param column_name column/key of database/json/csv
     * @param value       value corresponding to the column/key
     */
    void setValueToStringNode(String column_name, String value) {
        Node node = nodeMapper.getMap().get(column_name);
        if (node != null) {
            if (node instanceof Labeled) {
                Labeled labeled = (Labeled) node;
                labeled.setText(value);
            } else {
                throw new UnsupportedOperationException("column " + column_name + "cannot be assigned to " + node.toString());
            }
        }

    }

    /**
     * @param column_name column/key of database/json/csv
     * @param value       value corresponding to the column/key
     */
    void setValueToDoubleNode(String column_name, double value) {
        Node node = nodeMapper.getMap().get(column_name);
        if (node != null) {
            String formatted;
            if (precisionMap.containsKey(column_name)) {
                String precision = precisionMap.get(column_name);
                formatted = String.format(precision, value);
            } else {
                formatted = String.valueOf(value);
            }
            setValueToStringNode(column_name, formatted);
        }

    }

    /**
     * @param column_name column/key of database/json/csv
     * @param value       value corresponding to the column/key
     */
    void setValueToBooleanNode(String column_name, boolean value) {
        Node node = nodeMapper.getMap().get(column_name);
        if (node != null) {
            if (booleanMap.containsKey(column_name)) {
                setValueToStringNode(column_name, booleanMap.get(column_name).getString(value));
            } else if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                checkBox.setSelected(value);
            } else if (node instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) node;
                toggleButton.setSelected(value);
            } else {
                throw new UnsupportedOperationException("column " + column_name + "cannot be assigned to " + node.toString());
            }
        }
    }

    /**
     * @param column_name column/key of database/json/csv
     * @param array       is array-list of values corresponding to the column/key
     */
    void setValueToArrayNode(String column_name, ArrayList<?> array) {
        Node node = nodeMapper.getMap().get(column_name);
        if (node != null) {
            StringBuilder output = new StringBuilder();
            if (arrayMap.containsKey(column_name)) {
                for (Object obj : array) {
                    String ofObj;
                    if (obj instanceof Boolean) {
                        ofObj = booleanMap.get(column_name).getString((Boolean) obj);
                    } else if (obj instanceof Double | obj instanceof Float | obj instanceof BigDecimal) {
                        String precision = precisionMap.get(column_name);
                        ofObj = String.format(precision, obj);
                    } else {
                        ofObj = String.valueOf(obj);
                    }
                    output.append(ofObj).append(arrayMap.get(column_name));
                }
                setValueToStringNode(column_name, output.toString());
            } else {
                throw new UnsupportedOperationException("Array objectS either requires custom mapping or mapArrayToString(String,char) to be set");
            }
        }

    }

    /**
     * @param column_name column/key of database/json/csv
     * @param value       value corresponding to the column/key
     */
    void setValueToNumberNode(String column_name, long value) {
        setValueToStringNode(column_name, String.valueOf(value));
    }
    /**
     * @param forId column/key of database/json/csv
     * @param toDateFormat  format of date when converting Date to String
     */
    void addMapDate(String forId, String toDateFormat) {
        dateMap.put(forId, toDateFormat);
    }

    void addBooleanMap(String trueText, String falseText, String forId) {
        booleanMap.put(forId, new BooleanMap(trueText, falseText));
    }

    void addArrayMap(char seperator, String forId) {
        arrayMap.put(forId, seperator);
    }

    void addFloatMap(String forID, String precisionFormat) {
        precisionMap.put(forID, precisionFormat);
    }

    void addListener(CustomMapEventListener listener, String id) {
        listeners.put(id, listener);
    }

    void clearListeners() {
        listeners.clear();
    }

    void invokeListener(String column_name, Object value) {
        Node node = nodeMapper.getMap().get(column_name);
        if (node != null) {
            CustomMapEventListener<?> customMapEventListener = listeners.get(column_name);
            customMapEventListener.onDataFound(value, column_name, node);
        } else {
            throw new IllegalStateException("No node found for the custom map listener");
        }
    }

    private boolean hasCustomMapping(String column_name) {
        return listeners.containsKey(column_name);
    }

    void assign(String column_name, long value) {
        if (hasCustomMapping(column_name)) {
            invokeListener(column_name, value);
        } else {
            setValueToNumberNode(column_name, value);
        }
    }

    void assign(String column_name, double value) {
        if (hasCustomMapping(column_name)) {
            invokeListener(column_name, value);
        } else {
            setValueToDoubleNode(column_name, value);
        }

    }

    void assign(String column_name, boolean value) {
        if (hasCustomMapping(column_name)) {
            invokeListener(column_name, value);
        } else {
            setValueToBooleanNode(column_name, value);
        }
    }

    void assign(String column_name, String value) {
        if (hasCustomMapping(column_name)) {
            invokeListener(column_name, value);
        } else {
            setValueToStringNode(column_name, value);
        }
    }

    void assign(String column_name, ArrayList<?> array) {
        if (hasCustomMapping(column_name)) {
            invokeListener(column_name, array);
        } else {
            setValueToArrayNode(column_name, array);
        }
    }

    void assign(String column_name, Date value) {
        if (hasCustomMapping(column_name)) {
            invokeListener(column_name, value);
        } else {
            setValueToDateNode(column_name, value);
        }
    }
}

class BooleanMap {
    private final String trueText;
    private final String falseText;

    BooleanMap(String trueText, String falseText) {
        this.trueText = trueText;
        this.falseText = falseText;
    }

    String getString(boolean bol) {
        if (bol) {
            return trueText;
        } else {
            return falseText;
        }
    }
}
