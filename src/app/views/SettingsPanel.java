package app.views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
	private JButton btnUpdate;
	private JToggleButton btnToggleServer;

	private boolean runServer;

	public SettingsPanel() {

		setLayout(new GridLayout(4, 2, 0, 5));

		resoLabel = new JLabel("Resolution: " + videoPanel.newD.width + "x" + videoPanel.newD.height);

		resoSlider = new JSlider(0, 100);
		resoSlider.setMinimum(1);
		resoSlider.setMaximum(100);
		resoSlider.setPaintTicks(true);
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
		btnToggleServer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					Main.runServer = true;
					System.out.println(runServer);
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					Main.runServer = false;
					System.out.println(runServer);
				}
			}
		});

		btnUpdate = new JButton("Set Dimension");
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				videoPanel.getPanel().stop();
				videoPanel.setReso();
				videoPanel.getPanel().start();
			}
		});

		add(btnToggleServer);
		add(resoLabel);
		add(resoSlider);
		add(btnUpdate);
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