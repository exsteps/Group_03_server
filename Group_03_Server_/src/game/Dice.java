package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// WORKING DIE - everything is checked
public class Dice { 
	// Defining...
	private int sides; // Number of sides on the die
	public int faceValue; // the value of the roll
	public List<Integer> listWithRolls = new ArrayList<>(); // The list, which contains the different face values.

	// Creating new random object, to create a random roll
	Random random = new Random();

	// Constructor, that takes in the number of players
	public Dice(int players) {
		// There will always be one more side on the die than there are players
		sides = players *2;
	}

	// Getting the size of the dice (how many faces).
	public int getDiceSize() {
		return this.sides;
	}

	// Rolling the die
	public void roll() throws InterruptedException {
		// Value for face. Take in sides as argument, which sets the max. 1 is min.
		faceValue = random.nextInt(sides) + 1;
		Thread.sleep(100);
		listWithRolls.add(faceValue);
	}

	// Getting the faceValue of the specific i spot in list
	public int getRollsFromList(int i) {
		return listWithRolls.get(i);
	}

	// Getting the size of the roll list. Should be the same as number of players
	public int getListRollSize() {
		return listWithRolls.size();
	}


}