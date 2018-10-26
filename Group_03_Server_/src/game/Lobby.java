package game;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import main.MainServer;

/**
 * The Class Server.
 */
public class Lobby implements Serializable {
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

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Lobby s = new Lobby();
		s.runServer();
	}

	public void runServer() throws IOException, ClassNotFoundException {
		registerServer();
		new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(PORT);
				System.out.println("Server waiting for connections...");
				while (morePlayersCanJoin) {
					clientNumber++;

					player1 = new Player(clientNumber);
					listOfPlayers.add(player1);// Adding the new player to the list listOfPlayers
					Socket socket1 = serverSocket.accept();
					DataOutputStream objectOutputStream = new DataOutputStream(socket1.getOutputStream());
					objectOutputStream.writeInt(MAX_USERS - clientNumber);

					System.out.println("User " + clientNumber + " is now connected");
					clientNumber++;
					player2 = new Player(clientNumber);
					listOfPlayers.add(player2);// Adding the new player to the list listOfPlayers
					Socket socket2 = serverSocket.accept();
					DataOutputStream objectOutputStream1 = new DataOutputStream(socket2.getOutputStream());
					objectOutputStream1.writeInt(MAX_USERS - clientNumber);

					System.out.println("User " + clientNumber + " is now connected");
					clientNumber++;
					player3 = new Player(clientNumber);
					listOfPlayers.add(player3);// Adding the new player to the list listOfPlayers
					Socket socket3 = serverSocket.accept();
					DataOutputStream objectOutputStream2 = new DataOutputStream(socket3.getOutputStream());
					objectOutputStream2.writeInt(MAX_USERS - clientNumber);

					System.out.println("User " + clientNumber + " is now connected");
					clientNumber++;
					player4 = new Player(clientNumber);
					listOfPlayers.add(player4);// Adding the new player to the list listOfPlayers
					Socket socket4 = serverSocket.accept();
					DataOutputStream objectOutputStream3 = new DataOutputStream(socket4.getOutputStream());
					System.out.println("User " + clientNumber + " is now connected");
					objectOutputStream3.writeInt(MAX_USERS - clientNumber);

					if (clientNumber >= 4) {
						System.out.println("Limit Reached");
						morePlayersCanJoin = false;
					}
					// new ServerThread(socket, socket2).start();

					switch (clientNumber) {
					case 2:
						new Thread(new ServerThread(socket1, socket2)).start();
						break;
					case 3:
						new Thread(new ServerThread(socket1, socket2, socket3)).start();
						break;
					case 4:
						new Thread(new ServerThread(socket1, socket2, socket3, socket4)).start();
						break;
					default:
						break;
					}

				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}).start();

	}

	private void registerServer() throws UnknownHostException, IOException, ClassNotFoundException {
		// Method for establishing a connection to the MainServer
		Socket socket = new Socket(MainServer.HOST, MainServer.PORT);

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		objectOutputStream.writeObject(this);

		System.out.println((String) objectInputStream.readObject());
	}

	public class ServerThread extends Thread {
		public Socket socket1;
		public Socket socket2;
		public Socket socket3;
		public Socket socket4;

		ServerThread(Socket socket) {
			this.socket1 = socket;
		}

		ServerThread(Socket socket, Socket socket2) {
			this.socket1 = socket;
			this.socket2 = socket2;
		}

		ServerThread(Socket socket1, Socket socket2, Socket socket3) {
			this.socket1 = socket1;
			this.socket2 = socket2;
			this.socket3 = socket3;
		}

		ServerThread(Socket socket1, Socket socket2, Socket socket3, Socket socket4) {
			this.socket1 = socket1;
			this.socket2 = socket2;
			this.socket3 = socket3;
			this.socket4 = socket4;
		}

		// Declare the input and output streams
		DataInputStream objectInputStream;
		DataOutputStream objectOutputStream;
		DataInputStream objectInputStream2;
		DataOutputStream objectOutputStream2;
		DataInputStream objectInputStream3;
		DataOutputStream objectOutputStream3;
		DataInputStream objectInputStream4;
		DataOutputStream objectOutputStream4;

		public void run() {
			try {

				// This method is for when the client want's to connect to the lobby
				objectInputStream = new DataInputStream(socket1.getInputStream());
				objectOutputStream = new DataOutputStream(socket1.getOutputStream());

				System.out.println(socket1);
				System.out.println("User 1 is now connected");

				objectOutputStream.writeUTF(joinedServer);
				objectOutputStream.writeUTF(youArePlayerNumber + player1.getName());

				objectOutputStream.writeUTF(pressIfReady);

				objectInputStream2 = new DataInputStream(socket2.getInputStream());
				objectOutputStream2 = new DataOutputStream(socket2.getOutputStream());
				System.out.println(socket2);
				System.out.println("User 2 is now connected");

				objectOutputStream2.writeUTF(joinedServer);
				objectOutputStream2.writeUTF(youArePlayerNumber + player2.getName());

				objectOutputStream2.writeUTF(pressIfReady);

				objectInputStream3 = new DataInputStream(socket3.getInputStream());
				objectOutputStream3 = new DataOutputStream(socket3.getOutputStream());
				System.out.println(socket2);
				System.out.println("User 3 is now connected");

				objectOutputStream3.writeUTF(joinedServer);
				objectOutputStream3.writeUTF(youArePlayerNumber + player3.getName());

				objectOutputStream3.writeUTF(pressIfReady);

				objectInputStream4 = new DataInputStream(socket4.getInputStream());
				objectOutputStream4 = new DataOutputStream(socket4.getOutputStream());
				System.out.println(socket2);
				System.out.println("User 4 is now connected");

				objectOutputStream4.writeUTF(joinedServer);
				objectOutputStream4.writeUTF(youArePlayerNumber + player4.getName());

				objectOutputStream4.writeUTF(pressIfReady);

				if (objectInputStream.readInt() == 1) {
					System.out.println("Player number before increment from user 1: " + playerNumberReady);
					playerNumberReady++;
					System.out.println("Player number after increment from user 1: " + playerNumberReady);
					allPlayersReady = checkIfAllPlayersReady();
					if (allPlayersReady == false) {
						objectOutputStream.writeUTF(waiting);
					}
				}

				if (objectInputStream2.readInt() == 1) {
					System.out.println("Player number before increment from user 2: " + playerNumberReady);
					playerNumberReady++;
					System.out.println("Player number after increment from user 2: " + playerNumberReady);
					allPlayersReady = checkIfAllPlayersReady();
					if (allPlayersReady != true) {
						// objectOutputStream.writeUTF(waiting);
						objectOutputStream2.writeUTF(waiting);
					} else {
						objectOutputStream.writeUTF(letsGo);
						objectOutputStream2.writeUTF(letsGo);
					}
				}

				if (objectInputStream3.readInt() == 1) {
					System.out.println("Player number before increment from user 2: " + playerNumberReady);
					playerNumberReady++;
					System.out.println("Player number after increment from user 2: " + playerNumberReady);
					allPlayersReady = checkIfAllPlayersReady();
					if (allPlayersReady != true) {
						// objectOutputStream.writeUTF(waiting);
						// objectOutputStream2.writeUTF(waiting);
						objectOutputStream3.writeUTF(waiting);
					} else {
						objectOutputStream.writeUTF(letsGo);
						objectOutputStream2.writeUTF(letsGo);
						objectOutputStream3.writeUTF(letsGo);
					}
				}

				if (objectInputStream4.readInt() == 1) {
					System.out.println("Player number before increment from user 2: " + playerNumberReady);
					playerNumberReady++;
					System.out.println("Player number after increment from user 2: " + playerNumberReady);
					allPlayersReady = checkIfAllPlayersReady();
					if (allPlayersReady != true) {
						objectOutputStream.writeUTF(waiting);
					} else {
						objectOutputStream.writeUTF(letsGo);
						objectOutputStream2.writeUTF(letsGo);
						objectOutputStream3.writeUTF(letsGo);
						objectOutputStream4.writeUTF(waiting);
						objectOutputStream4.writeUTF(letsGo);
					}
				}

				// Send a bool to the client so that they stay inside a Game while loop
				objectOutputStream.writeBoolean(true);
				objectOutputStream2.writeBoolean(true);
				objectOutputStream3.writeBoolean(true);
				objectOutputStream4.writeBoolean(true);

				while (allPlayersReady) {

					while (YOUHAVEWON != true) {

						rollTheDice();

					}
					if (YOUHAVEWON == true) {

						gameEnded();

					}

				}
			} catch (EOFException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public boolean checkIfAllPlayersReady() {

			if (playerNumberReady != MAX_USERS) {
				return false;
			} else {
				return true;
			}

		}

		public void gameEnded() throws IOException, InterruptedException {
			objectOutputStream.writeUTF("The game has ended");
			objectOutputStream2.writeUTF("The game has ended");
			objectOutputStream3.writeUTF("The game has ended");
			objectOutputStream4.writeUTF("The game has ended");
		}

		public void rollTheDice() throws IOException, InterruptedException {
			objectOutputStream.writeUTF("Press 'r' to roll the dice");
			objectOutputStream2.writeUTF("Press 'r' to roll the dice");
			objectOutputStream3.writeUTF("Press 'r' to roll the dice");
			objectOutputStream4.writeUTF("Press 'r' to roll the dice");

			// READ INPUT FROM USER AND SEE IF IT IS 'r'
			if (objectInputStream.readChar() == roll && objectInputStream2.readChar() == roll
					&& objectInputStream3.readChar() == roll && objectInputStream4.readChar() == roll) {
				dice = new Dice(clientNumber); // Creating a new dice for getting different rolls.

				// Roll once for each player
				for (int i = 0; i < clientNumber; i++) {
					dice.roll();
					// System.out.println(listOfPlayers.get(i).getName() + " rolled a " +
					// dice.getRollsFromList(i));
					objectOutputStream.writeUTF(listOfPlayers.get(i).getName() + rolledA + dice.getRollsFromList(i));
					objectOutputStream2.writeUTF(listOfPlayers.get(i).getName() + rolledA + dice.getRollsFromList(i));
					objectOutputStream3.writeUTF(listOfPlayers.get(i).getName() + rolledA + dice.getRollsFromList(i));
					objectOutputStream4.writeUTF(listOfPlayers.get(i).getName() + rolledA + dice.getRollsFromList(i));
					Thread.sleep(1000);
				}

				int max = Collections.max(dice.listWithRolls); // Finding the max of the rolls.

				Thread.sleep(1000);
				// This is where the scoring happens
				outerloop: for (int i = 0; i < clientNumber; i++) {

					// if not player i has max, go back and search for next player.
					if (dice.getRollsFromList(i) != max) {
						// System.out.println(listOfPlayers.get(i).getName() + "did not roll the
						// highest");
						Thread.sleep(1000);
					}
					// Is roll i max?
					if (dice.getRollsFromList(i) == max) {
						// Checking if others also have max
						for (int j = i + 1; j < clientNumber; j++) {

							// If no one else has max, and will (should) only give point if every player has
							// been checked, hence the j == num...
							if (dice.getRollsFromList(i) != dice.getRollsFromList(j) && j == (clientNumber - 1)) {
								listOfPlayers.get(i).addScore();
								objectOutputStream.writeBoolean(noElseHasMax = true);
								objectOutputStream2.writeBoolean(noElseHasMax = true);
								objectOutputStream3.writeBoolean(noElseHasMax = true);
								objectOutputStream4.writeBoolean(noElseHasMax = true);
								// System.out.println((i + 1) + rolledTheHighestValue);
								objectOutputStream.writeUTF((i + 1) + rolledTheHighestValue);
								objectOutputStream2.writeUTF((i + 1) + rolledTheHighestValue);
								objectOutputStream3.writeUTF((i + 1) + rolledTheHighestValue);
								objectOutputStream4.writeUTF((i + 1) + rolledTheHighestValue);

								break outerloop;
							}

							// If another player also has rolled the max, a point will be subtracted.
							else if (dice.getRollsFromList(i) == dice.getRollsFromList(j)) {
								objectOutputStream.writeBoolean(noElseHasMax = false);
								objectOutputStream2.writeBoolean(noElseHasMax = false);
								objectOutputStream3.writeBoolean(noElseHasMax = false);
								objectOutputStream4.writeBoolean(noElseHasMax = false);
								objectOutputStream
										.writeUTF(listOfPlayers.get(i).getName() + and + listOfPlayers.get(j).getName()
												+ bothRolledHighestValue + max + bothLosePoint);
								objectOutputStream2
										.writeUTF(listOfPlayers.get(i).getName() + and + listOfPlayers.get(j).getName()
												+ bothRolledHighestValue + max + bothLosePoint);
								objectOutputStream3
										.writeUTF(listOfPlayers.get(i).getName() + and + listOfPlayers.get(j).getName()
												+ bothRolledHighestValue + max + bothLosePoint);
								objectOutputStream4
										.writeUTF(listOfPlayers.get(i).getName() + and + listOfPlayers.get(j).getName()
												+ bothRolledHighestValue + max + bothLosePoint);
								listOfPlayers.get(i).subScore();
								listOfPlayers.get(j).subScore();
								break outerloop;
							}

						}
						// If it's the last player, who has max:
						/*
						 * TODO: CAN BE OPTIMIZED, if: for (int i = 0; i < numberOfPlayers; i++) ==> for
						 * (int i = numberOfPlayers; i >=0; i--). And the same for j. Try it out!
						 */
						if (i == clientNumber - 1) {
							System.out.println("ifLastPlayerHasMax: " + ifLastPlayerHasMax);
							objectOutputStream.writeBoolean(ifLastPlayerHasMax = true);
							objectOutputStream2.writeBoolean(ifLastPlayerHasMax = true);
							objectOutputStream3.writeBoolean(ifLastPlayerHasMax = true);
							objectOutputStream4.writeBoolean(ifLastPlayerHasMax = true);
							listOfPlayers.get(i).addScore();
							objectOutputStream.writeUTF((i + 1) + rolledTheHighestValue);
							objectOutputStream2.writeUTF((i + 1) + rolledTheHighestValue);
							objectOutputStream3.writeUTF((i + 1) + rolledTheHighestValue);
							objectOutputStream4.writeUTF((i + 1) + rolledTheHighestValue);

							break outerloop;
						} else {
							System.out.println("ifLastPlayerHasMax: " + ifLastPlayerHasMax);
							objectOutputStream.writeBoolean(ifLastPlayerHasMax = false);
							objectOutputStream2.writeBoolean(ifLastPlayerHasMax = false);
							objectOutputStream3.writeBoolean(ifLastPlayerHasMax = false);
							objectOutputStream4.writeBoolean(ifLastPlayerHasMax = false);

							break outerloop;
						}
					}

				}
				// Printing out the scoring sheet:
				objectOutputStream.writeUTF(scoringList);
				objectOutputStream2.writeUTF(scoringList);
				objectOutputStream3.writeUTF(scoringList);
				objectOutputStream4.writeUTF(scoringList);
				for (int i = 0; i < clientNumber; i++) {
					objectOutputStream.writeUTF(
							listOfPlayers.get(i).getName() + totalScore + listOfPlayers.get(i).getTotalScore());
					objectOutputStream2.writeUTF(
							listOfPlayers.get(i).getName() + totalScore + listOfPlayers.get(i).getTotalScore());
					objectOutputStream3.writeUTF(
							listOfPlayers.get(i).getName() + totalScore + listOfPlayers.get(i).getTotalScore());
					objectOutputStream4.writeUTF(
							listOfPlayers.get(i).getName() + totalScore + listOfPlayers.get(i).getTotalScore());

					if (listOfPlayers.get(i).getTotalScore() == scoreToWin) {
						YOUHAVEWON = true;
					} else {
						YOUHAVEWON = false;
					}

				}
				objectOutputStream.writeBoolean(ifLastPlayerHasMax = false);
				objectOutputStream2.writeBoolean(ifLastPlayerHasMax = false);
				objectOutputStream3.writeBoolean(ifLastPlayerHasMax = false);
				objectOutputStream4.writeBoolean(ifLastPlayerHasMax = false);
			}

		}

	}

}
