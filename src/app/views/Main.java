package app.views;

import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{

    public static SettingsPanel settingsPanel;
    public static VideoPanel videoPanel;
    public static WebcamPanel webcamPanel;

    public static int defWidth = 1000;
    public static int defHeight = 1000;

    public Main(){
        setTitle("Lab Recon Webcam");
        setSize(1200, 500);
        setLayout(new BorderLayout());

        videoPanel = new VideoPanel();
        webcamPanel = videoPanel.getPanel();
        settingsPanel = new SettingsPanel();

        settingsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        webcamPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(settingsPanel, BorderLayout.NORTH);
        add(webcamPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Main frame = new Main();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);
    }
}
