package app.views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingsPanel extends JPanel {

    public static Dimension newD;
    public int defWidth = Main.defWidth;
    public int defHeight = Main.defHeight;

    private JSlider resoSlider;

    public SettingsPanel(){

        setLayout(new GridLayout(4,2,0,5));

        JLabel resoLabel = new JLabel("Resolution:");

        resoSlider = new JSlider(0,100);
        resoSlider.setVisible(true);
        resoSlider.addChangeListener(new ResoChangeListener());

        add(resoLabel);
        add(resoSlider);
    }

    public class ResoChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider root = (JSlider) e.getSource();
            if (root.getValueIsAdjusting()) {
                double factor = root.getValue()/ 100;
                Dimension newD = new Dimension((int)(factor * defWidth), (int)(factor * defHeight));
                Main.videoPanel.setReso(newD);
            }
        }
    }
}