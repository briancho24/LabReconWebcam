package app.views;


import com.github.sarxos.webcam.*;
import org.ini4j.Ini;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;


public class Main extends JFrame {

	static Webcam webcam;
	static VideoPanel videoPanel;
	static SettingsPanel settingsPanel;
	static ConsolePanel consolePanel;
	static HelpPanel helpPanel;
	static LabReconStreamer streamer;
	static Map<String, Webcam> webcamList = new HashMap<String, Webcam>();
	static JTabbedPane tp;

	static boolean runServer = true;
	static int defWidth = 640;
	static int defHeight = 480;
	static int port = 4400;

	static Ini ini;

	public Main() throws FileNotFoundException {
		setTitle("Lab Recon Webcam");

		setLayout(new GridLayout(1, 2));

		videoPanel = new VideoPanel();
		settingsPanel = new SettingsPanel();
		consolePanel = new ConsolePanel();
		helpPanel = new HelpPanel();
		tp = new JTabbedPane();

		settingsPanel.setVisible(true);
//		settingsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		videoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		consolePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(videoPanel);
		tp.add("Settings", settingsPanel);
		tp.add("Console", consolePanel);
		tp.add("Help", helpPanel);
		add(tp);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setSize(800, 480);
	}

	public static void main(String[] args) throws InterruptedException, IOException {

		File f = new File("settings.ini");

		if (!f.exists() && !f.isDirectory()) {
			f.createNewFile();
			ini = new Ini(f);
			ini.put("streamer", "camera", "");
			ini.put("streamer", "port", "80");
			ini.put("streamer", "res_w", "640");
			ini.put("streamer", "res_h", "480");
			ini.put("streamer", "fps", "80");
			ini.put("image", "img_w", "114");
			ini.put("image", "img_h", "114");
		}

		ini = new Ini(f);

		String webcamName = ini.get("streamer", "camera");
		port = Integer.parseInt(ini.get("streamer", "port"));
		int res_w = Integer.parseInt(ini.get("streamer", "res_w"));
		int res_h = Integer.parseInt(ini.get("streamer", "res_h"));
		int fps = Integer.parseInt(ini.get("streamer", "fps"));
		int img_w = Integer.parseInt(ini.get("image", "img_w"));
		int img_h = Integer.parseInt(ini.get("image", "img_h"));


		List<Webcam> webcams = Webcam.getWebcams();

		for (Webcam w : webcams)
			webcamList.put(w.getName(), w);

		webcam = webcamList.getOrDefault(webcamName, Webcam.getDefault());

		ini.put("streamer", "camera", webcamName);
		ini.store();

		Main frame = new Main();
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + " Streaming to port " + port);
		System.out.println("Webcam Name: " + webcamName);
		videoPanel.setDim(new Dimension(res_w, res_h));
		streamer = new LabReconStreamer(port, webcam, fps, true, img_w, img_h);

		do {
			if (runServer) {
				if (!webcam.isOpen()) {
					videoPanel.getWebcamPanel().start();
					streamer.start();
					System.out.println("Stream started");
				}
				Thread.sleep(2000);
			} else {
				if (webcam.isOpen()) {
					System.out.println("Stream stopped");
					streamer.stop();
					videoPanel.getWebcamPanel().stop();
					Thread.sleep(2000);
				}
			}
		} while (true);
	}
}
