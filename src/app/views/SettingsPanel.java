package app.views;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class SettingsPanel extends JPanel {

	private int defWidth = Main.defWidth;
	private int defHeight = Main.defHeight;

	private static VideoPanel videoPanel = Main.videoPanel;
	private static boolean runServer;

	private JSlider resoSlider;
	private JLabel lblReso;
	private JLabel lblPad1;
	private JLabel lblCamera;
	private JLabel lblPortNum;
	private JButton btnUpdate;
	private JToggleButton btnToggleServer;
	private JTextField portNum;
	private JComboBox webcamNames;

	public SettingsPanel() {

		Font settingsFont = new Font("Apple Casual", Font.PLAIN, 14);
		GridLayout layout = new GridLayout(6,1);
		setLayout(new GridLayout(6, 1));
		runServer = Main.runServer;

		webcamNames = new JComboBox();
		for (String w : Main.webcamList.keySet())
			webcamNames.addItem(w);
		webcamNames.setSelectedItem(Main.webcam.getName());

		lblReso = new JLabel("Resolution: " + videoPanel.getDim().width + "x" + videoPanel.getDim().height);
		lblReso.setFont(settingsFont);

		lblCamera = new JLabel("Camera: " + Main.webcam.getName());
		lblCamera.setFont(settingsFont);

		lblPortNum = new JLabel("Port: " + Main.port);
		lblPortNum.setFont(settingsFont);

		resoSlider = new JSlider(0, 100);
		resoSlider.setValue(100);
		resoSlider.setMinimum(1);
		resoSlider.setMaximum(100);
		resoSlider.setPaintTicks(true);
		resoSlider.setMajorTickSpacing(5);
		resoSlider.setSnapToTicks(true);
		resoSlider.setVisible(true);


		btnToggleServer = new JToggleButton("Run/Stop");
		btnToggleServer.setSelected(true);
		btnToggleServer.setSize(100,20);
		btnToggleServer.setFocusPainted(false);
		btnToggleServer.setBackground(Color.RED);
		btnToggleServer.setUI(new MetalToggleButtonUI() {
			@Override
			protected Color getSelectColor() {
				return Color.GREEN;
			}
		});


		btnUpdate = new JButton("Set Resolution");
		btnUpdate.setContentAreaFilled(false);

		lblPad1 = new JLabel();

		portNum = new JTextField();

		addListeners();


		EmptyBorder padding = new EmptyBorder(10,10,10,10);
		lblCamera.setBorder(padding);
		webcamNames.setBorder(padding);
		btnToggleServer.setBorder(padding);
		lblReso.setBorder(padding);
		resoSlider.setBorder(padding);
		lblPortNum.setBorder(padding);

		add(lblCamera);
		add(webcamNames);
		add(btnToggleServer);
		add(lblReso);
		add(resoSlider);
		add(lblPortNum);
	}

	private void addListeners() {
		resoSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider root = (JSlider) e.getSource();
				if (root.getValueIsAdjusting()) {
					double factor = root.getValue() / 100.0;
					videoPanel.setDim(new Dimension((int) (factor * defWidth), (int) (factor * defHeight)));
					lblReso.setText("Resolution: " + videoPanel.getDim().width + "x" + videoPanel.getDim().height);
				} else {
					videoPanel.getWebcamPanel().stop();
					videoPanel.setResolution();
					videoPanel.getWebcamPanel().start();
				}
			}
		});
		btnToggleServer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					Main.runServer = true;
					btnToggleServer.setBackground(Color.GREEN);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					Main.runServer = false;
					btnToggleServer.setBackground(Color.RED);
				}
			}
		});
		portNum.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!portNum.getText().isEmpty()) {
					Main.port = Integer.parseInt(portNum.getText());
					lblPortNum.setText("Port: " + Main.port);
					Main.streamer.stop();
					Main.streamer = new LabReconStreamer(Main.port, Main.webcam, 60, true);
					videoPanel.getWebcamPanel().stop();
					videoPanel.getWebcamPanel().start();
				}
			}
		});

		webcamNames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JComboBox cb = (JComboBox)e.getSource();
				String cam = (String)cb.getSelectedItem();
				Main.ini.put("streamer", "camera", cam);
				try {
					Main.ini.store();
					Main.webcam = Main.webcamList.get(cam);
					Main.streamer.setWebcam(Main.webcam);
					Main.videoPanel.updateWebcamFeed();
					lblCamera.setText("Camera: " + Main.webcam.getName());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(cam);
			}
		});
	}


}