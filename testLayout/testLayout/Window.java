package testLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Window extends JFrame {

	private LoginPanel loginPanel;
	private PositionMain mainPosition;
	private SpielenMain mainSpielen;
	private Client client;

	int schifflänge = 3;
	boolean activateDelete = false;
	boolean waagerecht = false;

	// Rectangle[] felder; // Graphischen Felder (paint) (kleine Kästchen)
	ArrayList<Point[]> schiffe = new ArrayList<Point[]>(); // tatsächliche Position der Schiffe, die nacher auch an den
															// Server übermittelt wird
	ArrayList<Point[]> alreadyPositionedShips = new ArrayList<Point[]>(); // bereits positionierte
																			// Felder (blaue Schiffe)

	public Window() {
		super("Schiffeversenken V1");
		// setDefaultCloseOperation(EXIT_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (client == null) {
					System.exit(0);
				} else {
					client.trennen();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e1) {
					}
					System.exit(0);
				}
			}
		});

		setPreferredSize(new Dimension(650, 350));
		setMinimumSize(new Dimension(500, 380));
		setBackground(Color.white);

		loginPanel = new LoginPanel(this);

		// setSize(getPreferredSize());
		add(loginPanel);
		pack(); // wie setSize, nur das alles auf seine präferierte Größe gesetzt wird
		setVisible(true);

	}

	public Client getClient() {
		return client;
	}

	public void logInToPosition() {

		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					client = new Client(loginPanel.getName(), loginPanel.getIP(), loginPanel.getHost());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();

		remove(loginPanel);
		loginPanel = null;

		client.verbinden();

		mainPosition = new PositionMain(this);
		add(mainPosition);
		setMinimumSize(new Dimension(650, 650));
		setPreferredSize(mainPosition.getPreferredSize());
		setSize(mainPosition.getPreferredSize());
		setBackground(Color.white);
		pack();
	}

	public void positionToSpielen() {
		remove(mainPosition);
		mainPosition = null;
		mainSpielen = new SpielenMain(this);
		setSize(mainSpielen.getPreferredSize());
		add(mainSpielen);
		pack();

	}

	public SidePanel getSidePanel() {
		return mainSpielen.getSidePanel();
	}

	public SpielenMain getSpielenMain() {
		return mainSpielen;
	}

}
