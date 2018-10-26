package game;
public class Player {

	private int playerNumber; // player "name"
	public int totalScore;

	public Player(int playerNumber) {
		this.playerNumber = playerNumber;
		totalScore= 0;
	}

	public String getName() {
		return "Player no. " + playerNumber;
	}

	public void addScore() {
		totalScore = totalScore+1;
	// 	if(totalScore == scoreToWin) return with System.out.println("YOU WON!!)	
	}

	public void subScore() {
		totalScore = totalScore - 1;
		if(totalScore < 0) totalScore = 0;
	}

	public int getTotalScore() {
		return totalScore;
	}

}