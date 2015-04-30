package analfreq.gui;

import analfreq.config.Config;
import analfreq.datamanager.DataManager;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UIFactory {

    // CHART AND MENU
    private static BorderPane rootNode;
    private static MenuBar menuBar;
    private static final DragZoomBubbleChart chart
            = DragZoomBubbleChartFactory.createBubbleChart();
    

    // PROPERTY EDITOR
    private static Label createNewFreqEventLabel;
    private static Label minFreqLabel;
    private static Label maxFreqLabel;
    private static Label startTimeLabel;
    private static Label endTimeLabel;
    private static Label eventNameLabel;
    private static Label eventDescriptionLabel;
    private static HBox eventTypeControls;
    private static HBox minFreqControls;
    private static HBox maxFreqControls;
    private static HBox startTimeControls;
    private static HBox endTimeControls;
    private static HBox eventDescriptionControls;
    private static HBox buttonControls;
    private static TextField minFreqTextField;
    private static TextField maxFreqTextField;
    private static TextField startTimeTextField;
    private static TextField endTimeTextField;
    private static TextField eventNameTextField;
    private static TextField eventDescriptionTextField;
 
     public static void plotObject(XYChart.Series series) {
        chart.getData().addAll(series);
    }

    public static void initStage(Stage stage ){
         rootNode = new BorderPane();
        rootNode.getStylesheets().add("css/Skin.css");
        rootNode.setCenter(chart);
        rootNode.setRight(UIFactory.createUIControls());
        
        
        menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuFile.getItems().add(new MenuItem("New"));
        menuFile.getItems().add(new MenuItem("Open"));
        menuFile.getItems().add(new MenuItem("Save"));
        menuFile.getItems().add(new MenuItem("Save As"));
        menuFile.getItems().add(new SeparatorMenuItem());
        menuFile.getItems().add(new MenuItem("Exit"));
        menuBar.getMenus().add(menuFile);
        
        Menu examplesMenu = new Menu("Settings");
        examplesMenu.getItems().add(new MenuItem("Preferences"));
        examplesMenu.getItems().add(new MenuItem("Change Theme"));
        menuBar.getMenus().add(examplesMenu);
        
        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(new MenuItem("Help Contents"));
        helpMenu.getItems().add(new MenuItem("Check for Updates"));
        helpMenu.getItems().add(new MenuItem("About"));
        menuBar.getMenus().add(helpMenu);
        
             
        rootNode.setTop(menuBar);
        stage.setTitle(Config.STAGE_TITLE);
        stage.setScene(new Scene(rootNode));
        stage.getIcons().add(new Image("icon/icon.png"));
        stage.getScene().setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.SHIFT)) {
                chart.setShiftPressed(true);
            }
        });

        stage.getScene().setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.SHIFT)) {
                chart.setShiftPressed(false);
            }
        });

        stage.show();
        
    }
    
    private static void clearTextField() {
        eventNameTextField.clear();
        minFreqTextField.clear();
        maxFreqTextField.clear();
        startTimeTextField.clear();
        endTimeTextField.clear();
        eventDescriptionTextField.clear();
    }

    private static void initControls(){
        createNewFreqEventLabel = new Label("New Freq Event");
        createNewFreqEventLabel.setFont(new Font(23));
        minFreqLabel = new Label("Minimum Freq:");
        minFreqTextField = new TextField();
        maxFreqLabel = new Label("Maximum Freq:");
        maxFreqTextField = new TextField();
        startTimeLabel = new Label("Starting Time:");
        startTimeTextField = new TextField();
        endTimeLabel = new Label("Ending Time:");
        endTimeTextField = new TextField();
        eventNameLabel = new Label("Event Name:");
        eventNameTextField = new TextField();
        eventDescriptionLabel = new Label("Description:");
        eventDescriptionTextField = new TextField();

        // "Submit" button creation
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction((ActionEvent event) -> {
            //Ensure that user enters data in the correct format
            String minFreqText = minFreqTextField.getText();
            String maxFreqText = maxFreqTextField.getText();

            //The input string must be numbers only.  Cannot contain letters
            String regex = "\\D+";
            if (minFreqText.matches(regex) && maxFreqText.matches(regex)) {
                System.out.println("Invalid input.");

            } else {

                //Sending the data to the DataManager
                DataManager.processDataFromGUI(eventNameTextField.getText(),
                        minFreqTextField.getText(),
                        maxFreqTextField.getText(),
                        startTimeTextField.getText(),
                        endTimeTextField.getText(),
                        eventDescriptionTextField.getText());

                clearTextField();
            }
        });

        // "Reset" button creation
        Button resetButton = new Button();
        resetButton.setText("Reset");
        resetButton.setOnAction((ActionEvent event) -> {
            Config.debug("Reset");
            clearTextField();
        });

        buttonControls = new HBox();
        buttonControls.getChildren().addAll(submitButton, resetButton);
        buttonControls.setSpacing(10);
    }
            
    public static GridPane createUIControls(){
        initControls();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10.0));
        gridPane.setHgap(4.0); 
        gridPane.setVgap(4.0);
        // Node, col, row (colspan,rowspan)
        gridPane.add(createNewFreqEventLabel,0,0,2,1);
        gridPane.add(eventNameLabel,0,1);
        gridPane.add(eventNameTextField,1,1);     
        gridPane.add(minFreqLabel,0,2);
        gridPane.add(minFreqTextField,1,2); 
        gridPane.add(maxFreqLabel,0,3);
        gridPane.add(maxFreqTextField,1,3);    
        gridPane.add(startTimeLabel,0,4);
        gridPane.add(startTimeTextField,1,4); 
        gridPane.add(endTimeLabel,0,5);
        gridPane.add(endTimeTextField,1,5); 
        gridPane.add(eventDescriptionLabel,0,6);
        gridPane.add(eventDescriptionTextField,1,6);  
        gridPane.add(buttonControls,0,7,2,1);
        return gridPane;      
    }
}
