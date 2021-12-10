// JAZA KHAN
// FALL 2021 CPSC 219

package application;

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
    
//    private Factory fact = Factory.getInstance();
//    private PollList polls = fact.createEmptyPolls();
//    private Poll[] pollArray = polls.toArray();
    
    private String pollSelectedToChange;
    private String partySelectedToChange;

    
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
    	pollSelectedToChange = pollDropdown.getValue();
    	partySelectedToChange = partyDropdown.getValue();
    	
    	String updatedSeatsForParty = seatsTextfield.getText();
    	String updatedPercentageOfVote = votesTextfield.getText();
    	
    	for (Poll p: pollArray) {
    		if ((p.getPollName()).equals(pollSelectedToChange)) {
    			Party[] pollParties = p.getParties();
    			
    			for (Party party: pollParties) {
    				if (party.toString().equals(partySelectedToChange)) {
    					party.setProjectedNumberOfSeats(Float.parseFloat(updatedSeatsForParty));
    					party.setProjectedPercentageOfVotes((Float.parseFloat(updatedPercentageOfVote))/100);
    				}
    			}
    		}
    	}
    	
    }

    
    /** updatePollName() method 
     *  Updates the name of the poll the user specified through the ChoiceBox */
    @FXML
    void updatePollName(ActionEvent event) {
        Poll[] pollArray = polls.toArray();
    	pollSelectedToChange = pollDropdown.getValue();
    	String updatedPollName = pollNameTextfield.getText();
 
    	for (Poll p: pollArray) {
    		if ((p.getPollName()).equals(pollSelectedToChange)) {
    			p.setPollName(updatedPollName);
    		}
    	}

    	initialize();
    	pollDropdown.setValue(updatedPollName);
    }
    
    // exists only so app is accessible
    public void testMethod(PollTrackerApp app) {
    	EditPollController.app = app;
    }
    
    /** initialize() method 
     *  Stores code that will be run on program start up */
    @FXML
    void initialize() {
    	this.polls = setPolls(app.polls);
    	
    	Poll[] pollArray = polls.toArray();

    	System.out.println("Verifying what polls is now:\n " + this.polls);
    	
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
     *  @param the PollList which will be edited provided by PollTrackerApp 
     * @return */
	public PollList setPolls(PollList polls) {
		System.out.println("Just reached EditPollController. On setPolls() method right now\n");
		System.out.println("This is polls instance variable in EditPollController BEFORE setting it: " + this.polls);
		System.out.println("\nThis is the polls passed in the argument for setPolls() (coming from app):\n" + polls);
		
		System.out.println("**********************");
		
		this.polls = polls;
		System.out.println("Just set polls\n");
		return this.polls;
	}
	


	/** wtf does this do */
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
    
}








