package app.views;


import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamStreamer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static app.views.CamStreamServer.streamer;

public class Main extends JFrame {

	public static Main frame;
	public static SettingsPanel settingsPanel;
	public static VideoPanel videoPanel;

	public static int defWidth = 640;
	public static int defHeight = 480;

	public Main() {
		setTitle("Lab Recon Webcam");
		setSize(800, 800);

		setLayout(new GridLayout(2, 2));

		videoPanel = new VideoPanel();
		settingsPanel = new SettingsPanel();

		settingsPanel.setVisible(true);
		settingsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		videoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(settingsPanel);
		add(videoPanel);
	}


	public static LabReconStreamer streamer;
	public static Webcam webcam;
	public static boolean runServer = true;

	public static void main(String[] args) throws  InterruptedException {

		webcam = Webcam.getDefault();
		frame = new Main();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(true);

		streamer = new LabReconStreamer(4400, webcam, 60, true);

		while (true) {
			if (runServer) {
				if (!webcam.isOpen()) {
					videoPanel.getPanel().start();
					streamer.start();
				}
				Thread.sleep(5000);
			} else {
				if (webcam.isOpen()) {
					streamer.stop();
					videoPanel.getPanel().stop();
					Thread.sleep(5000);
				}
			}
		}


	}
}
