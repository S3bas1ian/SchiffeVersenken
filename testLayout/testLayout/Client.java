package testLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Client extends Thread {

	private Socket socket;
	private ObjectInputStream dataIn;
	private ObjectOutputStream dataOut;
	private String id, ip, enemyPlayerId;
	private int port;

	private ArrayList<Point[]> meineSchiffe;
	private ArrayList<Point> enemyShoots;
	private ArrayList<Point[]> versenkteSchiffe;

	private String currentPlayerID;
	private boolean treffer, shouldRun;

	private JTextArea textChat;

	public Client(String id, String ip, int port) throws UnknownHostException, IOException { // , JTextArea textChat
		this.id = id;
		this.ip = ip;
		this.port = port;
		shouldRun = true;
		enemyShoots = new ArrayList<Point>();
	}

	public void sendMyShips(ArrayList<Point[]> ships) {
		meineSchiffe = ships;
		try {
			dataOut.writeObject(ships);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public ArrayList<Point> getEnemyShoots() {
		return enemyShoots;
	}

	public void waitForEnemyShoot() {
		try {
			Object obj = dataIn.readObject();
			if (obj.getClass().getName().equals("java.awt.Point")) {
				enemyShoots.add((Point) obj);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Point[]> getMyShips() {
		return meineSchiffe;
	}

	public void verbinden() {
		try {
			socket = new Socket(ip, port);
			dataOut = new ObjectOutputStream(socket.getOutputStream());
			dataOut.writeUTF(id);
			dataOut.flush();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		start();
	}

	public void trennen() {
		// alles, was beim trennen vom Server gemacht werden muss
		try {
			shouldRun = false;
			sleep(40);
			dataOut.writeObject(new String("$disconnect"));
			dataOut.flush();
			dataIn.close();
			dataOut.close();
			socket.close();
			System.exit(0);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void shoot(Point p) { // return true wenn treffer, return false wenn kein Treffer
		try {
			dataOut.writeObject(p);
			dataOut.flush();
			sleep(20);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void switchCurrentPlayer() {
		try {
			dataOut.writeObject(new String("$switchCurrentPlayer"));
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCurrentPlayerID() {
		try {
			dataOut.writeObject(new String("$getCurrentPlayerID"));
			dataOut.flush();
			sleep(20);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return currentPlayerID;
	}

	public String getID() {
		return id;
	}

	public boolean getTreffer() {
		return treffer;
	}

	@Override
	public void run() {
		try {
			dataIn = new ObjectInputStream(socket.getInputStream());
			while (shouldRun) {

				Object obj = dataIn.readObject();
				if (obj.getClass().getName().equals("java.awt.Point")) {
					enemyShoots.add((Point) obj);
				} else if (obj.getClass().getName().equals("java.lang.String")) {
					if (obj.equals("$enemy disconnected")) {
						textChat.append("Gegner Disconnected \n");
						shouldRun = false;
						dataIn.close();
						dataOut.close();
						socket.close();
						sleep(2500);
						System.exit(0);
					} else if (obj.equals("$gewonnen")) {
						textChat.append("GEWONNEN \n");
					} else if (obj.equals("$enemywon")) {
						textChat.setFont(new Font("Agency FB", Font.PLAIN, 30));
						textChat.setForeground(Color.red);
						textChat.append("verloren");
						sleep(2500);
						trennen();
					} else if (((String) obj).split(" ")[0].equals("enemyPlayerID")) {
						enemyPlayerId = ((String) obj).split(" ")[1];
					} else {
						currentPlayerID = (String) obj;
					}
				} else if (obj.getClass().getName().equals("java.lang.Boolean")) {
					treffer = (boolean) obj;
				} else if (obj.getClass().getName().equals("java.util.ArrayList")) {
					versenkteSchiffe = (ArrayList<Point[]>) obj;
				}

			}

		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setTextChat(JTextArea textChat) {
		this.textChat = textChat;
	}

	public String getEnemyPlayerID() {
		if (enemyPlayerId != null) {
			return enemyPlayerId;
		} else {
			try {
				dataOut.writeObject(new String("$enemyPlayerID"));
				dataOut.flush();
				Thread.sleep(60);
				return enemyPlayerId;

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ArrayList<Point[]> getVersenkteSchiffe() {
		return versenkteSchiffe;
	}

}
