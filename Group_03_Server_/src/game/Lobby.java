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
 * The Class Lobby.
 */
public class Lobby extends VariableClass implements Serializable {


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
		DataInputStream streamFromPlayer1;
		DataOutputStream streamToPlayer1;
		DataInputStream streamFromPlayer2;
		DataOutputStream streamToPlayer2;
		DataInputStream streamFromPlayer3;
		DataOutputStream streamToPlayer3;
		DataInputStream streamFromPlayer4;
		DataOutputStream streamToPlayer4;

		public void run() {
			try {

				// This method is for when the client want's to connect to the lobby
				streamFromPlayer1 = new DataInputStream(socket1.getInputStream());
				streamToPlayer1 = new DataOutputStream(socket1.getOutputStream());

				// Info printed that a client has connected
				System.out.println("User 1 is now connected");

				// Info sent to player that he has connected to lobby, and his player number
				streamToPlayer1.writeUTF(joinedServer);
				streamToPlayer1.writeUTF(youArePlayerNumber + player1.getName());
				
				// Info sent to player that he should press 'r' to confirm he's ready
				streamToPlayer1.writeUTF(pressIfReady);

//     ---------------------------------------------------------------------------------
				
				// This method is for when the client want's to connect to the lobby
				streamFromPlayer2 = new DataInputStream(socket2.getInputStream());
				streamToPlayer2 = new DataOutputStream(socket2.getOutputStream());
				// Info printed that a client has connected
				System.out.println("User 2 is now connected");
				
				// Info sent to player that he has connected to lobby, and his player number
				streamToPlayer2.writeUTF(joinedServer);
				streamToPlayer2.writeUTF(youArePlayerNumber + player2.getName());

				// Info sent to player that he should press 'r' to confirm he's ready
				streamToPlayer2.writeUTF(pressIfReady);
				
//		---------------------------------------------------------------------------------
				
				// This method is for when the client want's to connect to the lobby
				streamFromPlayer3 = new DataInputStream(socket3.getInputStream());
				streamToPlayer3 = new DataOutputStream(socket3.getOutputStream());
				
				// Info sent to player that he has connected to lobby, and his player number
				System.out.println("User 3 is now connected");

				// Info sent to player that he has connected to lobby, and his player number
				streamToPlayer3.writeUTF(joinedServer);
				streamToPlayer3.writeUTF(youArePlayerNumber + player3.getName());
				
				// Info sent to player that he should press 'r' to confirm he's ready
				streamToPlayer3.writeUTF(pressIfReady);

//		---------------------------------------------------------------------------------				
				
				// This method is for when the client want's to connect to the lobby
				streamFromPlayer4 = new DataInputStream(socket4.getInputStream());
				streamToPlayer4 = new DataOutputStream(socket4.getOutputStream());
				
				// Info sent to player that he has connected to lobby, and his player number
				System.out.println("User 4 is now connected");

				// Info sent to player that he has connected to lobby, and his player number
				streamToPlayer4.writeUTF(joinedServer);
				streamToPlayer4.writeUTF(youArePlayerNumber + player4.getName());

				// Info sent to player that he should press 'r' to confirm he's ready
				streamToPlayer4.writeUTF(pressIfReady);

//		---------------------------------------------------------------------------------				
				
// 		THIS SECTIONS CHECKS IF THE CORRECT INPUT IS RECEIVED
				
				while(allPlayersReady != true) {
					if (streamFromPlayer1.readInt() == 1) {
						playerNumberReady++;
						// This function checks if all players are ready
						allPlayersReady = checkIfAllPlayersReady();
					}

					if (streamFromPlayer2.readInt() == 1) {
						playerNumberReady++;
						allPlayersReady = checkIfAllPlayersReady();
					}

					if (streamFromPlayer3.readInt() == 1) {
						playerNumberReady++;
						allPlayersReady = checkIfAllPlayersReady();
					}

					if (streamFromPlayer4.readInt() == 1) {
						playerNumberReady++;
						allPlayersReady = checkIfAllPlayersReady();
					}
				}
				// Send a boolean to the client so that they stay inside a Game while loop
				streamToPlayer1.writeBoolean(true);
				streamToPlayer2.writeBoolean(true);
				streamToPlayer3.writeBoolean(true);
				streamToPlayer4.writeBoolean(true);

//		---------------------------------------------------------------------------------				
//		THIS SECTIONS IS WHEN ALL PLAYERS ARE READY AND THE GAME IS RUNNING
				
				
				// while all players are ready (game is running)
				while (allPlayersReady) {
					
					// while no one has one the game function is called
					while (YOUHAVEWON != true) {
						rollTheDice();
					}
					// if a player has won, the gameEnded function is called
					if (YOUHAVEWON == true) {
						gameEnded();
						YOUHAVEWON = false; // boolean set to false, so it doesn't loop
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

		// Function to check if all players are ready
		public boolean checkIfAllPlayersReady() {
			// when max number of users has not been reached it returns false
			if (playerNumberReady != MAX_USERS) { 
				return false;
			} else {
				return true;
			}

		}

		// Function that is run when there is a winner
		public void gameEnded() throws IOException, InterruptedException {
			streamToPlayer1.writeUTF(gameHasEnded + winningPlayer);
			streamToPlayer2.writeUTF(gameHasEnded + winningPlayer);
			streamToPlayer3.writeUTF(gameHasEnded + winningPlayer);
			streamToPlayer4.writeUTF(gameHasEnded + winningPlayer);
		}

		// function that runs the game
		public void rollTheDice() throws IOException, InterruptedException {
			streamToPlayer1.writeUTF(pressToRollDice);
			streamToPlayer2.writeUTF(pressToRollDice);
			streamToPlayer3.writeUTF(pressToRollDice);
			streamToPlayer4.writeUTF(pressToRollDice);

			// READ INPUT FROM USER AND SEE IF IT IS 'r'
			if (streamFromPlayer1.readChar() == roll && streamFromPlayer2.readChar() == roll
					&& streamFromPlayer3.readChar() == roll && streamFromPlayer4.readChar() == roll) {
				dice = new Dice(clientNumber); // Creating a new dice for getting different rolls.

				// Roll once for each player
				for (int i = 0; i < clientNumber; i++) {
					dice.roll();

					// Send to every player what each player rolled
					streamToPlayer1.writeUTF(listOfPlayers.get(i).getName() + rolledA + dice.getRollsFromList(i));
					streamToPlayer2.writeUTF(listOfPlayers.get(i).getName() + rolledA + dice.getRollsFromList(i));
					streamToPlayer3.writeUTF(listOfPlayers.get(i).getName() + rolledA + dice.getRollsFromList(i));
					streamToPlayer4.writeUTF(listOfPlayers.get(i).getName() + rolledA + dice.getRollsFromList(i));
					Thread.sleep(1000);
				}

				int max = Collections.max(dice.listWithRolls); // Finding the max of the rolls.

				Thread.sleep(1000);
				// This is where the scoring happens
				outerloop: for (int i = 0; i < clientNumber; i++) {

					// if not player i has max, go back and search for next player.
					if (dice.getRollsFromList(i) != max) {

						Thread.sleep(1000);
					}
					// Is roll i max?
					if (dice.getRollsFromList(i) == max) {
						// Checking if others also have max
						for (int j = i + 1; j < clientNumber; j++) {

							// If no one else has max, and will (should) only give point if every player has
							// been checked, hence the j == num...
							if (dice.getRollsFromList(i) != dice.getRollsFromList(j) && j == (clientNumber - 1)) {
								listOfPlayers.get(i).addScore(); // score is added to player that rolled the highest number
								
								// Boolean sent to the client so the correct input stream is read on the client side
								streamToPlayer1.writeBoolean(noElseHasMax = true);
								streamToPlayer2.writeBoolean(noElseHasMax = true);
								streamToPlayer3.writeBoolean(noElseHasMax = true);
								streamToPlayer4.writeBoolean(noElseHasMax = true);
								
								// Info sent to user who rolled the highest value and that user gets a point
								streamToPlayer1.writeUTF(listOfPlayers.get((i)).getName() + rolledTheHighestValue);
								streamToPlayer2.writeUTF(listOfPlayers.get((i)).getName()  + rolledTheHighestValue);
								streamToPlayer3.writeUTF(listOfPlayers.get((i)).getName() + rolledTheHighestValue);
								streamToPlayer4.writeUTF(listOfPlayers.get((i)).getName()  + rolledTheHighestValue);

								break outerloop;
							}

							// If another player also has rolled the max, a point will be subtracted.
							else if (dice.getRollsFromList(i) == dice.getRollsFromList(j)) {
								
								// Boolean sent to the client so the correct input stream is read on the client side
								streamToPlayer1.writeBoolean(noElseHasMax = false);
								streamToPlayer2.writeBoolean(noElseHasMax = false);
								streamToPlayer3.writeBoolean(noElseHasMax = false);
								streamToPlayer4.writeBoolean(noElseHasMax = false);
								
								// Info sent to user which two users rolled the highest value and that a point will be subtracted from them
								streamToPlayer1.writeUTF(listOfPlayers.get(i).getName() + and + listOfPlayers.get(j).getName() + bothRolledHighestValue + max + bothLosePoint);
								streamToPlayer2.writeUTF(listOfPlayers.get(i).getName() + and + listOfPlayers.get(j).getName() + bothRolledHighestValue + max + bothLosePoint);
								streamToPlayer3.writeUTF(listOfPlayers.get(i).getName() + and + listOfPlayers.get(j).getName() + bothRolledHighestValue + max + bothLosePoint);
								streamToPlayer4.writeUTF(listOfPlayers.get(i).getName() + and + listOfPlayers.get(j).getName() + bothRolledHighestValue + max + bothLosePoint);
								
								// remove 1 point from those two players
								listOfPlayers.get(i).subScore();
								listOfPlayers.get(j).subScore();
								break outerloop;
							}

						}
						
						// If it's the last player, who has max:
						if (i == clientNumber - 1) {
							
							// Boolean sent to the client so the correct input stream is read on the client side
							streamToPlayer1.writeBoolean(ifLastPlayerHasMax = true);
							streamToPlayer2.writeBoolean(ifLastPlayerHasMax = true);
							streamToPlayer3.writeBoolean(ifLastPlayerHasMax = true);
							streamToPlayer4.writeBoolean(ifLastPlayerHasMax = true);
							
							// add a point to that player
							listOfPlayers.get(i).addScore();
							
							// Info sent to user who rolled the highest value and that user gets a point
							streamToPlayer1.writeUTF(listOfPlayers.get((i)).getName() + rolledTheHighestValue);
							streamToPlayer2.writeUTF(listOfPlayers.get((i)).getName() + rolledTheHighestValue);
							streamToPlayer3.writeUTF(listOfPlayers.get((i)).getName() + rolledTheHighestValue);
							streamToPlayer4.writeUTF(listOfPlayers.get((i)).getName() + rolledTheHighestValue);

							break outerloop;
						} else {
							// Boolean sent to the client so the correct input stream is read on the client side
							streamToPlayer1.writeBoolean(ifLastPlayerHasMax = false);
							streamToPlayer2.writeBoolean(ifLastPlayerHasMax = false);
							streamToPlayer3.writeBoolean(ifLastPlayerHasMax = false);
							streamToPlayer4.writeBoolean(ifLastPlayerHasMax = false);

							break outerloop;
						}
					}

				}
				// Printing out the scoring sheet:
				streamToPlayer1.writeUTF(scoringList);
				streamToPlayer2.writeUTF(scoringList);
				streamToPlayer3.writeUTF(scoringList);
				streamToPlayer4.writeUTF(scoringList);
				
				// For loop to send info of the total score of each player
				for (int i = 0; i < clientNumber; i++) {
					streamToPlayer1.writeUTF(listOfPlayers.get(i).getName() + totalScore + listOfPlayers.get(i).getTotalScore());
					streamToPlayer2.writeUTF(listOfPlayers.get(i).getName() + totalScore + listOfPlayers.get(i).getTotalScore());
					streamToPlayer3.writeUTF(listOfPlayers.get(i).getName() + totalScore + listOfPlayers.get(i).getTotalScore());
					streamToPlayer4.writeUTF(listOfPlayers.get(i).getName() + totalScore + listOfPlayers.get(i).getTotalScore());

				}
				
				// For loop to check if any player has reached the score needed to win
				for(int i = 0; i < clientNumber; i++) {
					
					// if a player has reached the score needed to win
					if (listOfPlayers.get(i).getTotalScore() == scoreToWin) {
						YOUHAVEWON = true; // this boolean becomes true
						
						// The name of the winning player + info that he has won the game is assigned to this String variable
						winningPlayer = listOfPlayers.get(i).getName() + " won the game";
					break;
						// if no one has won, this bool stays false
					} else {
						YOUHAVEWON = false;
					}
				}
				
				// Boolean sent to the client so the correct input stream is read on the client side
				streamToPlayer1.writeBoolean(ifLastPlayerHasMax = false);
				streamToPlayer2.writeBoolean(ifLastPlayerHasMax = false);
				streamToPlayer3.writeBoolean(ifLastPlayerHasMax = false);
				streamToPlayer4.writeBoolean(ifLastPlayerHasMax = false);
			}

		}

	}

}
