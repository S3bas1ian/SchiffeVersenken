package com.schiffeversenken.server;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Player extends Thread {

	private Socket socket;
	private Server server;
	private ObjectInputStream dataInputStream;
	private ObjectOutputStream dataOutputStream;
	private boolean shouldRun = true;
	private String id;
	private ArrayList<Point[]> meineSchiffe;
	private ArrayList<Point> shots = new ArrayList<>();

	private int trefferCounter = 0;

	public Player(String id, Socket socket, Server server, ObjectInputStream dataInputStream) {
		this.socket = socket;
		this.server = server;
		this.id = id;
		this.dataInputStream = dataInputStream;

		waitForShips(); // bevor der Spieler irgendwas machen kann, muss der Server erst die
						// Schiffspositionen bekommen
		start();
	}

	@SuppressWarnings("unchecked")
	private void waitForShips() {
		while (meineSchiffe == null) {
			try {
				meineSchiffe = (ArrayList<Point[]>) dataInputStream.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public int getShipLength() {
		int l = 0;
		for (Point[] element : meineSchiffe) {
			l += element.length;
		}
		return l;
	}

	public void sendShip(Point p) {
		try {
			dataOutputStream.writeObject(p);
			dataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player getEnemy() {
		if (server.playerA.getID().equals(id)) {
			return server.playerB;
		} else {
			return server.playerA;
		}
	}

	public ArrayList<Point[]> getShips() {
		return meineSchiffe;
	}

	/**
	 * überprüft ob Position Point in meineSchiffe enthalten ist
	 * 
	 * @param point
	 * @return true wenn Schiff "getroffen" wurde
	 */

	public boolean isShip(Point point) {
		for (Point[] element : meineSchiffe) {
			for (Point element2 : element) {
				if (element2.equals(point)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getID() {
		return id;
	}

	public void sendVersenkteSchiffe(ArrayList<Point[]> s) {
		try {
			dataOutputStream.writeObject(s);
			dataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Der Server fragt permanent ab, ob Daten an ihn gesendet wurden.
	 */
	@Override
	public void run() {
		try {
			dataOutputStream = new ObjectOutputStream(socket.getOutputStream());
			while (shouldRun) {

				Object obj = dataInputStream.readObject();

				if (obj.getClass().getName().equals("java.lang.String")) {
					String request = (String) obj;
					if (request.equals("$getCurrentPlayerID")) {
						String cp = server.getCurrentPlayerID();
						dataOutputStream.writeObject(cp);
						dataOutputStream.flush();
					} else if (request.equals("$switchCurrentPlayer")) {
						server.switchCurrentPlayer();
						dataOutputStream.writeObject(server.getCurrentPlayerID());
						dataOutputStream.flush();
					} else if (request.equals("$disconnect")) {
						shouldRun = false;
						beenden();
					}
					// Client fragt irgendwas in Form eines Strings an
				} else if (obj.getClass().getName().equals("java.awt.Point")) {
					Point p = (Point) obj;
					shots.add(p);
					boolean treffer = getEnemy().isShip(p);
					dataOutputStream.writeObject(treffer);
					dataOutputStream.flush();

					// gucken nach versenkt
					boolean versenkt;
					ArrayList<Point[]> versenkteSchiffe = new ArrayList<>();
					for (Point[] element : getEnemy().getMeineSchiffe()) {
						versenkt = true;
						for (Point element2 : element) {
							if (contains(shots, element2) == false) {
								versenkt = false;
							}
						}

						if (versenkt) {
							versenkteSchiffe.add(element);
						}
					}
					sendVersenkteSchiffe(versenkteSchiffe);

					if (treffer) {
						trefferCounter++;
						if (trefferCounter == getEnemy().getShipLength()) {
							// Mitteilung an spieler und gegner, dass Gewonnen wurde
							dataOutputStream.writeObject(new String("$gewonnen"));
							dataOutputStream.flush();
							getEnemy().sendGewonnen();

						}
					}

					getEnemy().sendShip(p); // zuständig für Schussanzeige des Gegners
					// auf bool bekommt Point
					// Anschließend beim Gegner checken ob treffer und mit writeBoolean mitteilen ob
					// treffer oder nicht
				}

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void sendGewonnen() {
		try {
			dataOutputStream.writeObject(new String("$enemywon"));
			dataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean contains(ArrayList<Point> s, Point p) {
		boolean b = false;
		for (Point element : s) {
			if (element.equals(p)) {
				b = true;
			}
		}
		return b;
	}

	private void beenden() {
		shouldRun = false;
		try {
			sleep(40);
			dataInputStream.close();
			dataOutputStream.close();
			socket.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (getEnemy() != null) {
			getEnemy().sendBeenden();
		}

		server.beenden();
	}

	public void sendBeenden() {
		try {
			dataOutputStream.writeObject(new String("$enemy disconnected"));
			dataOutputStream.flush();
			shouldRun = false;
			dataInputStream.close();
			dataOutputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Point[]> getMeineSchiffe() {
		return meineSchiffe;
	}

}
