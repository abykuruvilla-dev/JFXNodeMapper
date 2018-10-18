import javafx.scene.Node;

public interface CustomMapEventListener<T> {
    void onDataFound(Object data, String id, Node node);
}
