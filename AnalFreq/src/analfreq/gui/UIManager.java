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
import analfreq.xml.XMLWriter;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UIManager {

    private static final DragZoomBubbleChart chart
            = DragZoomBubbleChartManager.createBubbleChart();
    private static BorderPane rootNode;
    private static MenuBar menuBar;
    private static Menu menuFile, examplesMenu, helpMenu;
    private static Label createNewFreqEventLabel, minFreqLabel, maxFreqLabel,
            startTimeLabel, endTimeLabel, eventNameLabel, eventDescriptionLabel;

    private static HBox eventTypeControls, minFreqControls, maxFreqControls,
            startTimeControls, endTimeControls, eventDescriptionControls, buttonControls;

    private static TextField minFreqTextField, maxFreqTextField, startTimeTextField,
            endTimeTextField, eventNameTextField, eventDescriptionTextField;

    public static void plotObject(XYChart.Series series) {
        chart.getData().addAll(series);
    }
    
    public static DragZoomBubbleChart getChart(){
        return chart;
    }

    private static void initMenus() {
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
        rootNode = new BorderPane();
        rootNode.getStylesheets().add("css/Skin01.css");
        rootNode.setCenter(chart);
        rootNode.setRight(UIManager.createUIControls());
        rootNode.setTop(menuBar);
    }

    public static void initStage(Stage stage) {
        Debug.debug(Debug.getCurrentMethodName()+": Initializing Program...");
        initMenus();
        initRootNode();
        stage.setTitle(Config.STAGE_TITLE);
        stage.setScene(new Scene(rootNode));
        stage.getIcons().add(new Image("icon/icon.png"));
        stage.show();

    }

    private static void clearTextFields() {
        eventNameTextField.clear();
        minFreqTextField.clear();
        maxFreqTextField.clear();
        startTimeTextField.clear();
        endTimeTextField.clear();
        eventDescriptionTextField.clear();
    }

    private static void initUIControls() {
        createNewFreqEventLabel = new Label("Freq Event Editor");
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
                DataManager.plotFreqEvent(eventNameTextField.getText(),
                        minFreqTextField.getText(),
                        maxFreqTextField.getText(),
                        startTimeTextField.getText(),
                        endTimeTextField.getText(),
                        eventDescriptionTextField.getText());

                clearTextFields();
            }
        });

        // "Reset" button creation
        Button resetButton = new Button();
        resetButton.setText("Reset");
        resetButton.setOnAction((ActionEvent event) -> {
            Debug.debug(Debug.getCurrentMethodName()+": Reset");
            clearTextFields();
        });

        buttonControls = new HBox();
        buttonControls.getChildren().addAll(submitButton, resetButton);
        buttonControls.setSpacing(10);
    }

    public static GridPane createUIControls() {
        initUIControls();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10.0));
        gridPane.setHgap(4.0);
        gridPane.setVgap(4.0);
        // Node, col, row (colspan,rowspan)
        gridPane.add(createNewFreqEventLabel, 0, 0, 2, 1);
        gridPane.add(eventNameLabel, 0, 1);
        gridPane.add(eventNameTextField, 1, 1);
        gridPane.add(minFreqLabel, 0, 2);
        gridPane.add(minFreqTextField, 1, 2);
        gridPane.add(maxFreqLabel, 0, 3);
        gridPane.add(maxFreqTextField, 1, 3);
        gridPane.add(startTimeLabel, 0, 4);
        gridPane.add(startTimeTextField, 1, 4);
        gridPane.add(endTimeLabel, 0, 5);
        gridPane.add(endTimeTextField, 1, 5);
        gridPane.add(eventDescriptionLabel, 0, 6);
        gridPane.add(eventDescriptionTextField, 1, 6);
        gridPane.add(buttonControls, 0, 7, 2, 1);
        return gridPane;
    }
}
