package model;

/**
 * <h1>Poll Class individual assignment</h1>
 * Poll Class individual assignment
 * The Poll class represents a collection of parties
 * 
 *	@author Richi
 *
 */
public class Poll {			
	private String name;
	private Party[] parties;
	private int numberOfParties;
	
	/**
	 * Class constructor
	 * 
	 * Precondition: <code>aMax</code> must be an integer larger than 0 and <code>aName</code> must be a string
	 * Postcondition: Constructs a poll object with specified name and array size
	 * 
	 * if <code>aMax</code> is less than 1, then array size is set to 10
	 * 
	 * @param aName The name of the Poll
	 * @param aMax	The maximum number of parties in the parties array
	 * 
	 */
	public Poll(String aName, int aMax) {		
		name = aName;
		//sets the parties array to the size aMax, unless aMax is less than 1, then it is set to 10
		if(aMax < 1) 
			parties = new Party[10];
		else 
			parties = new Party[aMax];	
	}
	
	/**
	 * Getter for poll name, <code>name</code>
	 * 
	 * Precondition: none
	 * Postcondition: the poll name, <code>name</code> is returned
	 * 
	 * @return the name of the pole
	 */
	public String getPollName() {			
		return name;	
	}
	
	/**
	 * Setter for Poll name
	 * Sets the Poll name to String specified in argument
     * 
     @param nameOfPoll The name that will be set for Poll
	 */
	public void setPollName(String nameOfPoll) {
		this.name = nameOfPoll;
	}
	
	/**
	 * getter method for <code>parties</code> array
	 * 
	 * Precondition: none
	 * Postcondition: the array <code>parties</code> is returned
	 * 
	 * @return	the parties array
	 * 
	 */
	public Party[] getParties() {			
		return parties;	
	}
	
	/**
	 * getter method for <code>numberOfParties</code>
	 * 
	 * Precondition: none
	 * Postcondition: gets number of parties in the array
	 * @return the number of parties that have been added to the parties array
	 */
	public int getNumberOfParties() {		
		return numberOfParties;	
	}
	
	/**
	 * addParty method
	 * 
	 * Precondition: <code>aParty</code> cannot be a null party
	 * Postcondition: takes a <code>party</code> as an argument and adds that party to the <code>parties</code> array
	 * 
	 * @param aPartyToAdd the party to be added to the array
	 * 
	 * sends an error message if the <code>parties</code> array is full
	 * or if the method is called with a null Party
	 * 
	 * replaces a party if a duplicate party is added
	 * 
	 */
	public void addParty(Party aParty) {	
		boolean replaced = false;		
		// prints error message if trying to add a null party	
		if(aParty == null) System.out.println("ERROR: you can't add an empty party");	
		for(int n = 0; n < numberOfParties; n++)
			// to replace duplicate parties, the for loop iterates through every party in the parties array
			// if the name of a party in the array is the same as the party we are trying to add, aPartyToAdd, then aPartyToAdd replaces it
			// replaced is set to true to ensure that if a party has been replaced, then it is not added again
			if(aParty.getName().toUpperCase().equals(parties[n].getName().toUpperCase())) {		
				parties[n] = aParty;
				replaced = true;	
			}
		// prints error message if parties is full
		if(numberOfParties == parties.length && replaced == false) 		
			System.out.println("ERROR: the poll is full and no further parties can be added");
		// adds aParty to the parties array if aParty is valid, parties is not full, and aParty has not replaced a duplicate
		// increments numberOfParties every time a party is added
		if(numberOfParties < parties.length && replaced == false && aParty != null) {	
			parties[numberOfParties] = aParty;
			numberOfParties++;	
		}
	}
	
	/**
	 * getParty method
	 *  
	 * Precondition: <code>aName</code> must be a String
	 * Postcondition: Party with requested name is returned
	 * 
	 * Finds the party in the <code>parties</code> array
	 * with the same name as the requested party name <code>aName</code>
	 * and returns it
	 * 
	 * @param aName The name of the party that is requested
	 * 
	 * @return The party with the requested name
	 * if no such party exists, return null
	 * 
	 */
	public Party getParty(String aName) {		
		// iterate through parties, and check if the party name is equal to the requested name, aName
		for(Party p : parties) 
			if(p.getName().toUpperCase().equals(aName.toUpperCase())) 		
				return p;	
		return null;			
	}
	
	/**
	 * textVisualizationBySeats method
	 * 
	 * Precondition: <code>maxStars</code> must be a positive integer and <code>numofSeatsPerStar</code> must be a positive double
	 * Postcondition: visualizes the information in the poll by making a string with the information
	 * starting with the name of the poll, the name of the parties in the poll,
	 * and the maxStars and numOfSeatsPerStar associated with them.
	 * Uses the textVisualizationBySeats method in the Party class to create a visualization
	 * for each party in the <code>parties</code> array list, and returns that string
	 * 
	 * @param maxStars integer representing the maximum number of stars that should be displayed on a single line,
	 * taken as an argument and displayed in the string that is returned
	 * 
	 * @param numOfSeatsPerStar double which indicates how many seats are represented by a single star,
	 * taken as an argument and displayed in the string that is returned
	 * 
	 * @return String that gives a visual representation of the data of this poll,
	 * including the name, the name of the parties, and the <code>maxStars</code> and <code>numOfSeatsPerStar</code>
	 * 
	 */
	public String textVisualizationBySeats(int maxStars, double numOfSeatsPerStar) {	
		String vizBySeats = "";
		// iterates through every party in parties and adds the name of the party only if the party is not null,
		// along with maxStars and numOfSeatsPerStar information to a string
		for(int party = 0; party < parties.length; party++) 
			if(parties[party] != null)
				vizBySeats = vizBySeats + parties[party].textVisualizationBySeats(maxStars, numOfSeatsPerStar) + "\n";
		return name + "\n" + vizBySeats;			
	}
	
	/**
	 * textVisualizationByVotes method
	 * 
	 * Precondition: <code>maxStars</code> must be a positive integer and <code>percentofVotesPerStar</code> must be a positive double
	 * Postcondition: visualizes the information in the poll by making a string with the information
	 * starting with the name of the poll, the name of the parties in the poll,
	 * and the maxStars and numOfSeatsPerStar associated with them.
	 * Uses the textVisualizationByVotes method in the Party class to create a visualization
	 * for each party in the <code>parties</code> array list, and returns that string
	 * 
	 * @param maxStars integer representing the maximum number of stars that should be displayed on a single line,
	 * taken as an argument and displayed in the string that is returned
	 * 
	 * @param percentOfVotesPerStar double indicating the percent of votes per star
	 * taken as an argument and displayed in the string that is returned
	 * 
	 * @return String that gives a visual representation of the data of this poll,
	 * including the name, the name of the parties, and the maxStars and percentOfVotesPerStar
	 */
	public String textVisualizationByVotes(int maxStars, double percentOfVotesPerStar) {	
		String vizByVotes = "";
		// iterates through every party in parties and adds the name of the party only if the party is not null,
		// along with maxStars and percentOfVotesPerStar information to a string
		for(int party = 0; party < parties.length; party++)
			if(parties[party] != null)
				vizByVotes = vizByVotes + parties[party].textVisualizationByVotes(maxStars, percentOfVotesPerStar) + "\n";
		return name + "\n" + vizByVotes;		
	}
	
	/**
	 * toString method
	 *  
	 * Precondition: none
	 * Postcondition: returns a string visualization of the parties in the poll
	 * 
	 * creates a string with the name of the poll, and the name of each party in the poll
	 * all on different lines, and returns this string
	 * 
	 * @return String of the poll name and name of each party in the parties array
	 */
	public String toString() {			
		String theParties = "";
		// iterates through parties and adds the party name of every party in parties to a string only if the party is not null
		for(int i = 0; i < parties.length; i++) 
			if(parties[i] != null)
				theParties = theParties + parties[i].getName() + "\n";
		return name + "\n" + theParties;		
	}
}
