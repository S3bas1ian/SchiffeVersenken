package testLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class PositionMain extends JPanel {

	private PositionField f;
	private PositionButtons b;

	public PositionMain(Window window) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMinimumSize(new Dimension(650, 650));
		setBackground(Color.white);
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		// setBackground(Color.blue);
		f = new PositionField(window);
		b = new PositionButtons(window);

		add(f);
		add(b);
		add(Box.createGlue());

		// window.setTitle(client.getID());
	}

	public PositionField getPositionField() {
		return f;
	}

	public PositionButtons getPositionButtons() {
		return b;
	}

}
