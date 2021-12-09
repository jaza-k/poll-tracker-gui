package application;

import model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import model.Factory;

/**
 * The Controller class which visualizes a specified Poll from a PollList
 * 
 * @author Richi Patel
 *
 */
public class VisualizePollController {
    PollList polls;
    //PieChart instance variable representing the projected seats pie chart
    @FXML
    private PieChart seatsChart;
    //PieChart instance variable representing the projected percent of votes pie chart
    @FXML
    private PieChart votesChart;
    //choice box instance variable with options of which poll to display on the charts
    @FXML
    private ChoiceBox<String> vizChoice;
    
    /**
     * initialize method
     * Used to add choices to the choice box created in scene builder, and change PieChart display
     * 
     * Precondition: none
     * Postcondition: All the polls names in the randomly created PollList are added
     * to the options in the choice box, along with the aggregate Poll for that PollList.
     * PieCharts will visualize the data for the selected Poll in the choice box by seats and by votes.
     * the choice box will select aggregate by default, and the PieCharts will display the aggregate
     * Poll by default. If a different Poll is selected, the PieCharts will display data for that poll
     */
    @FXML
    void initialize() {
    	//set the default selected option to "Aggregate Poll" and Display the Aggregate Poll on the PieCharts by default using the chartCreationHelper method.
    	vizChoice.setValue("Aggregate Poll");
    	String[] names = new String[polls.toArray()[0].getNumberOfParties()];
    	for(int i = 0; i < polls.toArray()[0].getNumberOfParties(); )
    		names[i] = polls.toArray()[0].getParties()[i].getName();
    	Poll aggregate = polls.getAggregatePoll(names);
		chartCreationHelper(aggregate);
		//Create the String array of Poll names to be options in the choice box, with the first being the Aggregate Poll
    	String[] visualizationOptions = new String[polls.toArray().length + 1];
    	visualizationOptions[0] = "Aggregate Poll";
    	for(int i = 1; i <= polls.toArray().length; i++) 
    		//add the names of each Poll in the polls PollList to the String array
    		visualizationOptions[i] = polls.toArray()[i-1].getPollName();
    	//set the visualizations String array as the options for the choice box
    	vizChoice.setItems(FXCollections.observableArrayList(visualizationOptions));
    	//check for when choice box selected option is changed
    	vizChoice.getSelectionModel().selectedIndexProperty().addListener(
    		new ChangeListener<Number>() {
    		@Override
    		public void changed(ObservableValue observable, Number oldValue, Number newValue) {
    			int index = newValue.intValue();
    			//change chart data to the selected option in the choice box using chartCreationHelper method to display the data on the PieCharts
    			if(index > 0) {
    				Poll pollToChart = polls.toArray()[index - 1];
    				chartCreationHelper(pollToChart);
    			}
    			else if(index == 0) 
    				chartCreationHelper(aggregate);
    		}
    		}
    	);
    }
    
    /**
     * chartCreationHelper method
     * Used to set the data of the PieCharts so that they visualize the requested data.
     * 
     * Precondition: <code>aPoll</code> must be of the Poll class
     * Postcondition: Displays the Poll data on the PieCharts
     * 
     * @param aPoll The Poll that will be visualized by the PieCharts
     */
    private void chartCreationHelper(Poll aPoll) {
    	//Create PieChart data arrays for the Seats and percent Votes PieCharts
    	PieChart.Data[] seatData = new PieChart.Data[aPoll.getNumberOfParties()];
		PieChart.Data[] votesData = new PieChart.Data[aPoll.getNumberOfParties()];
		for(int i = 0; i < seatData.length; i++) {
			//Create and input the data into the PieChart data array with the Party name and number of seats/votes for each party
			Party aParty = aPoll.getParties()[i];
			seatData[i] = new PieChart.Data(aParty.getName() + " (" + aParty.getProjectedNumberOfSeats() + ")", aParty.getProjectedNumberOfSeats());
			votesData[i] = new PieChart.Data(aParty.getName() + " (" + Math.round(aParty.getProjectedPercentageOfVotes() *1000.0)/10.0 + ")", aParty.getProjectedPercentageOfVotes());
		}
		//set the PieCharts to display the data in the PieChart data arrays
		seatsChart.setData(FXCollections.observableArrayList(seatData));
		votesChart.setData(FXCollections.observableArrayList(votesData));
		//make the legend invisible
		seatsChart.setLegendVisible(false);
		votesChart.setLegendVisible(false);
		//Set the colour of the PieChart data node to the required party colour
		for(int i = 0; i < seatData.length; i++) {
			//get the party colour and change it to HTML form
			String colorToUse = aPoll.getParties()[i].getPartyColour().toString();
			colorToUse = colorToUse.replace("0x", "#");
			//set the colour of the data node to the required colour
			seatData[i].getNode().setStyle("-fx-pie-color: " + colorToUse + ";");
			votesData[i].getNode().setStyle("-fx-pie-color: " + colorToUse + ";");
		}
    }
    
    public void setPolls(PollList polls) {
    	this.polls = polls;
    }
}
