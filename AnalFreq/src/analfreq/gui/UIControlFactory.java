package analfreq.gui;

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
    private static Label instrumentName;
    private static Label instrumentDescription;
    private static HBox instrumentTypeControls;
    private static HBox centerFreqControls;
    private static HBox instrumentDescriptionControls;
    private static HBox buttonControls;
    private static TextField centerFreqTextField;
    private static TextField instrumentNameTextField;
    private static TextField instrumentDescriptionTextField;

    // GUI components for "Property Editor"
    private static Label propertyEditorLabel;

    /**
     * Private convenience method for clearing the text fields.
     */
    private static void clearTextField() {
        instrumentNameTextField.clear();
        centerFreqTextField.clear();
        instrumentDescriptionTextField.clear();
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
        UIControls.getChildren().add(instrumentTypeControls);
        UIControls.getChildren().add(centerFreqControls);
        UIControls.getChildren().add(instrumentDescriptionControls);
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
        centerFreq = new Label("Center Frequency:");
        centerFreqTextField = new TextField();
        centerFreqControls = new HBox();
        centerFreqControls.getChildren().addAll(centerFreq, centerFreqTextField);
        centerFreqControls.setSpacing(10);
        instrumentName = new Label("Instrument Name:");
        instrumentNameTextField = new TextField();
        instrumentTypeControls = new HBox();
        instrumentTypeControls.getChildren().addAll(instrumentName, instrumentNameTextField);
        instrumentTypeControls.setSpacing(10);
        Label instrumentDescription = new Label("Description:");
        instrumentDescriptionTextField = new TextField();
        instrumentDescriptionControls = new HBox();
        instrumentDescriptionControls.getChildren().addAll(instrumentDescription,
                instrumentDescriptionTextField);
        instrumentDescriptionControls.setSpacing(10);

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
            String centerFreqText = centerFreqTextField.getText();
            System.out.println("Inspecting text supplied by user: "
                    + centerFreqText);
            //The input string must be numbers only.  Cannot contain letters
            String regex = "\\D+";
            if (centerFreqText.matches(regex)) {
                System.out.println("Invalid input.");

            } else {

                //Sending the data to the DataManager
                DataManager.processData(instrumentNameTextField.getText(),
                        centerFreqTextField.getText(),
                        instrumentDescriptionTextField.getText());
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
