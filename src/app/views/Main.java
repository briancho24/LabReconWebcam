package app.views;


import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamStreamer;
import org.ini4j.Ini;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class Main extends JFrame {

	public static Main frame;
	public static SettingsPanel settingsPanel;
	public static VideoPanel videoPanel;

	public static int defWidth = 640;
	public static int defHeight = 480;
	public static int port = 4400;

	public Main() {
		setTitle("Lab Recon Webcam");

		setLayout(new GridLayout(1, 2));

		videoPanel = new VideoPanel();
		settingsPanel = new SettingsPanel();

		settingsPanel.setVisible(true);
		settingsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		videoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(videoPanel);
		add(settingsPanel);
	}


	public static LabReconStreamer streamer;
	public static Webcam webcam;
	public static boolean runServer = true;

	public static void main(String[] args) throws InterruptedException, IOException {

		Ini ini = new Ini(new File("settings.ini"));
		port = Integer.parseInt(ini.get("server","port"));
		videoPanel.newD = new Dimension(Integer.parseInt(ini.get("server", "res_w")), Integer.parseInt(ini.get("server", "res_h")));
		System.out.println(port);

		webcam = Webcam.getDefault();
		frame = new Main();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(800, 480);

//		TODO: method to update server port and rerun

		streamer = new LabReconStreamer(port, webcam, 60, true);

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
