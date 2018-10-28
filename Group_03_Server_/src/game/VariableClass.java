package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VariableClass {
// -------------- VARIABLES FOR LOBBY CLASS ----------------
	private static final long serialVersionUID = -21654L;
	public static final int PORT = 4445;
	public static final int MAX_USERS = 4;
	public static final String HOST = "localhost";
	public String name = "Lobby Server";
	static DataInputStream in;
	static DataOutputStream out;
	public int clientNumber = 0;
	public int playerNumberReady = 0;
	public boolean allPlayersReady = false;
	public boolean OddurIsNice = false;
	public String joinedServer = "Game Session started!";
	public String waiting = "Waiting...";
	public String letsGo = "LETS GO";
	public String youArePlayerNumber = "You are ";
	public String pressIfReady = "Press '1' if you are ready";
	public boolean morePlayersCanJoin = true;
	public Player player1;
	public Player player2;
	public Player player3;
	public Player player4;
	public Dice dice;
	public String rolledA = " rolled a ";
	public String rolledTheHighestValue = " rolled the highest value: +1 point.";
	public String scoringList = "\nScoring list:\n";
	public String totalScore = " - total score: ";
	public String bothRolledHighestValue = " both rolled the highest value:";
	public String bothLosePoint = "\n Both lose a point (if not already on 0).";
	public String and = " and ";
	public boolean wrongInput = false;
	List<Player> listOfPlayers = new ArrayList<Player>(); // The list, which holds the objects Player
	int scoreToWin = 4; // The score, that a player has to reach to win
	public char roll = 'r';
	public boolean YOUHAVEWON = false;
	public boolean noElseHasMax = false;
	public boolean ifLastPlayerHasMax = false;
	public static String winningPlayer;
	public String gameHasEnded = "The game has ended. ";
	public String pressToRollDice = "Press 'r' to roll the dice. The dice will be rolled when all players have pressed 'r'.";

// --------------------- VARIABLES FOR CLIENT CLASS ------------	
	
	public transient Socket socket;
	public transient Scanner input = new Scanner(System.in);	
	public boolean gameStarted;
	public boolean correctInput = false;
	
	

}
