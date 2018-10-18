import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String args[]) {
        try {
            NodeTreeTraversal nodeTreeTraversal = new NodeTreeTraversal();
            nodeTreeTraversal.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //No need to implement
    }
}
