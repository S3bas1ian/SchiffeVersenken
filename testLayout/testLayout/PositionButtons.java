package testLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PositionButtons extends JPanel {

	private JButton zweierButton, dreierButton, viererButton, f�nferButton;
	private JButton loginButton, l�schenButton, turnButton;
	private Window window;
	private Client client;

	private ArrayList<Point[]> test = new ArrayList<>();

	public PositionButtons(Window window) {
		this.window = window;
		client = window.getClient();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.white);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setPreferredSize(new Dimension(screenSize.width - screenSize.height, screenSize.height));
		setMinimumSize(new Dimension(75, 650));

		add(Box.createRigidArea(new Dimension(0, 15)));

		zweierButton = new JButton("Zweier");
		zweierButton.setMinimumSize(new Dimension(75, 25));
		zweierButton.setPreferredSize(new Dimension(115, 35));
		zweierButton.setMaximumSize(new Dimension(200, 50));
		zweierButton.setFont(new Font("Agency FB", Font.PLAIN, 19));
		zweierButton.setBackground(new Color(112, 128, 144));
		zweierButton.setForeground(Color.white);
		zweierButton.setAlignmentX(CENTER_ALIGNMENT);
		zweierButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.schiffl�nge = 2;
				System.out.println(test.getClass().getName());
			}
		});
		add(zweierButton);
		// _____________________________________________________________

		add(Box.createRigidArea(new Dimension(0, 15)));

		dreierButton = new JButton("Dreier");
		dreierButton.setMinimumSize(new Dimension(75, 25));
		dreierButton.setPreferredSize(new Dimension(115, 35));
		dreierButton.setMaximumSize(new Dimension(200, 50));
		dreierButton.setFont(new Font("Agency FB", Font.PLAIN, 19));
		dreierButton.setBackground(new Color(112, 128, 144));
		dreierButton.setForeground(Color.white);
		dreierButton.setAlignmentX(CENTER_ALIGNMENT);
		dreierButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.schiffl�nge = 3;
			}
		});
		add(dreierButton);
		// _____________________________________________________________

		add(Box.createRigidArea(new Dimension(0, 15)));

		viererButton = new JButton("Vierer");
		viererButton.setMinimumSize(new Dimension(75, 25));
		viererButton.setPreferredSize(new Dimension(115, 35));
		viererButton.setMaximumSize(new Dimension(200, 50));
		viererButton.setFont(new Font("Agency FB", Font.PLAIN, 19));
		viererButton.setBackground(new Color(112, 128, 144));
		viererButton.setForeground(Color.white);
		viererButton.setAlignmentX(CENTER_ALIGNMENT);
		viererButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.schiffl�nge = 4;
			}
		});
		add(viererButton);
		// _____________________________________________________________

		add(Box.createRigidArea(new Dimension(0, 15)));

		f�nferButton = new JButton("F�nfer");
		f�nferButton.setMinimumSize(new Dimension(75, 25));
		f�nferButton.setPreferredSize(new Dimension(115, 35));
		f�nferButton.setMaximumSize(new Dimension(200, 50));
		f�nferButton.setFont(new Font("Agency FB", Font.PLAIN, 19));
		f�nferButton.setBackground(new Color(112, 128, 144));
		f�nferButton.setForeground(Color.white);
		f�nferButton.setAlignmentX(CENTER_ALIGNMENT);
		f�nferButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.schiffl�nge = 5;
			}
		});
		add(f�nferButton);
		// _____________________________________________________________

		add(Box.createRigidArea(new Dimension(0, 25)));

		turnButton = new JButton("drehen");
		turnButton.setMinimumSize(new Dimension(75, 25));
		turnButton.setPreferredSize(new Dimension(115, 35));
		turnButton.setMaximumSize(new Dimension(200, 50));
		turnButton.setFont(new Font("Agency FB", Font.PLAIN, 19));
		turnButton.setAlignmentX(CENTER_ALIGNMENT);
		turnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.waagerecht = !window.waagerecht;
			}
		});

		add(turnButton);
		// _______________________________________________________________

		add(Box.createRigidArea(new Dimension(0, 25)));

		l�schenButton = new JButton("X");
		l�schenButton.setMinimumSize(new Dimension(75, 25));
		l�schenButton.setPreferredSize(new Dimension(115, 35));
		l�schenButton.setMaximumSize(new Dimension(200, 50));
		l�schenButton.setFont(new Font("Agency FB", Font.PLAIN, 19));
		l�schenButton.setBackground(Color.red);
		l�schenButton.setAlignmentX(CENTER_ALIGNMENT);
		l�schenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.activateDelete = !window.activateDelete;

			}
		});
		add(l�schenButton);
		// _____________________________________________________________

		add(Box.createRigidArea(new Dimension(0, 15)));

		loginButton = new JButton("confirm");
		loginButton.setMinimumSize(new Dimension(75, 25));
		loginButton.setPreferredSize(new Dimension(115, 35));
		loginButton.setMaximumSize(new Dimension(200, 50));
		loginButton.setFont(new Font("Agency FB", Font.PLAIN, 19));
		loginButton.setAlignmentX(CENTER_ALIGNMENT);
		loginButton.setBackground(new Color(255, 140, 000));
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Schiffe an Server �bermitteln und Spielfeld erzeugen
				client.sendMyShips(window.schiffe);
				window.positionToSpielen();

			}
		});
		add(loginButton);

	}

}
