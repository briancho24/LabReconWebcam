package app.views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import java.awt.*;


public class WebcamPanelExample {

    public static void main(String[] args) throws InterruptedException {

        Webcam webcam = Webcam.getDefault();
        final Dimension newD = new Dimension(1000,1000);
        Dimension[] dimensions = {newD};
        webcam.setCustomViewSizes(dimensions);

        for(Dimension i: webcam.getCustomViewSizes()){
            System.out.println(i);
        }
        //max 640, 480

        //webcam.setViewSize(WebcamResolution.VGA.getSize());

        final int origW = (int)WebcamResolution.VGA.getSize().getWidth();
        final int origH = (int)WebcamResolution.VGA.getSize().getHeight();

        webcam.setViewSize(newD);


        final JSlider resolution = new JSlider(0,100);
        resolution.setVisible(true);

        ChangeListener resoChange = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (source.getValueIsAdjusting()) {
                    int factor = source.getValue()/ 100;
                    newD.setSize(factor * origW, factor * origH);
                }
            }
        };


        resolution.addChangeListener(resoChange);

        Panel resoPanel = new Panel();
        resoPanel.add(resolution);
        resoPanel.setVisible(true);

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);

        JFrame window = new JFrame("Test webcam panel");
        window.add(resoPanel);
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500,500);
        window.setVisible(true);
    }
}