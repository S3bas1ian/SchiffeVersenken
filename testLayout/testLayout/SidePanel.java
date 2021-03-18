package testLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SidePanel extends JPanel {

	private Window window;
	private Client client;

	private JTextArea textChat;

	private int WTH;
	private int DIM = 11;

	public SidePanel(Window window) {
		this.window = window;
		client = window.getClient();
		// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(null);

		int t_x = (int) (window.getWidth() / 3.0);
		int t_y = window.getHeight();

		WTH = Math.min(window.getWidth() / 3, window.getHeight() / 2);

		setPreferredSize(new Dimension(t_x, t_y));
		// setSize(getPreferredSize());

		textChat = new JTextArea();
		textChat.setBorder(BorderFactory.createLineBorder(Color.black));
		textChat.setLineWrap(true);
		textChat.setBounds(0, t_y / 2, this.getWidth(), this.getHeight());

		add(textChat);

		client.setTextChat(textChat);

		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	public void paint(Graphics g) {
		WTH = Math.min(window.getWidth() / 3, window.getHeight() / 2);
		setBounds((int) (2.0 * window.getWidth() / 3), 0, window.getWidth() / 3, window.getHeight());

		g.setColor(Color.white);
		g.fillRect(0, 0, WTH, WTH);
		g.setColor(Color.black);

		grid(g);

		if (window.schiffe != null) {
			g.setColor(Color.black);
			for (Point[] s : window.schiffe) {
				for (Point element : s) {
					g.fillRect((int) Math.round(element.x * WTH / 11.0), (int) Math.round(element.y * WTH / 11.0),
							(int) Math.round(WTH / 11.0), (int) Math.round(WTH / 11.0));
				}
			}
		}

		if (client.getEnemyShoots() != null) {
			g.setColor(Color.red);
			// g.setFont(new Font("Agency FB", Font.BOLD, 25));
			for (Point element : client.getEnemyShoots()) {
//				g.fillRect((int) Math.round(element.x * WTH / 11.0), (int) Math.round(element.y * WTH / 11.0),
//						(int) Math.round(WTH / 11.0), (int) Math.round(WTH / 11.0));

				g.drawLine((int) Math.round(element.x * WTH / 11.0), (int) Math.round((element.y + 1) * WTH / 11.0),
						(int) Math.round((element.x + 1) * WTH / 11.0), (int) Math.round(element.y * WTH / 11.0));

				g.drawLine((int) Math.round(element.x * WTH / 11.0), (int) Math.round(element.y * WTH / 11.0),
						(int) Math.round((element.x + 1) * WTH / 11.0), (int) Math.round((element.y + 1) * WTH / 11.0));
			}
		}

		textChat.setBounds(0, window.getHeight() / 2, window.getWidth() / 3, window.getHeight() / 2);

		paintChildren(g);
	}

	private void grid(Graphics g) {
		for (int x = 0; x < DIM; x++) {
			g.drawLine(x * WTH / DIM, 0, x * WTH / DIM, WTH - WTH / DIM);
		}
		for (int y = 0; y < DIM; y++) {
			g.drawLine(0, y * WTH / DIM, WTH - WTH / DIM, y * WTH / DIM);
		}
	}

}
