package game;
//import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import main.MainServer;

public class BoardGameClient extends VariableClass implements Serializable {
	private static final long serialVersionUID = -6224L;


	public static void main(String[] args) {

		BoardGameClient c = new BoardGameClient();

		try {

			c.joinServer();

		} catch (ClassNotFoundException | IOException e) {

			System.out.println("Failed to join server.");
			e.printStackTrace();
		}
	}

	public void joinServer() throws UnknownHostException, IOException, ClassNotFoundException {

		
		
		try {
		socket = new Socket(Lobby.HOST, Lobby.PORT);
		DataOutputStream objectOutputStream = new DataOutputStream(socket.getOutputStream());
		DataInputStream objectInputStream = new DataInputStream(socket.getInputStream());
		System.out.println("Trying to establish connection...");
		while(true) {
//		objectOutputStream.writeObject(this);
		System.out.println("You have connected to the Lobby");
		System.out.println("Please wait for more players... Missing " + objectInputStream.readInt() + " players");
//		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println(objectInputStream.readUTF());
		System.out.println(objectInputStream.readUTF());
		System.out.println(objectInputStream.readUTF());
		
		int ready = input.nextInt();
		objectOutputStream.writeInt(ready);
		System.out.println(waiting);

		
		// Receive a bool so the client stays inside the game while loop
		gameStarted = objectInputStream.readBoolean();
		
		// If gameStarted is true, print out 'Lets GO' message once
		if(gameStarted == true) {
			System.out.println(letsGo);
		}
		
		// IF THE GAME HAS STARTED
		while(gameStarted) {

		// Receive instructions to roll the dice
		System.out.println(objectInputStream.readUTF());

		// Receive 'r' from clients input and send it to the lobby
		String roll = input.next();
		objectOutputStream.writeChars(roll);

		// Receive info what each player rolled
		for(int i = 0; i < 4; i++) {
		System.out.println(objectInputStream.readUTF());
		}
		
		// If no one else had max
		if(objectInputStream.readBoolean() == true) {
		// Receive info of who rolled the highest value
		System.out.println(objectInputStream.readUTF());
		
		//Receive info of scoring list
		for(int i = 0; i < 5; i++) {
		System.out.println(objectInputStream.readUTF());
		}
		
		// Else if someone else has max
	} else {
		System.out.println(objectInputStream.readUTF());
		//Receive info of scoring list
		for(int i = 0; i < 5; i++) {
		System.out.println(objectInputStream.readUTF());
		}
	}
		// if last player has the max value
		if(objectInputStream.readBoolean() == true) {
		// Receive info of who rolled the highest value
		System.out.println(objectInputStream.readUTF());
		
		//Receive info of scoring list
		for(int i = 0; i < 5; i++) {
		System.out.println(objectInputStream.readUTF());
		} 
		
	} else {
		
	}
		
		
		}
		}
//			objectOutputStream.writeObject(name + ": " + inputReader.readLine());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		}
}