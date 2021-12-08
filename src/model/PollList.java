package model;

/**
 * The PollList class represents a collection of polls
 * from the same election.
 * 
 *  update added more detailed documentation.
 * 
 * @author Joshua Lee
 * @version 2.1
 */
public class PollList {
	private static final int MAX_STARS_FOR_VISUALIZATION = 18;
	
	private Poll[] polls;
	private int numOfSeats;
	
	/**
	 * Class constructor
	 * 
	 * Precondition: none
	 * Postcondition: Constructs an object with the specified
	 * values for the number of polls and seats if greater than
	 * 1, else the poll array size is set to 5, and the number
	 * of sets is set to 10.
	 * 
	 * @param aNumOfPolls The number of polls in the poll list.
	 * @param aNumOfSeats The number of seats in the poll list.
	 */
	public PollList(int aNumOfPolls, int aNumOfSeats) {
		// Initializes the array polls of the size specified
		// if greater than or equal to 1, else the size is 5.
		if (aNumOfPolls >= 1) {
			polls = new Poll[aNumOfPolls];
		} else {
			polls = new Poll[5];
		}
		
		// Sets the numOfSeats to the value specified if 
		// greater than or equal to 1, else it is set to 10.
		if (aNumOfSeats >= 1) {
			numOfSeats = aNumOfSeats;
		} else {
			numOfSeats = 10;
		}
	}
	
	/**
	 * A getter for <code>polls</code>.
	 * 
	 * Precondition: none
	 * Postcondition: The array <code>polls</code> is returned.
	 * 
	 * @return The polls in the poll list as an array. 
	 */
	public Poll[] toArray() {
		return polls;
	}
	
	/**
	 * A getter for <code>numOfSeats</code>.
	 * 
	 * Precondition: none
	 * Postcondition: <code>numOfSeats</code> is returned.
	 * 
	 * @return The number of seats in the poll list.
	 */
	public int getNumOfSeats() {
		return numOfSeats;
	}
	
	/**
	 * Adds a poll to the poll list if the poll is not null or 
	 * the poll list is not full.
	 * An error message will be printed if the poll list is full
	 * or the poll is null.
	 * 
	 * Precondition: none
	 * Postcondition: A poll is added if the poll list is not null 
	 * and has space, else it does not change the poll list and
	 * prints an error message.
	 * 
	 * @param aPoll a poll that is to be added to the poll list.
	 */
	public void addPoll(Poll aPoll) {
		// Checks if aPoll is null and prints an error message if true.
		if (aPoll == null) {
			System.out.println("ERROR: The argument is null. The list will be left unchanged.");
		} else {
			boolean hasRoom = false;
			int index = 0;
			
			// Checks the poll list for the first occurrence 
			// of an empty (null) entry.
			for (int i = 0; i < polls.length && hasRoom == false; i++) {
				if (polls[i] == null) {
					hasRoom = true;
					index = i;
				}
			}
			
			// Checks if polls is full, if true, an error message is printed,
			// else it adds aPoll to the array at the first empty spot.
			if (hasRoom == true) {
				polls[index] = aPoll;
			} else {
				System.out.println("ERROR: There is no more room in the polls array and no further polls can be added.");
			}
		}
	}
	
	/**
	 * Gets the data of a specified party and returns
	 * the average of the data from all the polls it is in.
	 * 
	 * Precondition: none
	 * Postcondition: A <code>Party</code> object with the average data of a
	 * given party, if it exists in the poll list is returned, 
	 * else, a null <code>Party</code> is returned.
	 * 
	 * @param name The name of a party whose data will be averaged.
	 * @return An instance of Party that contains the data average of a specified party.
	 */
	public Party getAveragePartyData(String name) {
		Party avgParty = new Party(name);
		Party tempParty = new Party(name);
		float avgSeats = 0;
		float avgVotes = 0;
		int partyOccurance = 0;

		// Adds up the number of seats and votes for a given party from
		// the polls only if it is in the poll.
		for (int i = 0; i < polls.length; i++) {
			tempParty = polls[i].getParty(name);
			if (tempParty != null) {
				avgSeats += tempParty.getProjectedNumberOfSeats();
				avgVotes += tempParty.getProjectedPercentageOfVotes();
				partyOccurance++;
			}	
		}

		// Sets the seats and votes in avgParty to be the average
		// seats and votes of a given party.
		if (partyOccurance != 0) {
			avgParty.setProjectedNumberOfSeats(avgSeats / partyOccurance);
			avgParty.setProjectedPercentageOfVotes(avgVotes / partyOccurance);
		}
		
		return avgParty;
	}
	
	/**
	 * Gets the aggregate of all polls in the <code>names</code> list.
	 * 
	 * Precondition: none
	 * Postcondition: Returns a poll representing the
	 * aggregate of all polls in the list.
	 * 
	 * @param names An String array of the party names.
	 * @return A poll that represents that aggregate data.
	 */
	public Poll getAggregatePoll(String[] names) {
		Poll aggregate = new Poll("Aggregate", names.length);
		
		// Adds the average data of a party in names to aggregate.
		for (int i = 0; i < names.length; i++) {
			aggregate.addParty(getAveragePartyData(names[i]));
		}
		
		return aggregate;
	}
	
	/**
	 * Returns the text visualization of the parties' 
	 * seats in the poll list.
	 * 
	 * Precondition: none
	 * Postcondition: Return a string that visualizes
	 * the parties' seats.
	 * 
	 * @return A string that visualizes the parties' seats in the poll list.
	 */
	public String textVisualizationBySeats() {
		String seatVisual = "";
		double numSeatsPerStar = Math.ceil((float)numOfSeats / MAX_STARS_FOR_VISUALIZATION);
		
		// Concatenates the text visualization by seats
		// of the parties in the poll list if not null.
		for (int i = 0; i < polls.length; i++) {
			if (polls[i] != null) {
				seatVisual += polls[i].textVisualizationBySeats(MAX_STARS_FOR_VISUALIZATION, numSeatsPerStar);
				seatVisual += "\n";
			}
		}
		
		return seatVisual;
	}
	
	/**
	 * Returns the text visualization of the parties'
	 * votes in the poll list.
	 * 
	 * Precondition: none
	 * Postcondition: Return a string that visualizes
	 * the parties' votes.
	 * 
	 * @return A string that visualizes the parties' votes in the poll list.
	 */
	public String textVisualizationByVotes() {
		String voteVisual = "";
		double percentVotePerStar = Math.ceil(100.0 / MAX_STARS_FOR_VISUALIZATION);
		
		// Concatenates the text visualization by votes
		// of the parties in the poll list if not null
		// and adds a new line escape code.
		for (int i = 0; i < polls.length; i++) {
			if (polls[i] != null) {
				voteVisual += polls[i].textVisualizationByVotes(MAX_STARS_FOR_VISUALIZATION, percentVotePerStar);
				voteVisual += "\n";
			}
		}
		
		return voteVisual;
	}
	
	/**
	 * Returns the string representation of the seats in the poll list.
	 * 
	 * Precondition: none
	 * Postcondition: Returns a formatted string of the number of seats 
	 * in the poll list.
	 * 
	 * @return A string that contains the total seats in the poll list and the number of seats held by a given party
	 */
	public String toString() {
		return "Number of seats: " + numOfSeats + "\n" + textVisualizationBySeats();
	}
}