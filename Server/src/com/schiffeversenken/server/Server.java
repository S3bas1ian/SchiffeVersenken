package com.schiffeversenken.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	ServerSocket serverSocket;
	ArrayList<Player> players = new ArrayList<Player>();
	boolean shouldRun = true;

	Player playerA;
	Player playerB;
	private Player currentPlayer; // der Server muss wissen, wer gerade am Zug ist

	public static void main(String[] args) {
		new Server(8000);
	}

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			while (playerB == null) { // solange noch nicht beide Spieler angemeldet sind, soll auf Spieler gewartet
										// werden
				Socket s = serverSocket.accept();
				ObjectInputStream din = new ObjectInputStream(s.getInputStream());
				String id = din.readUTF(); // beim Verbinden übermittelt der Client eine PlayerId

				// weist ID einen Spieler zu
				if (playerA == null) {
					playerA = new Player(id, s, this, din);
					currentPlayer = playerA;
				} else if (playerA != null && playerB == null) {
					playerB = new Player(id, s, this, din);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public String getCurrentPlayerID() {
		return currentPlayer.getID();
	}

	public void switchCurrentPlayer() {
		if (currentPlayer.getID().equals(playerA.getID())) {
			currentPlayer = playerB;
		} else {
			currentPlayer = playerA;
		}
	}

	public void beenden() {
		// den Server beenden
	}
}
