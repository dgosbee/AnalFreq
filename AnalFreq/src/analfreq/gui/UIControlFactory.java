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
    private static Label centerFreq;
    private static Label minFreq;
    private static Label maxFreq;
    private static Label eventName;
    private static Label eventDescription;
    private static HBox eventTypeControls;
    private static HBox minFreqControls;
    private static HBox maxFreqControls;
    private static HBox centerFreqControls;
    private static HBox eventDescriptionControls;
    private static HBox buttonControls;
    private static TextField minFreqTextField;
    private static TextField maxFreqTextField;
    private static TextField centerFreqTextField;
    private static TextField eventNameTextField;
    private static TextField eventDescriptionTextField;

    // GUI components for "Property Editor"
    private static Label propertyEditorLabel;

    /**
     * Private convenience method for clearing the text fields.
     */
    private static void clearTextField() {
        eventNameTextField.clear();
        minFreqTextField.clear();
        centerFreqTextField.clear();
        maxFreqTextField.clear();
        eventDescriptionTextField.clear();
    }

    
    /**
     * Creates the UI controls that appear on the right side of the screen.
     * Controls are returned on a FlowPane, which can then be placed inside
     * an appropriate layout manager.
     * 
     * @return A FlowPane containing the UI controls
     */
    public static FlowPane createUIControls() {
        FlowPane UIControls = new FlowPane(Orientation.VERTICAL);
        buildNewFreqEventPanel();
        buildPropertyEditorPanel();
        UIControls.getChildren().add(createNewFreqEventLabel);
        UIControls.getChildren().add(eventTypeControls);
        UIControls.getChildren().add(minFreqControls);
        UIControls.getChildren().add(centerFreqControls);
        UIControls.getChildren().add(maxFreqControls);
        UIControls.getChildren().add(eventDescriptionControls);
        UIControls.getChildren().add(buttonControls);
        UIControls.getChildren().add(propertyEditorLabel);
        return UIControls;
    }

    /**
     * Private convenience method for creating the GUI components of the 
     * "Create New Event" portion of the GUI. This code is still pretty 
     * gnarly and is in need of a good cleanup.
     */
    private static void buildNewFreqEventPanel() {
        createNewFreqEventLabel = new Label("Create New Event");
        createNewFreqEventLabel.setFont(new Font(30));
        
        minFreq = new Label("Minimum Frequency:");
        minFreqTextField = new TextField();
        minFreqControls = new HBox();
        minFreqControls.getChildren().addAll(minFreq, minFreqTextField);
        minFreqControls.setSpacing(10);
        
        
        
        centerFreq = new Label("Center Frequency:");
        centerFreqTextField = new TextField();
        centerFreqControls = new HBox();
        centerFreqControls.getChildren().addAll(centerFreq, centerFreqTextField);
        centerFreqControls.setSpacing(10);
        
        
        maxFreq = new Label("Maximum Frequency:");
        maxFreqTextField = new TextField();
        maxFreqControls = new HBox();
        maxFreqControls.getChildren().addAll(maxFreq, maxFreqTextField);
        maxFreqControls.setSpacing(10);
        
        
        
        
        eventName = new Label("Event Name:");
        eventNameTextField = new TextField();
        eventTypeControls = new HBox();
        eventTypeControls.getChildren().addAll(eventName, eventNameTextField);
        eventTypeControls.setSpacing(10);
        eventDescription = new Label("Event Description:");
        eventDescriptionTextField = new TextField();
        eventDescriptionControls = new HBox();
        eventDescriptionControls.getChildren().addAll(eventDescription,
                eventDescriptionTextField);
        eventDescriptionControls.setSpacing(10);

        /**
         * We need some kind of selection option to link the instruments and
         * frequencies entered to what shows up in the GUI Possibly a pull down
         * menu and/or clicking on the bubbles
         */
        // "Submit" button creation
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction((ActionEvent event) -> {
            //Ensure that user enters data in the correct format
            String minFreqText = minFreqTextField.getText();
            String centerFreqText = centerFreqTextField.getText();
            String maxFreqText = maxFreqTextField.getText();
            
            Config.debug("Inspecting text supplied by user: "
                    + minFreqText + 
                    " " + centerFreqText + 
                    " " + maxFreqText);
            //The input string must be numbers only.  Cannot contain letters
            String regex = "\\D+";
            if (minFreqText.matches(regex) && centerFreqText.matches(regex)
            && maxFreqText.matches(regex)) {
                System.out.println("Invalid input.");

            } else {

                //Sending the data to the DataManager
                DataManager.processData(eventNameTextField.getText(),
                        minFreqTextField.getText(),
                        centerFreqTextField.getText(),
                        maxFreqTextField.getText(),
                        eventDescriptionTextField.getText());
                //Clear the text fields
                clearTextField();
            }
        });

        // "Reset" button creation
        Button resetButton = new Button();
        resetButton.setText("Reset");
        resetButton.setOnAction((ActionEvent event) -> {
            //Resetting form data
            System.out.println("Reset");
            clearTextField();
        });
        buttonControls = new HBox();
        buttonControls.getChildren().addAll(submitButton, resetButton);
        buttonControls.setSpacing(10);
    }

    /**
     * Private convenience method for building the property editor panel.
     */
    private static void buildPropertyEditorPanel() {
        propertyEditorLabel = new Label("Property Editor");
        propertyEditorLabel.setFont(new Font(30));
    }
}
