import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.HashMap;

public class NodeTreeTraversal {
    private AnchorPane anchorPane;
    private StackPane stackpane;
    private BorderPane borderPane;
    private TitledPane titledPane;
    private DatePicker datePicker;
    private ScrollPane scrollPane;
    private Accordion accordion;
    private VBox vbox;
    private ButtonBar buttonBar;
    private Button yesButton, noButton, cancelButton;
    private ComboBox<String> comboBox;
    private HBox hbox;
    private Label label1, label2, label3;
    private CheckBox checkBox;
    private RadioButton radioButton;
    private TabPane tabPane;
    private Tab tab;

    NodeTreeTraversal() {
        anchorPane = new AnchorPane();
        anchorPane.setId("anchor-pane");
        stackpane = new StackPane();
        stackpane.setId("stack-pane");
        borderPane = new BorderPane();
        borderPane.setId("border-pane");
        titledPane = new TitledPane();
        titledPane.setId("titled-pane");
        datePicker = new DatePicker();
        datePicker.setId("date=picker");
        scrollPane = new ScrollPane();
        scrollPane.setId("scroll-pane");
        accordion = new Accordion();
        accordion.setId("accordion");
        vbox = new VBox();
        vbox.setId("vbox");
        buttonBar = new ButtonBar();
        buttonBar.setId("button-bar");
        yesButton = new Button();
        yesButton.setId("yes-button");
        noButton = new Button();
        noButton.setId("no-button");
        cancelButton = new Button();
        cancelButton.setId("cancel-button");
        comboBox = new ComboBox<>();
        comboBox.setId("combo-box");
        hbox = new HBox();
        hbox.setId("hbox");
        label1 = new Label();
        label1.setId("label-1");
        label2 = new Label();
        label2.setId("label-2");
        label3 = new Label();
        label3.setId("label-3");
        checkBox = new CheckBox();
        checkBox.setId("check-box");
        radioButton = new RadioButton();
        radioButton.setId("radio-button");
        tabPane = new TabPane();
        tabPane.setId("");
        tab = new Tab();
        tab.setId("tab");

    }

    public void test() throws Exception {
        Node parentNode = makeANastyUi();
        NodeMapper mapper = new NodeMapper();
        mapper.setRoot(parentNode);
        HashMap<String, Node> map = mapper.getMap();
        System.out.println("IDs Found");
        for (String key : map.keySet()) {
            System.out.println(key);
        }
    }

    private Node makeANastyUi() {
        ButtonBar.setButtonData(yesButton, ButtonBar.ButtonData.YES);
        ButtonBar.setButtonData(noButton, ButtonBar.ButtonData.NO);
        ButtonBar.setButtonData(cancelButton, ButtonBar.ButtonData.CANCEL_CLOSE);
        buttonBar.getButtons().addAll(yesButton, noButton, cancelButton);
        vbox.getChildren().addAll(buttonBar, comboBox, datePicker);
        hbox.getChildren().addAll(label1, checkBox, label2, radioButton, label3);
        scrollPane.setContent(anchorPane);
        tab.setContent(scrollPane);
        tabPane.getTabs().addAll(tab);
        borderPane.setTop(hbox);
        borderPane.setCenter(vbox);
        accordion.getPanes().addAll(titledPane);
        borderPane.setLeft(accordion);
        stackpane.getChildren().addAll(borderPane, tabPane);
        return stackpane;
    }

}
