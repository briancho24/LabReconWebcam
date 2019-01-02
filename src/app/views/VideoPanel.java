package app.views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class VideoPanel extends JPanel {

	public static Dimension newD = new Dimension(1000, 1000);

	public static WebcamPanel panel;
	public static Webcam webcam;

	public VideoPanel() {

		setLayout(new GridLayout(2,2));
//		setSize(new Dimension(640, 480));

		webcam = Main.webcam;

		Dimension[] dimensions = {newD};
		webcam.setCustomViewSizes(dimensions);

		//max 640, 480

		//webcam.setViewSize(WebcamResolution.VGA.getSize());

		webcam.setViewSize(newD);

		panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);

		add(panel);
//		add(switchSetting);

	}

	public WebcamPanel getPanel() {
		return panel;
	}

	public void setReso() {
		if (Main.webcam.isOpen())
			Main.webcam.close();
		Dimension[] dimensions = {newD};
		Main.webcam.setCustomViewSizes(dimensions);
		Main.webcam.setViewSize(newD);
		Main.webcam.open();
	}

}
