/*
 * Copyright (C) 2015 Black River Audio Works (www.blackriveraudioworks.com)
 *
 * This file is part of AnalFreq.
 *
 * Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AnalFreq.  If not, see <http://www.gnu.org/licenses/>.
 */
package analfreq.gui;

import analfreq.config.Config;
import analfreq.datamanager.DataManager;
import analfreq.debug.Debug;
import analfreq.freqevent.FreqEvent;
import analfreq.freqevent.FreqEventType;
import analfreq.xml.XMLWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UIManager {

    private static final DragZoomBubbleChart chart
            = DragZoomBubbleChartManager.createBubbleChart();
    private static ObservableList<FreqEventType> typeOptions;
    private static ComboBox comboBox;
    private static BorderPane rootNode;
    private static MenuBar menuBar;
    private static Menu menuFile, examplesMenu, helpMenu;
    private static Label createNewFreqEventLabel, typeLabel, minFreqLabel, maxFreqLabel,
            startTimeLabel, endTimeLabel, eventNameLabel, eventDescriptionLabel;

    private static HBox eventTypeControls, minFreqControls, maxFreqControls,
            startTimeControls, endTimeControls, eventDescriptionControls, buttonControls;

    private static TextField minFreqTextField, maxFreqTextField, startTimeTextField,
            endTimeTextField, eventNameTextField, eventDescriptionTextField;

    private static Button submitUpdateButton, resetButton;
    
    private static Node updateNode;
    
    // True when updating an existing node
    private static boolean isUpdate = false;
    
    public static void updateForm(Node n, FreqEvent fe){
        isUpdate = true;
        updateNode = n;
        Debug.debug(Debug.getCurrentMethodName()+"Node: "+n.getId()+" FreqEvent: "+fe.getName());
        minFreqTextField.setText(Integer.toString(fe.getMinFreq()));
        maxFreqTextField.setText(Integer.toString(fe.getMaxFreq()));
        startTimeTextField.setText(Integer.toString(fe.getStartTime()));
        endTimeTextField.setText(Integer.toString(fe.getEndTime()));
        eventNameTextField.setText(fe.getName());
        eventDescriptionTextField.setText(fe.getDescription());
        comboBox.setValue(fe.getType());
        submitUpdateButton.setText("Update");
    }
    
     private static void reset() {
        isUpdate = false;
        Debug.debug(Debug.getCurrentMethodName());
        eventNameTextField.clear();
        minFreqTextField.clear();
        maxFreqTextField.clear();
        startTimeTextField.clear();
        endTimeTextField.clear();
        eventDescriptionTextField.clear();
        submitUpdateButton.setText("Submit");
    }
   
    /**
     * This method is called once per series to be plotted. For example, the KICK
     * series might contain any number of data items: KICK PUNCH, KICK CLICK etc.
     * @param series 
     */
    public static void plotObject(XYChart.Series series) {
        chart.getData().addAll(series);
    }

    public static DragZoomBubbleChart getChart() {
        Debug.debug(Debug.getCurrentMethodName());
        return chart;
    }

    private static void initMenus() {
        Debug.debug(Debug.getCurrentMethodName());
        menuBar = new MenuBar();
        menuFile = new Menu("File");
        menuFile.getItems().add(new MenuItem("New"));
        menuFile.getItems().add(new MenuItem("Open"));

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction((ActionEvent) -> {
            XMLWriter.writeXML(DataManager.getFreqEvents());
        });

        menuFile.getItems().add(saveMenuItem);
        menuFile.getItems().add(new MenuItem("Save As"));
        menuFile.getItems().add(new SeparatorMenuItem());

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction((ActionEvent) -> {
            System.exit(0);
        });

        menuFile.getItems().add(exitMenuItem);
        menuBar.getMenus().add(menuFile);
        examplesMenu = new Menu("Settings");
        examplesMenu.getItems().add(new MenuItem("Preferences"));
        examplesMenu.getItems().add(new MenuItem("Change Theme"));
        menuBar.getMenus().add(examplesMenu);
        helpMenu = new Menu("Help");
        helpMenu.getItems().add(new MenuItem("Help Contents"));
        helpMenu.getItems().add(new MenuItem("Check for Updates"));
        helpMenu.getItems().add(new MenuItem("About"));
        menuBar.getMenus().add(helpMenu);
    }

    private static void initRootNode() {
        Debug.debug(Debug.getCurrentMethodName());
        rootNode = new BorderPane();
        rootNode.getStylesheets().add("css/Skin01.css");
        rootNode.setCenter(chart);
        rootNode.setRight(UIManager.createUIControls());
        rootNode.setTop(menuBar);
    }

    public static void initStage(Stage stage) {
        Debug.debug(Debug.getCurrentMethodName() + ": Initializing Program...");
        initMenus();
        initRootNode();
        stage.setTitle(Config.STAGE_TITLE);
        stage.setScene(new Scene(rootNode));
        stage.getIcons().add(new Image("icon/icon.png"));
        stage.show();

    }

   

    private static void initUIControls() {
        Debug.debug(Debug.getCurrentMethodName());
        
        typeOptions = FXCollections.observableArrayList(
                FreqEventType.GOOD,
                FreqEventType.BAD,
                FreqEventType.UGLY
        );
        
        comboBox = new ComboBox(typeOptions);
        comboBox.setValue(FreqEventType.GOOD);
        
        createNewFreqEventLabel = new Label("Freq Event Editor");
        createNewFreqEventLabel.setFont(new Font(23));
        typeLabel = new Label("Event Type:");
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

        // "Submit" and "Update" button creation
        submitUpdateButton = new Button();
        submitUpdateButton.setText("Submit");
        submitUpdateButton.setOnAction((ActionEvent event) -> {

            //Ensure that user enters data in the correct format
            String minFreqText = minFreqTextField.getText();
            String maxFreqText = maxFreqTextField.getText();

            //The input string must be numbers only.  Cannot contain letters
            String regex = "\\D+";
            if (minFreqText.matches(regex) && maxFreqText.matches(regex)) {
                System.out.println("Invalid input.");

            } else {

                //Sending the data to the DataManager
                if(!isUpdate){
                DataManager.plotFreqEvent(eventNameTextField.getText(),
                        (FreqEventType)comboBox.getValue(),
                        minFreqTextField.getText(),
                        maxFreqTextField.getText(),
                        startTimeTextField.getText(),
                        endTimeTextField.getText(),
                        eventDescriptionTextField.getText());
                }else{
                    DataManager.plotFreqEvent(updateNode, eventNameTextField.getText(),
                        (FreqEventType)comboBox.getValue(), minFreqTextField.getText(),
                        maxFreqTextField.getText(),
                        startTimeTextField.getText(),
                        endTimeTextField.getText(),
                        eventDescriptionTextField.getText());
                }
                reset();
            }
        });

        // "Reset" button creation
        resetButton = new Button();
        resetButton.setText("Reset");
        resetButton.setOnAction((ActionEvent event) -> {
            Debug.debug(Debug.getCurrentMethodName() + ": Reset");
            reset();
        });

        buttonControls = new HBox();
        buttonControls.getChildren().addAll(submitUpdateButton, resetButton);
        buttonControls.setSpacing(10);
    }

    public static GridPane createUIControls() {
        Debug.debug(Debug.getCurrentMethodName());
        initUIControls();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10.0));
        gridPane.setHgap(4.0);
        gridPane.setVgap(4.0);
        // Node, col, row (colspan,rowspan)
        gridPane.add(createNewFreqEventLabel, 0, 0, 2, 1);
        gridPane.add(eventNameLabel, 0, 1);
        gridPane.add(eventNameTextField, 1, 1);
        
         gridPane.add(typeLabel,0,2);
        gridPane.add(comboBox,1,2);
        
        gridPane.add(minFreqLabel, 0, 3);
        gridPane.add(minFreqTextField, 1, 3);
        gridPane.add(maxFreqLabel, 0, 4);
        gridPane.add(maxFreqTextField, 1, 4);
        gridPane.add(startTimeLabel, 0, 5);
        gridPane.add(startTimeTextField, 1, 5);
        gridPane.add(endTimeLabel, 0, 6);
        gridPane.add(endTimeTextField, 1, 6);
        gridPane.add(eventDescriptionLabel, 0, 7);
        gridPane.add(eventDescriptionTextField, 1, 7);
        gridPane.add(buttonControls, 0, 8, 2, 1);
       
        return gridPane;
    }
}
