package application;

import java.util.Scanner;

import javafx.scene.paint.Color;
import model.*;

/**
 * This class represents the entire application and pulls together
 * all other classes.
 * 
 * @author Jaza Khan
 * @author Richi Patel
 * @author Joshua Lee
 * 
 */

public class TextApplication {

    /** instance variables */
    private PollList polls;


    /**
     * displayPollDataBySeat() method
     * Prints a visualization of the poll passed in as argument 
     * to allow the user to compare parties.
     * 
     * @param aPoll prints a visualization of this poll's data by seats
     */
    public void displayPollDataBySeat(Poll aPoll) {
        String choice = "seats";
        displayHelper(aPoll, choice);
    }


    /**
     * displayPollsBySeat() method
     * Visualizes by seats
     * Displays all the Poll objects that are in the PollList instance
     * variable polls, followed by the aggregate of the polls. 
     * 
     * @param names a String array of poll names
     */
    public void displayPollsBySeat(String[] names) {
        String choice = "seats";

        // display each poll by seat first
        for (Poll aPoll: polls.toArray()){
            displayPollDataBySeat(aPoll);
        }

        // then display the aggregate poll (by seats)
        createAggPoll(names, choice);
    }


    /**
     * displayPollDataByVotes() method
     * Displays all the Poll objects that are in the PollList instance
     * variable polls, followed by the aggregate of the polls. 
     * 
     * @param aPoll prints a visualization of this poll's data by votes
     */
    public void displayPollDataByVotes(Poll aPoll) {
        String choice = "votes";
        displayHelper(aPoll, choice);
    }


    /**
     * displayPollsByVotes() method
     * Displays all the Poll objects that are in the PollList instance
     * variable polls, followed by the aggregate of the polls. 
     * 
     * @param names a String array of poll names
     */
    public void displayPollsByVotes(String[] names) {
        String choice = "votes";

        // display each poll by votes first
        for (Poll aPoll : polls.toArray()) {
            displayPollDataByVotes(aPoll);
        }
        
        // then display the aggregate poll (by votes)
        createAggPoll(names, choice);
    }
    
    
    /**
     * promptForPollList() method
     * Allows the user to manually enter the data for the polls, which get added
     * to the PollList polls, then gives the users the choice of visualization 
     * 
     * @param partyNames takes in the names of the parties in the election
     */
    public void promptForPollList(String[] partyNames) {
        System.out.println("\nPlease enter the name of each poll (provide names, comma separated):");
        
        Scanner sc = new Scanner(System.in);
        String inputPollNames = sc.nextLine();

        // separate the above input and store the poll names in an array 
        String[] pollNamesArray = inputPollNames.split(",");
        
        // add each poll to the instance variable polls
        for(int i = 0; i < pollNamesArray.length; i++) {
            polls.addPoll(new Poll(pollNamesArray[i], partyNames.length));
        }

        // iterate through each poll, and each party in it, asking for input
        for(Poll p : polls.toArray()) {
            for(String s: partyNames) {
                System.out.println("\nFOR POLL: " + p.getPollName()+ "\nPlease enter the expected number of seats for the party " + s);
                float expectedSeats = sc.nextFloat();
                System.out.println("\nFOR POLL: " + p.getPollName() + "\nPlease enter the expected percentage of votes for the party " + s);
                float expectedPercentage = sc.nextFloat();

                // create a new party with the given information and add it to the poll
                p.addParty(new Party(s, expectedSeats, expectedPercentage));
            }
        }

        // call helper method to give visualization options
        loopVisualization(partyNames);
    }


    /**
     * run() method
     * Runs the entire application. Prompts the user for information and then
     * uses the visualization methods to display the polls created.
     */ 
    public void run() {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Welcome to the poll tracker");
        System.out.println("\nHow many seats are available in the election?");
        int seatsAvailableInElection = Integer.parseInt(userInput.nextLine());

        System.out.println("\nWhich parties are in the election (provide names, comma separated):");
        String inputPartyNames = userInput.nextLine();
        
        // take the given party names and put them into new array
        String[] partiesNameArray = inputPartyNames.split(",");

        System.out.println("\nHow many polls do you want to track?");
        int numberOfPollsToTrack = Integer.parseInt(userInput.nextLine());

        System.out.println("\nWould you like a random set of polls to be generated?");
        String randomOrNot = userInput.nextLine();

        // if user picks option to randomly generate poll
        if (randomOrNot.equalsIgnoreCase("Yes")) {

            // create a new Factory with the provided info
        	Color[] colours = new Color[0];
            Factory fact = Factory.getInstance();
            fact.setPartyIdentifiers(partiesNameArray, colours);
            polls = fact.createRandomPollList(numberOfPollsToTrack);

            // retrieve the names of parties from the random factory
            String[] randomPartyNames = fact.getPartyNames();

            // call helper method to give visualization options
            loopVisualization(randomPartyNames);
        }

        // if user chooses to enter data for all the polls
        else if (randomOrNot.equalsIgnoreCase("No")) {
            polls = new PollList(numberOfPollsToTrack, seatsAvailableInElection);
            promptForPollList(partiesNameArray);
        }
    }


    /** PRIVATE HELPER METHODS */


     /**
     * displayHelper() method
     * Gets number of seats in a poll and then adds the poll to PollList 
     * object privPoll. Then prints out appropriate visualization
     * 
     * @param aPoll gets number of seats of this poll then adds it to polls
     * @param choice a String that stores the user's choice of either seats or votes
     */
    private void displayHelper(Poll aPoll, String choice) {
        // get number of seats for aPoll
        int numberOfSeats = countPollSeats(aPoll);

        // create local PollList object privPoll
        PollList privPoll = new PollList(1, numberOfSeats);
        privPoll.addPoll(aPoll);

        // print out correct visualization
        if (choice.equalsIgnoreCase("seats")) {
            System.out.println(privPoll.textVisualizationBySeats());
        }
        else if (choice.equalsIgnoreCase("votes")) {
            System.out.println(privPoll.textVisualizationByVotes());
        }
    }


     /**
     * allOption() method
     * Asks the user for their choice of visualization after "all", then displays 
     * the appropriate visualization
     * 
     * @param partyNames the names of all parties in the election
     */
    private void allOption(String[] partyNames) {
        String choice = askForVisualizationChoice();

        // if user picks option to visualize by seats
        if (choice.equalsIgnoreCase("seats")) {
            displayPollsBySeat(partyNames);
        }

        // if user picks option to visualize by votes
        else if (choice.equalsIgnoreCase("votes")) {
            displayPollsByVotes(partyNames);
        }
    }


     /**
     * aggregateOption() method
     * Asks the user for their choice of visualization after "aggregate", then
     * displays the appropriate visualization
     * 
     * @param partyNames the names of all parties in the election
     */
    private void aggregateOption(String[] partyNames) {
        String choice = askForVisualizationChoice();
        createAggPoll(partyNames, choice);
    }


     /**
     * createAggPoll() method
     * Creates a Poll object to store only the aggregate poll. Then 
     * visualizes this poll by either seats or votes
     * 
     * @param partyNames the names of all parties in the election
     * @param choice a String that stores the user's choice of either seats or votes
     */
    private void createAggPoll(String[] partyNames, String choice) {
        // create new Poll object to separately store the aggregate poll
        Poll aggregatePoll = polls.getAggregatePoll(partyNames);

        // create new helper PollList object to store aggPoll
        PollList help = new PollList(1, countPollSeats(aggregatePoll));
        help.addPoll(aggregatePoll);

        // visualize the helper poll accordingly
        if (choice.equalsIgnoreCase("seats")) {
            System.out.println(help.textVisualizationBySeats());
        } 
        else if (choice.equalsIgnoreCase("votes")) {
            System.out.println(help.textVisualizationByVotes());
        }
    }


    /**
     * askForVisualizationChoice() method
     * A helper method that asks the user for either visualization by
     * seats or by votes
     * 
     * @return the user response received from Scanner object
     */
    private String askForVisualizationChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWould you like to visualize by seats or by votes?");
        String bySeatsOrVotes = sc.nextLine();
        
        return bySeatsOrVotes;
    }


    /**
     * countPollSeats() method
     * A method that returns the total number of seats in a Poll object
     * 
     * @param aPoll the Poll object where we get the number of seats from
     * @return the number of seats in aPoll
     */
    private int countPollSeats(Poll aPoll) {
    	int numberOfSeats = 0;

    	// adds up the projected number of seats of a party to get the total seats in the poll
    	for (int i = 0; i < (aPoll.getNumberOfParties()); i++) {
            numberOfSeats += aPoll.getParties()[i].getProjectedNumberOfSeats();
        }

    	return numberOfSeats;
    }

    
    /**
     * loopVisualization() method
     * A helper method that asks for which kind of visualization the user wants
     * and displays the appropriate visualization by calling their method. Loops 
     * this process until user enters quit
     * 
     * @param partyNames the names of all parties in the election
     */
    private void loopVisualization(String[] partyNames) {
        Scanner sc = new Scanner(System.in);
        boolean endApp = false;
        
        do {
            System.out.println("\nWhich visualization would you like?\n" + "all (show result of all polls), aggregate (show aggregate result), quit (end application)");
            String visualizationChoice = sc.nextLine();

            // if user picked "all" option for visualization
            if (visualizationChoice.equalsIgnoreCase("All")) {
                allOption(partyNames);
            }

            // if user picked "aggregate" option for visualization
            else if (visualizationChoice.equalsIgnoreCase("Aggregate")) {
                aggregateOption(partyNames);
            }

            // if user wants to quit application
            else if (visualizationChoice.equalsIgnoreCase("Quit")) {
                endApp = true;
            }
            
        } while (!endApp);

    }

    public static void main(String[] args) {
        TextApplication textApp = new TextApplication();
        textApp.run();
    }
}