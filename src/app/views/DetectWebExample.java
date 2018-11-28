package app.views;


import com.github.sarxos.webcam.Webcam;

public class DetectWebExample {

    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();
        if (webcam != null) {
            System.out.println("Webcam: " + webcam.getName());
        } else {
            System.out.println("No webcam detected");
        }
    }
}