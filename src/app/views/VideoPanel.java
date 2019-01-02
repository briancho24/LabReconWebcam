package app.views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class VideoPanel extends JPanel{

    public static Dimension newD = new Dimension(1000,1000);
    public static WebcamPanel panel;
    public static Webcam webcam;

    public VideoPanel(){

        setLayout(new BorderLayout());

        webcam = Webcam.getDefault();

        Dimension[] dimensions = {newD};
        webcam.setCustomViewSizes(dimensions);

        //max 640, 480

        //webcam.setViewSize(WebcamResolution.VGA.getSize());

        webcam.setViewSize(newD);

        webcam.close();
        //webcam = Webcam.getDefault();
        webcam.open();

        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);


        JButton switchSetting = new JButton("Settings");
        switchSetting.setText("Settings");
        switchSetting.setSize(30,30);
        switchSetting.setMaximumSize(new Dimension(50,200));
        switchSetting.setVisible(true);
        switchSetting.addChangeListener(new SettingChangeListener());

        add(switchSetting, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    public WebcamPanel getPanel(){
        return panel;
    }

    public void setReso(){

        Dimension[] dimensions = {newD};
        webcam.setCustomViewSizes(dimensions);
        webcam.setViewSize(newD);
    }

    public class SettingChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JButton root = (JButton) e.getSource();
            if (root.getModel().isPressed()) {
                Main.viewSettings(true);
            }
        }
    }
}
