package app.views;


import com.github.sarxos.webcam.*;
import org.ini4j.Ini;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;


public class Main extends JFrame {

	static Webcam webcam;
	static VideoPanel videoPanel;
	static SettingsPanel settingsPanel;
	static ConsolePanel consolePanel;
	static LabReconStreamer streamer;
	static List<Webcam> webcamList;
	static JTabbedPane tp;

	static boolean runServer = true;
	static int defWidth = 640;
	static int defHeight = 480;
	static int port = 4400;

	static Ini ini;

	public Main() {
		setTitle("Lab Recon Webcam");

		setLayout(new GridLayout(1, 2));

		videoPanel = new VideoPanel(webcam);
		settingsPanel = new SettingsPanel();
		consolePanel = new ConsolePanel();
		tp = new JTabbedPane();

		settingsPanel.setVisible(true);
		settingsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		videoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		consolePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(videoPanel);
		tp.add("Settings", settingsPanel);
		tp.add("Console", consolePanel);
		add(tp);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setSize(800, 480);
	}

	public static void main(String[] args) throws InterruptedException, IOException {

		ini = new Ini(new File("resources/settings.ini"));
		String webcamName = ini.get("streamer", "camera");
		port = Integer.parseInt(ini.get("streamer", "port"));
		int res_w = Integer.parseInt(ini.get("streamer", "res_w"));
		int res_h = Integer.parseInt(ini.get("streamer", "res_h"));

		webcam = Webcam.getDefault();

		webcamList = Webcam.getWebcams();
		for (Webcam w : webcamList) {
			if (w.getName().equals(webcamName))
				webcam = w;
//			System.out.println(w.getName());
		}

		ini.put("streamer", "camera", webcamName);
		ini.store();

		Main frame = new Main();


		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + " Streaming to port " + port);
		System.out.println("Webcam Name: " + webcamName);
		videoPanel.setDim(new Dimension(res_w, res_h));
		streamer = new LabReconStreamer(port, webcam, 60, true);

		do {
			if (runServer) {
				if (!webcam.isOpen()) {
					videoPanel.getWebcamPanel().start();
					streamer.start();
					System.out.println("Stream started");
				}
				Thread.sleep(5000);
			} else {
				if (webcam.isOpen()) {
					System.out.println("Stream stopped");
					streamer.stop();
					videoPanel.getWebcamPanel().stop();
					Thread.sleep(5000);
				}
			}
		} while (true);
	}
}
