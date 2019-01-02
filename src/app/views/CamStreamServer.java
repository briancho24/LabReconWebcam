package app.views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamStreamer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

public class CamStreamServer {

	private static boolean running = true;

	private static void initFrame() {
		JFrame frame = new JFrame("Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(300, 300));

		JToggleButton jtb = new JToggleButton("Start/Stop");
		jtb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					running = true;
					System.out.println(running);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					running = false;
					System.out.println(running);
				}
			}
		});

		frame.add(jtb);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	public static void main(String[] args) throws InterruptedException {

		Webcam w = Webcam.getDefault();
		w.setViewSize(new Dimension(640, 480));
		LabReconStreamer streamer = new LabReconStreamer(4400, w, 60, true);

		initFrame();

		do {
			Thread.sleep(5000);
		} while (true);

	}
}
