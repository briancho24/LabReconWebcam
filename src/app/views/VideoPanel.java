package app.views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;

public class VideoPanel{

    public static Dimension newD = new Dimension(1000,1000);
    private WebcamPanel panel;
    private Webcam webcam;

    public VideoPanel(){

        webcam = Webcam.getDefault();

        /*
        Dimension[] dimensions = {newD};
        webcam.setCustomViewSizes(dimensions);

        //max 640, 480

        //webcam.setViewSize(WebcamResolution.VGA.getSize());

        webcam.setViewSize(newD);

        */
        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
    }

    public WebcamPanel getPanel(){
        return panel;
    }

    public void setReso(Dimension newD){
        webcam.close();
        webcam.setViewSize(newD);
        webcam.open();
    }
}
