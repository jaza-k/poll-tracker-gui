package model;

import javafx.scene.paint.Color;

/** This class represents a single political party
 * 
 * @author Jaza Khan
 * 
*/
public class Party {

    /** required instance variables */
    private String name;
    private float projectedNumberOfSeats;
    private float projectedPercentageOfVotes;
    private Color partyColour;


    /** Class constructor
     * Constructs a Party object with a specified name, partyName 
     * 
     * @param partyName The name of the party. Can be any string
     */
    public Party(String partyName) {
        this.name = partyName;
    }


    /** Class constructor
     * Constructs a Party object with specified name, specified number of
     * seats and specified percentage of votes. It also includes validation
     * 
     * @param partyName The name of the party. Can be any string
	 * @param numberOfSeats The number of seats in the party. Cannot be negative
     * @param percentageOfVotes expected win percentage. Value between 0 and 1
     * 
     */
    public Party(String partyName,  float numberOfSeats, float percentageOfVotes) {
        // validation block for checking if numberOfSeats is negative
        if (numberOfSeats < 0) {
            numberOfSeats = 0; // set to 0 if error is found
            System.out.println("Number of seats has to be a positive number");
        }

        // validation block checking if percentageOfVotes is between 0 and 1
        if (percentageOfVotes < 0) {
            percentageOfVotes = 0;
            System.out.println("Percentage of votes has to be greater than 0");
        }
        if (percentageOfVotes > 1) {
            percentageOfVotes = 0;
            System.out.println("Percentage of votes has to be less than 1");
        }

        this.name = partyName;
        this.projectedNumberOfSeats = numberOfSeats;
        this.projectedPercentageOfVotes = percentageOfVotes;
    }

    
    /** Getter methods for all instance variables */

    /**
	 * Getter for party name
	 * @return the name of the Party
	 */
    public String getName() {
        return name;
    }

    /**
	 * Getter for projectedNumberOfSeats
	 * @return the projected number of seats the party will win
	 */
    public float getProjectedNumberOfSeats() {
        return projectedNumberOfSeats;
    }

    /**
	 * Getter for projectedPercentageOfVotes
	 * @return the projected percentage of votes the party will get
	 */
    public float getProjectedPercentageOfVotes() {
        return projectedPercentageOfVotes;
    }

    /**
	 * Getter for partyColour
	 * @return the Color value of a party
	 */
    public Color getPartyColour() {
        // if there is no Color value provided
        if (partyColour == null) 
            System.out.println("No colour set for party");
        return partyColour;
    }
    

    /** Setter methods for all instance variables, with validatiom */

    /**
	 * Setter for party name
	 * Sets the party name to String specified in argument
     * 
     @param partyName The name that will be set for party
	 */
    public void setName(String thePartyName) {
        this.name = thePartyName;
    }

    /**
	 * Setter for party colour
	 * Sets the party color to Color value in argument
     * 
     * @param thePartyColour Color value to be set for party
	 */
    public void setColour(Color thePartyColour) {
        this.partyColour = thePartyColour;
    }

    /**
	 * Setter for projectedNumberOfSeats
	 * Sets the projectedNumberOfSeats to float specified in argument
     * Also runs validation before setting the variable
     * 
     * @param predictedNumberOfSeats The predicted number of seats that will be set
	*/
    public void setProjectedNumberOfSeats(float predictedNumberOfSeats) {
        // check if argument is negative
        if (predictedNumberOfSeats < 0) {
            System.out.println("Cannot set number of seats to a negative number.");
            // if error is found, reject the argument value
            predictedNumberOfSeats = this.projectedNumberOfSeats;
        }

        this.projectedNumberOfSeats = predictedNumberOfSeats;
    }

    /**
	 * Setter for projectedPercentageOfVotes
	 * Sets the projectedPercentageOfVotes to float specified in argument
     * Also runs validation before setting the variable
     * 
     * @param predictedPercentageOfVotes The predicted percent of votes that will be set
	 */
    public void setProjectedPercentageOfVotes(float predictedPercentageOfVotes) {
        // validating the argument
        if (predictedPercentageOfVotes < 0)  {
            System.out.println("Percentage of votes must be greater than 0");
            predictedPercentageOfVotes = this.projectedPercentageOfVotes;
        }
        if (predictedPercentageOfVotes > 1) {
            System.out.println("Percentage of votes must be less than 1.");
            predictedPercentageOfVotes = this.projectedPercentageOfVotes;
        }

        this.projectedPercentageOfVotes = predictedPercentageOfVotes;
    }


    /**
	 * toString() method
	 * Creates a String with the name of the party, its Color value, projected votes, 
     * and projected seats in the requested format.
	 * If no Color value is provided, this field is omitted
     * 
	 * @return a String with the above values in a specific format
	 */
    public String toString() {
        // convert to percentage value
        int projectedVotes = (int)(this.projectedPercentageOfVotes * 100);

        // if no party colour is set
        if (this.partyColour == null) {
            return (this.name + " (" + projectedVotes + "% of votes, " + 
                    this.projectedNumberOfSeats + " seats)");
        }

        // otherwise extract the RGB values from the party colour
        int blueValue = (int) partyColour.getBlue();
        int greenValue = (int) partyColour.getGreen();
        int redValue = (int) partyColour.getRed();
        
        return (this.name + " ([" + redValue + "," + greenValue + "," + blueValue + "], " + 
                projectedVotes + "% of votes, " + this.projectedNumberOfSeats + " seats)");
    }


    /**
	 * projectedPercentOfSeats() method
     * Takes the total number of seats, validates it, then calculates the
	 * percentage of seats the party is expected to win as a value between 0-1
     * 
     * @param totalSeatsAvailable the total number of seats in parliament
     * 
	 * @return the percentage of seats the party is expected to win
	 */
    public double projectedPercentOfSeats(int totalSeatsAvailable) {
        // validating the argument
        if (totalSeatsAvailable < 0) {
            System.out.println("Can't have 0 or less seats in election");
            return 0;
        }
        if (totalSeatsAvailable == 0) {
            System.out.println("Can't divide by 0");
            return 0;
        }
        return ((this.projectedNumberOfSeats)/totalSeatsAvailable);
    }


    /**
	 * textVisualizationBySeats() method
     * Calls the helper method which then takes in given information and creates
     * a visual representation of the party
     * 
     * @param maxStars the maximum number of stars that should be displayed on a line
     * @param numOfSeatsPerStar the number of seats represented by a single star
     * 
	 * @return the result of the helper method called
	 */
    public String textVisualizationBySeats(int maxStars, double numOfSeatsPerStar) {
        return visualizationHelper(this.projectedNumberOfSeats, numOfSeatsPerStar, maxStars);
    }


    /**
	 * textVisualizationByVotes() method
     * Calls the helper method which then takes in given information and creates
     * a visual representation of the party
     * 
     * @param maxStars the maximum number of stars that should be displayed on a line
     * @param numOfVotesPerStar the number of votes represented by a single star
     * 
	 * @return the result of the helper method called
	 */
    public String textVisualizationByVotes(int maxStars, double numOfVotesPerStar) {
        return visualizationHelper((this.projectedPercentageOfVotes * 100), numOfVotesPerStar, maxStars);  
    }


    /**
	 * visualizationHelper() method
     * A private helper method that contains the logic for displaying a visual for a
     * party. Prints a row of stars that represents the expected number of seats and a bar 
     * to indicate the number of seats needed for a majority in parliament
     * 
     * @param predictedValue the predicted number of seats the party will win
     * @param valuePerStar the number of seats each star represents
     * @param maxStars the maximum number of stars that should be displayed on a line
     * 
	 * @return a String that gives a visual representation of the data, along with party info
	 */
    private String visualizationHelper(double predictedValue, double valuePerStar, int maxStars) {
        String result = "";
        int totalNumberOfStars = (int)(predictedValue/valuePerStar);
        int numberOfStarsLeft = 0;

        // check how many seats, and stars, are required to win majority
        double majority = ((maxStars * valuePerStar)/2);
        double majorityStars = Math.ceil(majority/valuePerStar);

        boolean willGetMajority = (predictedValue >= majority) ? true : false;

        // if the party has enough seats
        if (willGetMajority) {
            for (int i = 0; i < totalNumberOfStars; i++) {
                // once the majority has been hit, add bar then continue
                if (i == majorityStars) result += "|";
                result += "*";
            }
            // if majority happens to be exactly half
            if (majorityStars == totalNumberOfStars) result += "|";

            // add spaces in order to reach maxStars
            numberOfStarsLeft = maxStars - (result.length());
            for (int i = 0; i <= numberOfStarsLeft; i++) result += " ";

            return result + " " + toString();
        }

        // if the party does not have enough seats
        else {
            // check how many seats are missing to reach majority
            double missingStars = Math.ceil(((majority - predictedValue)/valuePerStar));

            // print out stars, then missing seats, then bar to indicate majority
            for (int i = 0; i < totalNumberOfStars; i++) result += "*";
            for (int j = 0; j < missingStars; j++) result += " ";
            result += "|";

            // add spaces in order to reach maxStars
            numberOfStarsLeft = maxStars - (result.length());
            for (int i = 0; i <= numberOfStarsLeft; i++) result += " ";
        }

        // return a String that consists of the visual, and party information
        return result + " " + toString();
    }

}