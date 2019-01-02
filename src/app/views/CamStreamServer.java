package app.views;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CamStreamServer {

	private static boolean runServer = true;

	private static void initFrame() {
		JFrame frame = new JFrame("Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(300, 300));

		JToggleButton jtb = new JToggleButton("Start/Stop");
		jtb.setSelected(true);
		jtb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					runServer = true;
					System.out.println(runServer);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					runServer = false;
					System.out.println(runServer);
				}
			}
		});

		frame.add(jtb);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	static LabReconStreamer streamer;

	public static void main(String[] args) throws InterruptedException {

		Webcam w = Webcam.getDefault();
		Dimension[] dims = {new Dimension(800, 600)};
		w.setCustomViewSizes(dims);
		w.setViewSize(new Dimension(640, 480));
		streamer = new LabReconStreamer(4400, w, 60, true);

		initFrame();

//		streamer.setReso(new Dimension(800, 600));

//		while (true) {
//			if (runServer) {
//				if (!w.isOpen()) {
//					streamer.start();
//				}
//				Thread.sleep(5000);
//			} else {
//				if (w.isOpen()) {
//					streamer.stop();
//					Thread.sleep(5000);
//				}
//			}
//		}

	}
}
