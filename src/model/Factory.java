package model;

import java.util.Random;
import javafx.scene.paint.Color;

/**
 * Generates empty or random polls and parties for this application.
 * 
 * This is implemented using the Singleton design pattern.  Only a single instance of
 * this class will exist while the application is running.  To get a reference to the 
 * single instance, use the getInstance() method.
 * 
 * @author Nathaly Verwaal
 *
 */
public class Factory {
	public static final int DEFAULT_NUMBER_OF_SEATS = 345;
	public static final int DEFAULT_NUMBER_OF_POLLS = 5;

	private int numOfSeats = DEFAULT_NUMBER_OF_SEATS;
	private String[] partyNames = {"BQ", "CPC", "Green", "LPC", "NDP", "PPC", "Rhinoceros"};
	private Color[] partyColours = {Color.DARKBLUE, Color.BLUE, Color.GREEN, Color.RED, Color.ORANGE, Color.PURPLE, Color.GRAY}; 
	private int numOfPolls = DEFAULT_NUMBER_OF_POLLS;
	
	private static Factory singleton;
	
	/**
	 * Get the single instance of Factory.
	 * @return the single instance of factory.
	 */
	public static Factory getInstance() {
		if (singleton == null) {
			singleton = new Factory();
		}
		return singleton;
	}
	
	/**
	 * String representation of this Factory object that returns
	 * the state of the factory as a String.  The state includes the number of seats, party names,
	 * and the number of polls in the polltracker that this factory is used for.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Factory state: \n");
		str.append("\tNumber of seats: ");
		str.append(numOfSeats);
		str.append("\n");
		str.append("\tNumber of polls: ");
		str.append(numOfPolls);
		str.append("\n");
		str.append("\tparty names: ");
		for (String name : partyNames) {
			str.append(name);
			str.append(",");
		}
		str.append("\n");
		str.append("\tparty colours: ");
		for (Color color : partyColours) {
			str.append(color);
			str.append(",");
		}
		str.append("\n");
		return str.toString();
	}
	
	/**
	 * Update the number of seat in the poll tracker for this Factory singleton.
	 * @param numOfSeats the number of seats in the election we're tracking.
	 */
	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}
	
	/**
	 * Update the number of polls in the poll tracker.
	 * @param numOfPolls the number of polls that are tracking this election.
	 */
	public void setNumOfPolls(int numOfPolls) {
		this.numOfPolls = numOfPolls;
	}
	
	/** 
	 * Create a factory for an election that has specified number of 
	 * seats available in the election polled.
	 */
	private Factory() {
	}

	/**
	 * Parties can be identified by both names and colours in many elections.  For elections
	 * where this is the case, this setter methods allows the factory to be setup
	 * to use both names and colours when generating Party data.
	 * <p>
	 * This Factory depends on the the list of names and colours to be parallel lists: it will
	 * match the name and colour of a party by index.
	 * 
	 * @param names the names of the parties in the election.
	 * @param colours the colours of the parties in the elections.
	 */
	public void setPartyIdentifiers(String[] names, Color[] colours) {
		if (colours == null || names == null) return;
		int index = 0;
		int size = Math.max(names.length, colours.length);
		partyNames = new String[size];
		partyColours = new Color[size];
		while (index < names.length && index < colours.length) {
			partyNames[index] = names[index];
			partyColours[index] = colours[index];
			index++;
		}
		
		while (index < names.length) {
			partyNames[index] = names[index];
			partyColours[index] = Color.WHITE;
			index++;
		}
		
		while (index < colours.length) {
			partyNames[index] = "Party name unknown";
			partyColours[index] = colours[index];
			index++;
		}
	}
	
	/**
	 * Get the names of the parties for this election.
	 * @return list of names that will be used to generate polls.
	 */
	
	public String[] getPartyNames() {
		return partyNames;
	}
	
	/**
	 * Get the colours of the parties in this election.
	 * @return list of colours that will be used to generate parties.
	 */
	public Color[] getPartyColours() {
		return partyColours;
	}
	
	/**
	 * Create a party with specified name that is projected to win a random number of seats (between
	 * 0 and <code>maximumSeats</code>) and a random percentage of the vote (between 0 and <code>
	 * maximumPercent</code>).
	 * <p>
	 * The method ensures that the percentage of the vote is reasonable with respect with the number
	 * of votes generated.  The projected number of seats is generated as a completed random number between
	 * 0 and <code>maximumSeats</code>.  The percentage of seats generate will be a non-negative number
	 * which is at most 5% higher or lower than the percentage of seats that was generated.  (And the method
	 * ensures this number is at least 0 and at most <code>maximumPercent</code>.
	 *  
	 * @param name the name for the party to generate
	 * @param maximumSeats maximum number of seats this party should be projected to win
	 * @param maximumPercent maximum percent of the vote this party should be projected to win
	 * @return Party that was randomly generated within specified parameters.
	 */
	public Party createRandomParty(String name, Color colour, int maximumSeats, int maximumPercent) {
		Random rand = new Random();
		Party p1 = new Party(name);
		p1.setColour(colour);
		
		int projectedNumberOfSeats = rand.nextInt((int)(maximumSeats*100) + 1)/100;
		
		int percentOfSeatsProjected = projectedNumberOfSeats*100/numOfSeats;
		int maximumPercentOfVotes =  Math.max(0,percentOfSeatsProjected + 5);
		int minimumPercentOfVotes = Math.max(0,percentOfSeatsProjected - 5);
		
		//Generate a random number that falls in the correct range
		int range = Math.round(maximumPercentOfVotes - minimumPercentOfVotes);
		int projectedPercentOfVotes = minimumPercentOfVotes + rand.nextInt(range);
		
		// Set seats and percentage in the party we're generating and return result.
		p1.setProjectedNumberOfSeats(projectedNumberOfSeats);
		p1.setProjectedPercentageOfVotes(Math.min(maximumPercent, projectedPercentOfVotes)/100.0f);
		
		return p1;
	}
	
	/**
	 * Removes the character at specified index from the string and returns the result.
	 * @param str the string to remove a character from
	 * @param index the index of the character to remove
	 * @return the string <code>str</code> with the character at specified index removed.
	 */
	private String removeChar(String str, int index) {
		String first = "";
		String second = "";
		if (index!= 0 ) {
			first = str.substring(0,index);
		}
		if (index != str.length()-1) {
			second = str.substring(index+1);
		}
		return first + second;
	}
	
	/**
	 * Create a random poll which will have the specified name which will contain all the parties for this
	 * election.  The total projected votes for all parties will add to 100% and the total project seats will
	 * add to number of seats available in this election.  But the amount of seats and percent per party will be 
	 * completely random.
	 * @param name the name for the poll to create
	 * @return poll that randomly divides projected number of seats each party will win and projected percent of vote each
	 * party will win.
	 */
	public Poll createRandomPoll(String name) {
		Poll poll = new Poll(name, partyNames.length);
		
		// Create all indices into the partyNames array, so we can randomly choose a party.
		String partyIndices = "";
		for (int index = 0; index < partyNames.length; index++) {
			partyIndices += index;
		}
		
		Random rand = new Random();
		int percentLeft = 100;
		int seatsLeft = numOfSeats;
		for (int counter = 0; counter < partyNames.length-1; counter++) {
			// randomly choose an index from the list of indices into the partyNames array
			int nextIndex = rand.nextInt(partyIndices.length());
			String indexAsString = partyIndices.substring(nextIndex,nextIndex+1);
			int index = Integer.parseInt(indexAsString);

			// Generate a random party with the randomly chosen name and appropriate random seats and percent
			Party p = createRandomParty(partyNames[index], partyColours[index], seatsLeft, percentLeft);
			poll.addParty(p);
			
			// Setup for next party to generate
			percentLeft -= p.getProjectedPercentageOfVotes() * 100;
			seatsLeft -= p.getProjectedNumberOfSeats();
			
			// this has the index we used in this iteration removed
			partyIndices = removeChar(partyIndices, nextIndex);
		}
		
		// There is one party left now, project that it will get the remaining seats and percentage of vote
		int lastIndex = Integer.parseInt(partyIndices);
		Party lastParty = new Party(partyNames[lastIndex],seatsLeft,percentLeft/100.0f);
		lastParty.setColour(partyColours[lastIndex]);
		poll.addParty(lastParty);
		
		return poll;
	}
	
	/**
	 * Create a list of empty polls.
	 * @return a list of empty poll objects
	 */
	public PollList createEmptyPolls() {
		return createEmptyPolls(numOfPolls);
	}
	
	/**
	 * Create a list of empty polls that contains the specified number of polls
	 * in the list.  (Each of which is empty.)
	 * @param numOfPolls number of empty polls to place in the list.
	 * @return the list of empty polls that was generated.
	 */
	public PollList createEmptyPolls(int numOfPolls) {
		PollList list = new PollList(numOfPolls, numOfSeats);
		for (int counter = 0; counter < numOfPolls; counter++) {
			Poll p = new Poll("Poll" + counter, partyNames.length);
			for (int index = 0; index < partyNames.length; index++) {
				Party newParty = new Party(partyNames[index]);
				newParty.setColour(partyColours[index]);
				p.addParty(newParty);
			}
			list.addPoll(p);
		
		}
		return list;
	}
	
	/**
	 * Create a poll list with randomly created polls.  The number of polls is determined
	 * by <code>numOfPolls</code>.
	 * @return a list of randomly generated polls.
	 */
	public PollList createRandomPollList() {
		return createRandomPollList(numOfPolls);
	}

	/**
	 * Create a poll list with randomly created polls.
	 * @param numOfPolls the number of polls that will be randomly generated and placed in the list.
	 * @return the PollList with randomly generated polls.
	 */
	public PollList createRandomPollList(int numOfPolls) {
		PollList list = new PollList(numOfPolls,numOfSeats);
		for (int counter = 0; counter < numOfPolls; counter++) {
			list.addPoll(createRandomPoll("Poll" + counter));
		}
		return list;
	}
}
