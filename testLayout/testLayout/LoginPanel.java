package testLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

	private JButton submitt;
	private JTextField nameField, ipField, hostField;
	private JLabel nameLabel, ipLabel, hostLabel;
	private String name, ip;
	private int host;

	private Window window;

	public LoginPanel(Window window) {
		this.window = window;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(600, 300));

		add(Box.createRigidArea(new Dimension(0, 25)));

		nameLabel = new JLabel("Name:");
		nameLabel.setMaximumSize(new Dimension(60, 15));
		nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		nameLabel.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		add(nameLabel);

		nameField = new JTextField();
		nameField.setMaximumSize(new Dimension(450, 40));
		nameField.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameField.setFont(new Font("Agency FB", Font.PLAIN, 19));
		add(nameField, BorderLayout.CENTER);

		add(Box.createRigidArea(new Dimension(0, 25)));

		ipLabel = new JLabel("IP:");
		ipLabel.setMaximumSize(new Dimension(60, 15));
		ipLabel.setAlignmentX(CENTER_ALIGNMENT);
		ipLabel.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		add(ipLabel);

		ipField = new JTextField();
		ipField.setMaximumSize(new Dimension(450, 40));
		ipField.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		ipField.setFont(new Font("Agency FB", Font.PLAIN, 19));
		ipField.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(ipField);

		add(Box.createRigidArea(new Dimension(0, 25)));

		hostLabel = new JLabel("Host:");
		hostLabel.setMaximumSize(new Dimension(60, 15));
		hostLabel.setAlignmentX(CENTER_ALIGNMENT);
		hostLabel.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		add(hostLabel);

		hostField = new JTextField();

		hostField.setMaximumSize(new Dimension(450, 40));
		hostField.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		hostField.setAlignmentX(Component.CENTER_ALIGNMENT);
		hostField.setFont(new Font("Agency FB", Font.PLAIN, 19));
		add(hostField);

		add(Box.createRigidArea(new Dimension(0, 35)));

		submitt = new JButton("submitt");
		submitt.setFont(new Font("Agency FB", Font.PLAIN, 25));
		submitt.setBackground(new Color(255, 140, 000));
		submitt.setMinimumSize(new Dimension(50, 20));
		submitt.setMaximumSize(new Dimension(200, 50));
		submitt.setAlignmentX(CENTER_ALIGNMENT);
		submitt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!nameField.getText().equals("") && !ipField.getText().equals("")
						&& !hostField.getText().equals("")) {
					name = nameField.getText();
					window.setTitle(name + "  -Schiffeversenken");
					ip = ipField.getText();
					try {
						host = Integer.parseInt(hostField.getText()); // port
						login();
					} catch (java.lang.NumberFormatException ie) {
						hostField.setForeground(Color.red);
						hostField.setText("only numbers!!");
					}

				}
			}
		});
		add(submitt);

	}

	private void login() {
		// hier kommt jetzt normaler weise das ganze Server gedöns mit rein,
		// jedoch ist dieses Projekt nur zum erzeugen der graphischen Oberfläche
		window.logInToPosition();

		// window.repaint();
		// window.addPositionieren();
	}

	@Override
	public String getName() {
		return name;
	}

	public String getIP() {
		return ip;
	}

	public int getHost() {
		return host;
	}

}
