package app.views;


import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

	public static Main frame;
	public static SettingsPanel settingsPanel;
	public static VideoPanel videoPanel;

	public static int defWidth = 640;
	public static int defHeight = 480;

	public Main() {
		setTitle("Lab Recon Webcam");
		setSize(1200, 800);

		setLayout(new FlowLayout());

		videoPanel = new VideoPanel();
		settingsPanel = new SettingsPanel();

		settingsPanel.setVisible(false);
		settingsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		videoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(settingsPanel);
		add(videoPanel);
	}

	public static void viewSettings(boolean settings) {
		if (settings) {
			settingsPanel.setVisible(true);
			videoPanel.setVisible(false);
			videoPanel.panel.stop();
		} else {
			videoPanel.setSize(new Dimension(640, 480));
			videoPanel.panel.setSize(new Dimension(640, 480));
			videoPanel.panel.start();
			settingsPanel.setVisible(false);
			videoPanel.setVisible(true);
		}
	}

	public static void main(String[] args) {
		frame = new Main();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(true);
	}
}
