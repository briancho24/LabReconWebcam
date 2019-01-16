package app.views;

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

public class SettingsPanel extends JPanel {

	public int defWidth = Main.defWidth;
	public int defHeight = Main.defHeight;

	public static VideoPanel videoPanel = Main.videoPanel;

	private JSlider resoSlider;
	private JLabel resoLabel;
	private JLabel pad1;
	private JLabel header;
	private JLabel camera;
	private JLabel portNumLabel;
	private JButton btnUpdate;
	private JToggleButton btnToggleServer;
	private JTextField portNum;

	private boolean runServer;

	public SettingsPanel() {

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		resoLabel = new JLabel("Resolution: " + videoPanel.newD.width + "x" + videoPanel.newD.height);
		resoLabel.setBorder(new EmptyBorder(30,0,10,0));
		resoLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 18));

		header = new JLabel("Settings:");
		header.setFont(new Font("Lucida Sans", Font.PLAIN, 22));
		header.setBorder(new EmptyBorder(20,0,20,0));

		camera = new JLabel("Camera:");
		camera.setBorder(new EmptyBorder(0,0,10,0));
		camera.setFont(new Font("Lucida Sans", Font.PLAIN, 18));

		portNumLabel = new JLabel("Port: " + Main.port);
		portNumLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
		portNumLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 18));

		resoSlider = new JSlider(0, 100);
		resoSlider.setValue(100);
		resoSlider.setMinimum(1);
		resoSlider.setMaximum(100);
		resoSlider.setBorder(new EmptyBorder(0,120,0,20));
		resoSlider.setPaintTicks(true);
		resoSlider.setMajorTickSpacing(10);
		resoSlider.setVisible(true);
		resoSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider root = (JSlider) e.getSource();
				if (root.getValueIsAdjusting()) {
					double factor = root.getValue() / 100.0;
					videoPanel.newD = new Dimension((int) (factor * defWidth), (int) (factor * defHeight));
//				System.out.println("" + factor + " defWith " + defWidth);
//				System.out.println("1" + videoPanel.newD);
					resoLabel.setText("Resolution: " + videoPanel.newD.width + "x" + videoPanel.newD.height);
				}
			}
		});

		btnToggleServer = new JToggleButton("Run/Stop");
		btnToggleServer.setSelected(true);
        btnToggleServer.setFocusPainted(false);
		btnToggleServer.setBackground(Color.RED);
		btnToggleServer.setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return Color.GREEN;
            }
        });
		btnToggleServer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					Main.runServer = true;
					btnToggleServer.setBackground(Color.GREEN);
					System.out.println(runServer);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					Main.runServer = false;
                    btnToggleServer.setBackground(Color.RED);
					System.out.println(runServer);
				}
			}
		});

		btnUpdate = new JButton("Set Dimension");
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				videoPanel.getPanel().stop();
				videoPanel.setReso();
				videoPanel.getPanel().start();
			}
		});

		pad1 = new JLabel();
		pad1.setBorder(new EmptyBorder(30,0,0,0));

		portNum = new JTextField();
		portNum.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!portNum.getText().isEmpty()) {
					Main.port = Integer.parseInt(portNum.getText());
					portNumLabel.setText("Port: " + Main.port);
					Main.streamer.stop();
					Main.streamer = new LabReconStreamer(Main.port, Main.webcam, 60, true);
					videoPanel.getPanel().stop();
					videoPanel.getPanel().start();
				}
			}
		});
		add(header);
		add(pad1);
		add(camera);
		add(btnToggleServer);
		add(pad1);
		add(resoLabel);
		add(resoSlider);
		add(pad1);
		add(btnUpdate);
		add(portNumLabel);
		add(portNum);
	}


	public class SettingChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JButton root = (JButton) e.getSource();
			if (root.getModel().isPressed()) {
//				Main.viewSettings(false);
			}
		}
	}
}