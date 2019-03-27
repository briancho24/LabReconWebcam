package app.views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import java.awt.*;

public class VideoPanel extends JPanel {

	public static Dimension dim;
	public static WebcamPanel panel;

	public VideoPanel() {

		setLayout(new GridLayout(1, 1));

		dim = new Dimension(640, 480);
		Dimension[] dimensions = {dim};

//		this.webcam = webcam;
//		this.webcam.setCustomViewSizes(dimensions);
//		this.webcam.setViewSize(dim);

		panel = new WebcamPanel(Main.webcam);
		panel.setFPSDisplayed(false);
		panel.setDisplayDebugInfo(false);
		panel.setImageSizeDisplayed(false);
		panel.setMirrored(true);

		add(panel);
	}

	public WebcamPanel getWebcamPanel() {
		return panel;
	}

	public void setDim(Dimension d) {
		dim = d;
	}

	public Dimension getDim() {
		return dim;
	}

	public void setResolution() {
		if (Main.webcam.isOpen())
			Main.webcam.close();
		Dimension[] dimensions = {dim};
		Main.webcam.setCustomViewSizes(dimensions);
		Main.webcam.setViewSize(dim);
		Main.webcam.open();
	}

	public void updateWebcamFeed() {
		Main.webcam.close();
		this.remove(panel);
		panel = new WebcamPanel(Main.webcam);
		this.add(panel);
		Main.webcam.open();
		panel.revalidate();
	}

}
