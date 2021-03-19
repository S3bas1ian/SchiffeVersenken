package testLayout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SpielePanel extends JPanel implements MouseListener, MouseMotionListener {

	private Window window;
	private Client client;
	private SpielenMain mainSpielen;
	// private SidePanel sidePanel;

	private int WTH;
	private int DIM = 11;

	private Rectangle[] felder;
	private Point currentSelectedField;
	private ArrayList<Point> treffer = new ArrayList<>();
	private ArrayList<Point> wasser = new ArrayList<>();
	private ArrayList<Point[]> versenkteSchiffe = new ArrayList<>();

	public SpielePanel(Window window) {
		this.window = window;
		client = window.getClient();
		// sidePanel = window.getSidePanel();
		setLayout(null);
		setPreferredSize(new Dimension((int) (2.0 * window.getWidth() / 3.0), window.getHeight()));
		setSize(new Dimension((int) (2.0 * window.getWidth() / 3), window.getHeight()));
		setBackground(Color.blue);
		setBorder(BorderFactory.createLineBorder(Color.black));

		WTH = (int) Math.min(2.0 * window.getWidth() / 3, 1.0 * window.getHeight());

		addMouseListener(this);
		addMouseMotionListener(this);

	}

	@Override
	public void paint(Graphics g) {
		WTH = (int) Math.min(2.0 * window.getWidth() / 3, 1.0 * window.getHeight());
		setBounds(0, 0, (int) (2.0 * window.getWidth() / 3), window.getHeight());
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.red);

		if (currentSelectedField != null) {
			g.fillRect(currentSelectedField.x * WTH / DIM, currentSelectedField.y * WTH / DIM, WTH / DIM, WTH / DIM);
		}
		if (treffer.size() > 0) {
			g.setColor(Color.pink);
			for (Point s : treffer) {
				g.fillRect(s.x * WTH / DIM, s.y * WTH / DIM, WTH / DIM, WTH / DIM);
			}
		}

		if (wasser.size() > 0) {
			g.setColor(Color.blue);
			for (Point s : wasser) {
				g.fillRect(s.x * WTH / DIM, s.y * WTH / DIM, WTH / DIM, WTH / DIM);
			}
		}

		versenkteSchiffe = client.getVersenkteSchiffe();
		if (versenkteSchiffe != null) {

			System.out.println(versenkteSchiffe.size());
			for (Point[] t : versenkteSchiffe) {
				g.setColor(Color.GREEN);
				for (Point p : t) {
					g.drawLine(p.x * WTH / DIM + WTH / (2 * DIM), p.y * WTH / DIM, p.x * WTH / DIM + WTH / (2 * DIM),
							(p.y + 1) * WTH / DIM);
				}
			}
		}

		grid(g);
	}

	private void grid(Graphics g) {
		g.setColor(Color.black);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1.7f));

		for (int x = 0; x < DIM; x++) {
			g2d.drawLine(x * WTH / DIM, 0, x * WTH / DIM, WTH - WTH / DIM);
		}
		for (int y = 0; y < DIM; y++) {
			g2d.drawLine(0, y * WTH / DIM, WTH - WTH / DIM, y * WTH / DIM);
		}

		fillPoints();
	}

	private void fillPoints() {
		ArrayList<Point> eckPunkte = new ArrayList<Point>();
		for (int x = 0; x < DIM - 1; x++) {
			for (int y = 0; y < DIM - 1; y++) {
				eckPunkte.add(new Point((int) (x * WTH / 11.0), (int) (y * WTH / 11.0)));
			}
		}

		felder = new Rectangle[eckPunkte.size()];
		for (int i = 0; i < felder.length; i++) {
			felder[i] = new Rectangle(eckPunkte.get(i).x, eckPunkte.get(i).y, WTH / DIM, WTH / DIM);
		}
	}

//____________________________________________
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

		// int FELD_SIZE = WTH / DIM;

		for (Rectangle element : felder) {
			if (element.contains(e.getPoint())) {
				currentSelectedField = new Point((int) Math.round(element.x * 11.0 / WTH),
						(int) Math.round(element.y * 11.0 / WTH));
				window.getSpielenMain().repaint();
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (client.getCurrentPlayerID().equals(client.getID())) {

			if (currentSelectedField != null) {
				client.shoot(currentSelectedField);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
				}

				if (client.getTreffer() == true) {
					treffer.add(currentSelectedField);
				} else {
					wasser.add(currentSelectedField);
					client.switchCurrentPlayer();
				}
				window.getSpielenMain().repaint();
				// sidePanel.repaint();
				// später wird shots durch treffer und wasser ersetzt...
				// shots.add(currentSelectedField);
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
