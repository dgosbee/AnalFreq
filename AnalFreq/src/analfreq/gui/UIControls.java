package analfreq.gui;

import analfreq.datamanager.DataManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class UIControls {

    private static TextField centerFreqTextField;
    private static TextField instrumentNameTextField;
    private static TextField instrumentDescriptionTextField;

    private static void clearTextField() {
        instrumentNameTextField.clear();
        centerFreqTextField.clear();
        instrumentDescriptionTextField.clear();

    }

    /**
     * We are probably going to rename this to something like "Property editor"
     * so we can follow the convention better
     *
     * @return
     */
    public static FlowPane createUIControls() {

        FlowPane UIControls = new FlowPane(Orientation.VERTICAL);
        /**
         * This is where the create new event panel begins We are probably going
         * to move this to its own class
         */
        Label createNewFreqEventLabel = new Label("Create New Event");
        createNewFreqEventLabel.setFont(new Font(30));

        Label centerFreq = new Label("Center Frequency:");
        centerFreqTextField = new TextField();
        HBox centerFreqControls = new HBox();
        centerFreqControls.getChildren().addAll(centerFreq, centerFreqTextField);
        centerFreqControls.setSpacing(10);

        Label instrumentName = new Label("Instrument Name:");
        instrumentNameTextField = new TextField();
        HBox instrumentTypeControls = new HBox();
        instrumentTypeControls.getChildren().addAll(instrumentName, instrumentNameTextField);
        instrumentTypeControls.setSpacing(10);

        Label instrumentDescription = new Label("Description:");
        instrumentDescriptionTextField = new TextField();
        HBox instrumentDescriptionControls = new HBox();
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

        HBox buttonControls = new HBox();
        buttonControls.getChildren().addAll(submitButton, resetButton);
        buttonControls.setSpacing(10);

        /**
         * This is where the property editor will be defined However we are
         * probably going to move this to its own class also
         */
        Label propertyEditorLabel = new Label("Property Editor");
        propertyEditorLabel.setFont(new Font(30));

        //Everything below here needs to stay in this class.  DO NOT MOVE
        UIControls.getChildren().add(createNewFreqEventLabel);
        UIControls.getChildren().add(instrumentTypeControls);
        UIControls.getChildren().add(centerFreqControls);
        UIControls.getChildren().add(instrumentDescriptionControls);
        UIControls.getChildren().add(buttonControls);
        UIControls.getChildren().add(propertyEditorLabel);

        return UIControls;
    }

}
