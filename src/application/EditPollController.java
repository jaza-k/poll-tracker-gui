package application;

/**
 * EditPollController class which loads the scene for editing polls
 * 
 * @author Jaza Khan
 *
 */

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;

public class EditPollController {

	public static PollTrackerApp app;
	public static VisualizePollController visualizer;

	/** instance variables with ID's for all elements */
    @FXML
    private Button clearButton;
    @FXML
    private BorderPane editor;
    @FXML
    private Label numberOfSeats;
    @FXML
    private FlowPane options;
    @FXML
    private ChoiceBox<String> partyDropdown;
    @FXML
    private BorderPane partyEditor;
    @FXML
    private FlowPane partySelector;
    @FXML
    private Label partyToUpdate;
    @FXML
    private Label percentageSign;
    @FXML
    private ChoiceBox<String> pollDropdown;
    @FXML
    private FlowPane pollNameEditor;
    @FXML
    private TextField pollNameTextfield;
    @FXML
    private FlowPane pollSelector;
    @FXML
    private Label pollToEdit;
    @FXML
    private Label seatsLabel;
    @FXML
    private TextField seatsTextfield;
    @FXML
    private Label title;
    @FXML
    private Button updatePartyButton;
    @FXML
    private Button updatePollNameButton;
    @FXML
    private Label votesLabel;
    @FXML
    private TextField votesTextfield;
    @FXML
    private BorderPane wrapper;

    
    /** instance variables that are required throughout multiple methods */
    private PollList polls;
    private String pollSelectedToChange;
    private String partySelectedToChange;

    
    /** connector() method
     * 	Exists only to link classes and make them accessible */
    void connector(PollTrackerApp app, VisualizePollController visualizer) {
    	EditPollController.app = app;
    	EditPollController.visualizer = visualizer;
    }
    
    
    /** clearValues() method that resets all values in all fields
     * upon clicking of the Clear button */
    @FXML
    void clearValues(ActionEvent event) {
    	votesTextfield.setText("");
    	seatsTextfield.setText("");
    	pollNameTextfield.setText("");
    	partyDropdown.valueProperty().set(null);
    	
    	initialize();
    }
    
    
    /* updateParty() method 
     * Updates party information (expected number of seats and votes)
     * inside the poll and party specified by user */
    @FXML
    void updateParty(ActionEvent event) {
    	Poll[] pollArray = polls.toArray();
    	
    	// grab user's selection and save it in respective variables
    	pollSelectedToChange = pollDropdown.getValue();
    	partySelectedToChange = partyDropdown.getValue();
    	String updatedSeatsForParty = seatsTextfield.getText();
    	String updatedPercentageOfVote = votesTextfield.getText();
    	
    	
    	ArrayList<String> newPartyInfo = new ArrayList<String>();
    	
    	// iterate through polls to find matching poll name
    	for (Poll p: pollArray) {
    		if ((p.getPollName()).equals(pollSelectedToChange)) {
    			Party[] pollParties = p.getParties();
    			
    			// iterate through parties to find matching party name
    			for (Party party: pollParties) {
    				if (party.toString().equals(partySelectedToChange)) {
    					
    					// once party is found, update values
    					party.setProjectedNumberOfSeats(Float.parseFloat(updatedSeatsForParty));
    					party.setProjectedPercentageOfVotes((Float.parseFloat(updatedPercentageOfVote))/100);
    				}
    				// immediately display the change in dropdown box
    				partyDropdown.setValue(party.toString());
    				newPartyInfo.add(party.toString());
    			}
    		}
    	}
    	// update the dropdown options to now include the new change
    	partyDropdown.setItems(FXCollections.observableArrayList(newPartyInfo));
    	visualizer.start();
    }

    
    /** updatePollName() method 
     *  Updates the name of the poll the user specified through the ChoiceBox */
    @FXML
    void updatePollName(ActionEvent event) {
    	// convert polls to Array in order to make them iterable
        Poll[] pollArray = polls.toArray();
        
        // save user input for which poll to change and its new name
    	pollSelectedToChange = pollDropdown.getValue();
    	String updatedPollName = pollNameTextfield.getText();
    	
    	ArrayList<String> newPollNames = new ArrayList();
 
    	// iterate through polls to find matching poll, then set new name
    	for (Poll p: pollArray) {
    		if ((p.getPollName()).equals(pollSelectedToChange)) {
    			p.setPollName(updatedPollName);
    		}
    		newPollNames.add(p.getPollName());
    	}

    	initialize();
    	
    	// immediately display new change in dropdown box
    	pollDropdown.setValue(updatedPollName);
    	
    	// update the poll names under visualize tab as well
    	visualizer.updatePollNames(newPollNames);
    }
    
    
    /** initialize() method 
     *  Stores code that will be run on program start up */
    @FXML
    void initialize() {
    	// call setPolls() to initialize polls instance variable
    	this.polls = setPolls(app.polls);
    	
    	Poll[] pollArray = polls.toArray();
    	
    	// sets the Label to display the accurate number of seats that are available in the election
    	Integer seatsAvailable = polls.getNumOfSeats();
    	numberOfSeats.setText("/ " + seatsAvailable.toString());
    	
    	// displaying the poll names in dropdown to select poll
    	ArrayList<String> pollNames = new ArrayList();
    	
    	for (Poll p: pollArray) {
    		String nameOfPoll = p.getPollName();
    		pollNames.add(nameOfPoll);
    	}
    	
    	// setting the values in the dropdown
    	pollDropdown.setItems(FXCollections.observableArrayList(pollNames));
    	
    	// once the user selects a poll to edit from the dropdown
    	pollDropdown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue observable, Number oldValue, Number newValue) {
				 int index = newValue.intValue();
				 if (index >= 0) {
					 pollSelectedToChange = (String)(pollNames.toArray())[index];
					 
					 for (Poll poll: pollArray) {
						 if(poll.getPollName().equals(pollSelectedToChange)) {
							 Poll selectedPoll = poll;
							 
							 // displaying all the parties in dropdown for party to update
							 Party[] parties = selectedPoll.getParties();
    						 ArrayList<String> partiesToSelect = new ArrayList<String>();
    						 
    						 for (Party p: parties) {
    					    		String partyInfo = p.toString();
    					    		partiesToSelect.add(partyInfo);
    					     }
    						 
    						 // setting the values in the dropdown
    						 partyDropdown.setItems(FXCollections.observableArrayList(partiesToSelect));
						 }
					 } 
				 }
			}
    	});
    }


    /** setPolls() method
     *  Called by PollTrackerApp to provide the controller with list of polls
     *  to edit in the view
     *  
     * 	@param the PollList which will be edited provided by PollTrackerApp 
     * 	@return the newly-set polls object */
	public PollList setPolls(PollList polls) {
		this.polls = polls;
		return this.polls;
	}
	

	/** figure out wtf this does */
	public void refresh() {
		// TODO 
	}
    
}