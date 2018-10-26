package main;


import java.net.*;
import java.io.*;
import java.util.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import game.Lobby;

public class MainServer {

	public static final int PORT = 4444;
	public static final String HOST = "localhost";
	public ArrayList<Lobby> serverList = new ArrayList<>();
	boolean lobbyConnected = true;
	int lobbyCount = 0;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Creating the main server and running it using the method runServer()
		new MainServer().runServer();
	}

	public void runServer() throws IOException, ClassNotFoundException {
		
	
		// Creating the server on the stated port
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Main Server initiated.");

		
		while (true) {

			// a socket to accept the lobbys connection 
			Socket socket = serverSocket.accept();

			try {
				
				// Establishing the connection to the Lobby server and then adding it to its list
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject("Lobby created successfully.");
				Lobby s = (Lobby) objectInputStream.readObject();
				this.serverList.add(s);
				System.out.println("Server \"" + s.name + "\" added to game list.");
				

				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}