package app.views;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class HelpPanel extends JPanel {

	JButton btnOpenFile;
	JButton btnSave;
	JFrame tempFrame;
	JTextArea fileText;
	JScrollPane scrollPane;
	JTextArea info;

	public HelpPanel() throws FileNotFoundException {

		Font settingsFont = new Font("Apple Casual", Font.PLAIN, 14);
//		GridLayout layout = new GridLayout(6, 1);
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints c = new GridBagConstraints();


		btnOpenFile = new JButton("View settings.ini");

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;

		fileText = new JTextArea();
		scrollPane = new JScrollPane(fileText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		btnSave = new JButton("Save and Close");

		String infoText = "";
		infoText += "[port]: Specifies port number where stream will be served\n";
		infoText += "[res_h]: Number of vertical pixels in image\n";
		infoText += "[res_w]: Number of horizontal pixels in image\n";
		infoText += "[fps]: Number of frames per second for stream\n";
		infoText += "[camera]: Name of webcam\n";
		infoText += "[img_h]: Height of image in pixels as displayed on webpage\n";
		infoText += "[img_w]: Width of image in pixels as displayed on webpage\n\n";

		infoText += "Note: Changes to the application settings will not take place until the app is restarted.";

		info = new JTextArea();
		info.setText(infoText);
		info.setEditable(false);
		info.setWrapStyleWord(true);
		info.setBorder(null);
		info.setLineWrap(true);
		info.setBackground(null);


		tempFrame = new JFrame();
		tempFrame.setLayout(new GridBagLayout());
		tempFrame.setVisible(false);
		tempFrame.setLocationRelativeTo(null);
		tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tempFrame.setResizable(true);
		tempFrame.setSize(400, 400);

		c.gridheight = 4;
		c.gridx = 0;
		c.gridy = 0;
		tempFrame.add(scrollPane, c);

		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 6;
		c.ipady = 10;
		tempFrame.add(btnSave, c);

		addListeners();


		c.gridheight = 6;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 10;
		add(info, c);

		c.gridheight = 1;
		c.gridy = 7;
		add(btnOpenFile, c);
	}


	//open settings.ini
	private void addListeners() {
		btnOpenFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = "";
				FileReader file = null;
				try {
					file = new FileReader(Main.ini.getFile());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine())
					s += scan.nextLine() + "\n";
				scan.close();
				fileText.setText(s);
				tempFrame.setVisible(true);
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileWriter out = new FileWriter(Main.ini.getFile());
					out.write(fileText.getText());
					out.close();
					tempFrame.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
