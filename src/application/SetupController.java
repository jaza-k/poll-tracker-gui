package application;

/**
 * Graphical user interface (GUI) for setting up the
 * Poll Tracker Application.
 * The user inputs data, number of seats, number of parties,
 * number of polls, the parties' names, and their colour, which
 * can be used to visualize polling data.
 * 
 * @author Joshua Lee
 */

import java.io.FileInputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Factory;

public class SetupController {

	// Arrays that store the text fields and colour pickers
	// that are dynamically created so that they can be
	// accessed in the new instance of the controller
	// when the scene is switched.
	private static TextField[] textFields;
	private static ColorPicker[] colourPickers;
	
    @FXML
    private TextField numPartiesTextField;

    @FXML
    private TextField numPollsTextField;

    @FXML
    private TextField numSeatsTextField;

    @FXML
    private Button resetButton;

    @FXML
    private Button submitButton;
    
    @FXML
    private VBox colourOptionBox;

    @FXML
    private VBox promptBox;

    @FXML
    private Button submitPartyInfoButton;

    @FXML
    private VBox textFieldBox;

    /**
     * An event listener for the <code>resetButton</code>.
     * Clears the text fields for <code>numSeatsTextField</code>,
     * <code>numPollsTextField</code>, and <code>numPartiesTextField</code> 
     * when clicked.
     * 
     * Precondition: The reset button exists and has been linked.
     * Postcondition: The text fields, <code>numSeatsTextField</code>,
     * <code>numPollsTextField</code>, and 
     * <code>numPartiesTextField</code>, are cleared.
     * 
     * @param event Button click event for the <code>reset</code> button
     */
    @FXML
    void reset(ActionEvent event) {
    	numSeatsTextField.setText("");
    	numPartiesTextField.setText("");
    	numPollsTextField.setText("");
    }

    /**
     * Sets up the alignment and spacing of the VBoxes
     * 
     * Precondition: The VBoxes exists
     * Postcondition: Creates new VBoxes with the correct
     * spacing between children, alignments and the paddings.
     */
    private void vBoxSetup() {
    	// Creates VBoxes with spacing such that the children
    	// elements align in a line.
    	promptBox = new VBox(19);
        textFieldBox = new VBox(10);
        colourOptionBox = new VBox(10);
        
        // Sets the alignment and padding for each VBox
        promptBox.setAlignment(Pos.CENTER);
		promptBox.setPadding(new Insets(0, 0, 0, 50));
		
		textFieldBox.setAlignment(Pos.CENTER);
		textFieldBox.setPadding(new Insets(0, 10, 0, 10));
		
		colourOptionBox.setAlignment(Pos.CENTER);
		colourOptionBox.setPadding(new Insets(0, 50, 0 ,0));
    }
    
    /**
     * Adds 'n' labels, text fields, and colour pickers based on
     * on 'n' parties to the new scene.
     * 
     * Precondition: numOfParties is not null and <code>promptBox</code>, 
     * <code>textFieldBox</code>, and <code>colourOptionBox</code> exists.
     * Postcondition: 'n' amount of labels, text fields, and colour 
     * pickers has been added to the VBoxes of the new scene.
     * 
     * @param numOfParties The number of parties the user inputed
     */
    private void addElements(int numOfParties) {
    	// Initializes the arrays to store text fields and colour pickers
		textFields = new TextField[numOfParties];
		colourPickers = new ColorPicker[numOfParties];
    	
		// adds 'n' labels, text fields, and colour pickers
		// based on how many parties there are
    	for (int i = 0; i < numOfParties; i++) {
			Label label = new Label("Name of Party");
			TextField textField = new TextField();
			ColorPicker colourPicker = new ColorPicker();
			
			textField.setPromptText("Enter name");
			
			// adds the text field and colour picker to the array
			// so that it can accessed in the new instance of the
			// controller in the new scene.
			textFields[i] = textField;
			colourPickers[i] = colourPicker;
			
			promptBox.getChildren().add(label);
			textFieldBox.getChildren().add(textField);
			colourOptionBox.getChildren().add(colourPicker);
		}
    }
    
    /**
     * An event listener for the <code>submitButton</code>.
     * Stores the number of seats and polls from the input in 
     * the <code>Factory</code> and initializes and switches to the new scene.
     * 
     * Precondition: The submit button exists and has been linked.
     * Postcondition: Stores the user input for number of seats and 
     * poll in <code>Factory</code> and creates and switches to a new scene.
     * 
     * @param event Button click event for the <code>submit</code> button
     */
    @FXML
    void submit(ActionEvent event) {
    	// Gets numeric values from the user input in the text fields
    	int numOfSeats = Integer.parseInt(numSeatsTextField.getText());
    	int numOfParties = Integer.parseInt(numPartiesTextField.getText());
    	int numOfPolls = Integer.parseInt(numPollsTextField.getText());
    	
    	// Stores the number of seats and polls from the user input in Factory
    	Factory.getInstance().setNumOfSeats(numOfSeats);
    	Factory.getInstance().setNumOfPolls(numOfPolls);
    	
    	// Creates a new scene for the input of party names and their colours
    	try {
    		BorderPane partyView = new BorderPane();
    		
    		// Loads FXML file for the new scene
    		FXMLLoader loader = new FXMLLoader();
    		partyView = (BorderPane)loader.load(new FileInputStream("src/view/PartyNamesView.fxml"));
    		
    		// Initializes the scene
            vBoxSetup();
    		addElements(numOfParties);

    		partyView.setLeft(promptBox);
    		partyView.setCenter(textFieldBox);
    		partyView.setRight(colourOptionBox);
    		
    		Scene partyScene = new Scene(partyView, 400, 400);
    		
    		// gets the window that the event is in
    		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    		
    		window.setScene(partyScene);
    		window.show();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * An event listener for the <code>submitPartyInfoButton</code>
     * Stores the names and the colours of the parties from the input in
     * the <code>Factory</code>.
     * 
     * Precondition: <code>textFields</code> and <code>colourPickers</code>
     * are not null, and the submit party info button exists and has been linked.
     * Postcondition: Stores the user input for the names and colours of the parties
     * in <code>Factory</code>
     * 
     * @param event Button click event for the <code>submitPartyInfo</code> button
     */
    @FXML
    void submitPartyInfo(ActionEvent event) {
    	int numOfParties = textFields.length;
    	
    	String[] partyNames = new String[numOfParties];
    	Color[] partyColours = new Color[numOfParties];
    	
    	// gets the input from the text fields and colour pickers
    	// which were initialized in the previous scene
    	for (int i = 0; i < numOfParties; i++) {
    		partyNames[i] = textFields[i].getText();
    		partyColours[i] = colourPickers[i].getValue();
    	}
    	
    	// Stores the names and colours of the parties from the input
    	Factory.getInstance().setPartyIdentifiers(partyNames, partyColours);
    	
    	// Prints out the Factory to the console when submit
    	System.out.println(Factory.getInstance().toString());
    }

}
