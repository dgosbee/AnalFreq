package analfreq.gui;

import analfreq.config.Config;
import analfreq.datamanager.DataManager;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UIControlFactory {

    // GUI components for "Create New Freq Event"
    private static Label createNewFreqEventLabel;
    private static Label minFreq;
    private static Label maxFreq;
    private static Label startTime;
    private static Label endTime;
    private static Label eventName;
    private static Label eventDescription;
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

    private static void clearTextField() {
        eventNameTextField.clear();
        minFreqTextField.clear();
        maxFreqTextField.clear();
        startTimeTextField.clear();
        endTimeTextField.clear();
        eventDescriptionTextField.clear();
    }

    /**
     * Creates the UI controls that appear on the right side of the screen.
     * Controls are returned on a FlowPane, which can then be placed inside an
     * appropriate layout manager.
     *
     * @return A FlowPane containing the UI controls
     */
    public static FlowPane createUIControls() {
        FlowPane UIControls = new FlowPane(Orientation.VERTICAL);
        buildNewFreqEventPanel();
        UIControls.getChildren().add(createNewFreqEventLabel);
        UIControls.getChildren().add(eventTypeControls);
        UIControls.getChildren().add(minFreqControls);
        UIControls.getChildren().add(maxFreqControls);
        UIControls.getChildren().add(startTimeControls);
        UIControls.getChildren().add(endTimeControls);
        UIControls.getChildren().add(eventDescriptionControls);
        UIControls.getChildren().add(buttonControls);
        return UIControls;
    }

    /**
     * Private convenience method for creating the GUI components of the "Create
     * New Event" portion of the GUI. This code is still pretty gnarly and is in
     * need of a good cleanup.
     */
    private static void buildNewFreqEventPanel() {
        createNewFreqEventLabel = new Label("New Freq Event");
        createNewFreqEventLabel.setFont(new Font(30));
        
        minFreq = new Label("Min Freq:");
        minFreqTextField = new TextField();
        minFreqControls = new HBox();
        minFreqControls.getChildren().addAll(minFreq, minFreqTextField);
        minFreqControls.setSpacing(10);
        
        maxFreq = new Label("Max Freq:");
        maxFreqTextField = new TextField();
        maxFreqControls = new HBox();
        maxFreqControls.getChildren().addAll(maxFreq, maxFreqTextField);
        maxFreqControls.setSpacing(10);
        
        startTime = new Label("Start Time:");
        startTimeTextField = new TextField();
        startTimeControls = new HBox();
        startTimeControls.getChildren().addAll(startTime, startTimeTextField);
        startTimeControls.setSpacing(10);
        
        endTime = new Label("End Time:");
        endTimeTextField = new TextField();
        endTimeControls = new HBox();
        endTimeControls.getChildren().addAll(endTime, endTimeTextField);
        endTimeControls.setSpacing(10);
     
        eventName = new Label("Event Name:");
        eventNameTextField = new TextField();
        eventTypeControls = new HBox();
        eventTypeControls.getChildren().addAll(eventName, eventNameTextField);
        eventTypeControls.setSpacing(10);
        eventDescription = new Label("Event Description:");
        eventDescriptionTextField = new TextField();
        eventDescriptionControls = new HBox();
        eventDescriptionControls.getChildren().addAll(eventDescription,eventDescriptionTextField);
        eventDescriptionControls.setSpacing(10);

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
}
