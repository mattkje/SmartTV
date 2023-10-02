package no.calmmatt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static no.calmmatt.SmartTV.PORT_NUMBER;

public class RemoteControl {
    public static void main(String[] args){
        RemoteControl remoteControl = new RemoteControl();
        remoteControl.run();
    }

    private void run() {
        try {
            Socket socket = new Socket("localhost", PORT_NUMBER);
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse = socketReader.readLine();
            System.out.println("Server's response: " + serverResponse);

            socketWriter.println("Hallo");
        } catch (IOException e) {
            System.err.println("Could not establish connection to the server: " + e.getMessage());
        }
    }
}
