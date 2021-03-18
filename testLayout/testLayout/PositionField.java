package testLayout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

public class PositionField extends JPanel implements MouseListener, MouseMotionListener {

	private int DIM = 10;
	private Window window;
	private int WTH;

	private Rectangle[] felder;
	private Rectangle[] tempShip; // temporäre Schiffsposition (Mauszeiger)

	public PositionField(Window window) {
		this.window = window;
		setLayout(null);
		setBackground(Color.white);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(new Dimension(screenSize.height - 80, screenSize.height - 80));
		setMinimumSize(new Dimension(575, 650));
		setSize(500, 500);
		// setMaximumSize(new Dimension(5000, 5000));
		// setSize(getPreferredSize());
		WTH = Math.min(getWidth(), window.getHeight());

		addMouseListener(this);
		addMouseMotionListener(this);

		fillPoints();
	}

	@Override
	public void paint(Graphics g) {
		WTH = Math.min(getWidth(), window.getHeight());
		int w_d = Math.round(WTH / DIM);
		g.setColor(Color.white);
		g.fillRect(0, 0, WTH, window.getHeight());

		if (tempShip != null && window.activateDelete == false) {
			g.setColor(Color.red);
			for (Rectangle element : tempShip) {
				g.fillRect(element.x, element.y, (int) (WTH / 10.0), (int) (WTH / 10.0));
			}

		} else if (window.activateDelete == true) {
			g.setColor(Color.pink);
			g.fillRect(tempShip[0].x, tempShip[0].y, w_d, w_d);
		}

		if (window.schiffe != null) {
			g.setColor(Color.blue);
			for (Point[] s : window.schiffe) {
				for (Point element : s) {
					g.fillRect(Math.round(element.x * WTH / DIM), Math.round(element.y * WTH / DIM),
							Math.round(WTH / DIM), Math.round(WTH / DIM));
				}
			}
		}

		grid(g);

	}

	private void grid(Graphics g) {
		fillPoints();
		g.setColor(Color.black);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2.1f));

		for (int x = 0; x < DIM + 1; x++) {
			g2d.drawLine(x * WTH / DIM, 0, x * WTH / DIM, WTH);
		}
		for (int y = 0; y < DIM + 1; y++) {
			g2d.drawLine(0, y * WTH / DIM, WTH, y * WTH / DIM);
		}
	}

	private void fillPoints() {
		ArrayList<Point> eckPunkte = new ArrayList<Point>();
		for (int x = 0; x < DIM; x++) {
			for (int y = 0; y < DIM; y++) {
				eckPunkte.add(new Point(x * WTH / DIM, y * WTH / DIM));
			}
		}

		felder = new Rectangle[eckPunkte.size()];
		for (int i = 0; i < felder.length; i++) {
			felder[i] = new Rectangle(eckPunkte.get(i).x, eckPunkte.get(i).y, WTH / DIM, WTH / DIM);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		WTH = Math.min(getWidth(), window.getHeight());
		int FELD_SIZE = WTH / DIM;
		for (Rectangle element : felder) {
			// berechnet anhand des Spielfeldes und der Mausposition das tempSchiff
			if (element.contains(e.getPoint()) && window.activateDelete == false) {

				if (window.schifflänge == 3 && window.waagerecht == false) {
					tempShip = new Rectangle[window.schifflänge];

					tempShip[0] = new Rectangle(element.x, element.y - FELD_SIZE, FELD_SIZE, FELD_SIZE);
					tempShip[1] = element;
					tempShip[2] = new Rectangle(element.x, element.y + FELD_SIZE, FELD_SIZE, FELD_SIZE);
				} else if (window.schifflänge == 3 && window.waagerecht == true) {
					tempShip = new Rectangle[window.schifflänge];
					tempShip[0] = new Rectangle(element.x - FELD_SIZE, element.y, FELD_SIZE, FELD_SIZE);
					tempShip[1] = element;
					tempShip[2] = new Rectangle(element.x + FELD_SIZE, element.y, FELD_SIZE, FELD_SIZE);
				} else if (window.schifflänge == 5 && window.waagerecht == true) {
					tempShip = new Rectangle[window.schifflänge];
					tempShip[0] = new Rectangle(element.x - FELD_SIZE * 2, element.y, FELD_SIZE, FELD_SIZE);
					tempShip[1] = new Rectangle(element.x - FELD_SIZE, element.y, FELD_SIZE, FELD_SIZE);
					tempShip[2] = element;
					tempShip[3] = new Rectangle(element.x + FELD_SIZE, element.y, FELD_SIZE, FELD_SIZE);
					tempShip[4] = new Rectangle(element.x + FELD_SIZE * 2, element.y, FELD_SIZE, FELD_SIZE);
				} else if (window.schifflänge == 5 && window.waagerecht == false) {
					tempShip = new Rectangle[window.schifflänge];
					tempShip[0] = new Rectangle(element.x, element.y - FELD_SIZE * 2, FELD_SIZE, FELD_SIZE);
					tempShip[1] = new Rectangle(element.x, element.y - FELD_SIZE, FELD_SIZE, FELD_SIZE);
					tempShip[2] = element;
					tempShip[3] = new Rectangle(element.x, element.y + FELD_SIZE, FELD_SIZE, FELD_SIZE);
					tempShip[4] = new Rectangle(element.x, element.y + FELD_SIZE * 2, FELD_SIZE, FELD_SIZE);
				} else if (window.schifflänge == 2 && window.waagerecht == false) {
					tempShip = new Rectangle[window.schifflänge];
					tempShip[0] = element;
					tempShip[1] = new Rectangle(element.x, element.y + FELD_SIZE, FELD_SIZE, FELD_SIZE);
				} else if (window.schifflänge == 2 && window.waagerecht == true) {
					tempShip = new Rectangle[window.schifflänge];
					tempShip[0] = element;
					tempShip[1] = new Rectangle(element.x + FELD_SIZE, element.y, FELD_SIZE, FELD_SIZE);
				} else if (window.schifflänge == 4 && window.waagerecht == false) {
					tempShip = new Rectangle[window.schifflänge];
					tempShip[0] = new Rectangle(element.x, element.y - FELD_SIZE, FELD_SIZE, FELD_SIZE);
					tempShip[1] = element;
					tempShip[2] = new Rectangle(element.x, element.y + FELD_SIZE, FELD_SIZE, FELD_SIZE);
					tempShip[3] = new Rectangle(element.x, element.y + FELD_SIZE * 2, FELD_SIZE, FELD_SIZE);
				} else if (window.schifflänge == 4 && window.waagerecht == true) {
					tempShip = new Rectangle[window.schifflänge];
					tempShip[0] = new Rectangle(element.x - FELD_SIZE, element.y, FELD_SIZE, FELD_SIZE);
					tempShip[1] = element;
					tempShip[2] = new Rectangle(element.x + FELD_SIZE, element.y, FELD_SIZE, FELD_SIZE);
					tempShip[3] = new Rectangle(element.x + FELD_SIZE * 2, element.y, FELD_SIZE, FELD_SIZE);
				}

				repaint();
				break;
			} else if (element.contains(e.getPoint()) && window.activateDelete == true) {
				tempShip = new Rectangle[1];
				tempShip[0] = element;
				repaint();
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		/**
		 * überprüft ob sich Schiffe überlagern oder außerhalb des Spielfeldes befinden
		 */
		if (window.activateDelete == false) {
			boolean doescontainall = true;

			ArrayList<Point> l_felder = new ArrayList<>();
			for (int x = 0; x < DIM; x++) {
				for (int y = 0; y < DIM; y++) {
					l_felder.add(new Point(x, y));
				}
			}

			// überprüft ob sich Schiffe überlagern oder berühren
			for (Rectangle element : tempShip) {
				int tsp_x = (int) Math.round(element.x * 10.0 / WTH);
				int tsp_y = (int) Math.round(element.y * 10.0 / WTH);

				Point lt = new Point(tsp_x - 1, tsp_y - 1);
				Point lm = new Point(tsp_x - 1, tsp_y);
				Point lb = new Point(tsp_x - 1, tsp_y + 1);

				Point mt = new Point(tsp_x, tsp_y - 1);
				Point mb = new Point(tsp_x, tsp_y + 1);

				Point rt = new Point(tsp_x + 1, tsp_y - 1);
				Point rm = new Point(tsp_x + 1, tsp_y);
				Point rb = new Point(tsp_x + 1, tsp_y + 1);

				for (Point[] element2 : window.schiffe) {
					if (Arrays.asList(element2).contains(lt) || Arrays.asList(element2).contains(lm)
							|| Arrays.asList(element2).contains(lb) || Arrays.asList(element2).contains(mt)
							|| Arrays.asList(element2).contains(mb) || Arrays.asList(element2).contains(rt)
							|| Arrays.asList(element2).contains(rm) || Arrays.asList(element2).contains(rb)) {

						doescontainall = false;
					}
				}

			}

			Point[] ts_Point = new Point[tempShip.length];
			for (int i = 0; i < tempShip.length; i++) {
				ts_Point[i] = new Point((int) Math.round(tempShip[i].x * 10.0 / WTH),
						(int) Math.round(tempShip[i].y * 10.0 / WTH));
			}

			for (Point element : ts_Point) { // überprüft, ob alle Teile des Schiffes überhaupt im Spielfeld
												// liegen
				if (l_felder.contains(element) == false) {
					doescontainall = false;
				}
			}

			if (tempShip != null && doescontainall == true) { // => das sich das Schiff vollständig an einer
				// akzeptierten Stelle im Spielfeld befindet

				window.schiffe.add(ts_Point);
				repaint();
			}
		} else if (window.activateDelete == true && tempShip != null) {
			// Hier kommt der Teil zum löschen rein
			Point p = new Point((int) Math.round(tempShip[0].x * 10.0 / WTH),
					(int) Math.round(tempShip[0].y * 10.0 / WTH));
			for (int i = 0; i < window.schiffe.size(); i++) {
				if (Arrays.asList(window.schiffe.get(i)).contains(p)) {
					// löschen
					window.schiffe.remove(i);
					repaint();
				}
			}
		}
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
