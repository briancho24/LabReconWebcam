package app.views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingsPanel extends JPanel {

    public int defWidth = Main.defWidth;
    public int defHeight = Main.defHeight;

    public static VideoPanel videoPanel = Main.videoPanel;

    private JSlider resoSlider;

    public SettingsPanel(){

        setLayout(new GridLayout(4,2,0,5));

        JLabel resoLabel = new JLabel("Resolution:");

        resoSlider = new JSlider(0,100);
        resoSlider.setMinimum(1);
        resoSlider.setMaximum(100);
        resoSlider.setPaintTicks(true);
        resoSlider.setVisible(true);
        resoSlider.addChangeListener(new ResoChangeListener());

        JButton switchSetting = new JButton("Settings");
        switchSetting.setText("Settings");
        switchSetting.setSize(30,30);
        switchSetting.setMaximumSize(new Dimension(50,200));
        switchSetting.setVisible(true);
        switchSetting.addChangeListener(new SettingChangeListener());

        add(switchSetting, BorderLayout.NORTH);
        add(resoLabel);
        add(resoSlider);
    }

    public class ResoChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider root = (JSlider) e.getSource();
            if (root.getValueIsAdjusting()) {
                double factor = root.getValue()/ 100.0;
                videoPanel.newD = new Dimension((int)(factor * defWidth), (int)(factor * defHeight));
                System.out.println("" + factor + " defWith " + defWidth);
                System.out.println("1"+videoPanel.newD);
                videoPanel.setReso();
            }
        }
    }

    public class SettingChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JButton root = (JButton) e.getSource();
            if (root.getModel().isPressed()) {
                Main.viewSettings(false);
            }
        }
    }
}