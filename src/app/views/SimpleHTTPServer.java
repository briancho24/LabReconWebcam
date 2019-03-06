package app.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Java program to create a simple HTTP Server to demonstrate how to use * ServerSocket and Socket class. * * @author Javin Paul
 */
public class SimpleHTTPServer {
    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(55555);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 55555.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();

            if(clientSocket != null) {
                System.out.println("Connected");
            }
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());


        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("\r\n");
        out.println("<html><p> Hello world </p></html>");
        out.flush();

        out.close();

        clientSocket.close();
        serverSocket.close();
    }
}
