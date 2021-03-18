package testLayout;

import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class SpielenMain extends JPanel {

	private Window window;

	private SpielePanel spielePanel;
	private SidePanel sidePanel;

	public SpielenMain(Window window) {
		this.window = window;
		setLayout(null);
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		setSize(getPreferredSize());

		sidePanel = new SidePanel(window);
		sidePanel.setBounds((int) (2.0 * window.getWidth() / 3), 0, window.getWidth() / 3, window.getHeight());

		spielePanel = new SpielePanel(window);
		spielePanel.setBounds(0, 0, (int) (2.0 * window.getWidth() / 3), window.getHeight());

		add(sidePanel);
		add(spielePanel);

	}

	@Override
	public void paint(Graphics g) {
		setSize(window.getSize());
		paintChildren(g);
	}

	public SidePanel getSidePanel() {
		return sidePanel;
	}

}
