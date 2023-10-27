package no.gruppe15.remote;

import no.gruppe15.tv.TvLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static no.gruppe15.tv.TvServer.PORT_NUMBER;

/**
 * Put Client methods here
 */

public class RemoteClient {

    private static final String SERVER_HOST = "localhost";
    private TvLogic logic;

    private Socket socket;

    private final BufferedReader socketReader;

    private final PrintWriter socketWriter;

    public RemoteClient(Socket socket, TvLogic logic) throws IOException {
        this.logic = logic;
        this.socket = socket;
        socketWriter = new PrintWriter(socket.getOutputStream(), true);
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        try {
            socket = new Socket(SERVER_HOST, PORT_NUMBER);

            Scanner userInputScanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.print("Enter a message: ");
                String input = userInputScanner.nextLine();

                socketWriter.println(input);

                if (input.equals("exit")) {
                    exit = true;
                }

                String serverResponse = socketReader.readLine();
                System.out.println("Server Response: " + serverResponse);
            }
            socketWriter.close();
            socketReader.close();
            socket.close();


        } catch (IOException e) {
            System.err.println("Could not establish connection to the server: " + e.getMessage());
        }
    }

    private void sendCommandToServer(String command) throws IOException {
        System.out.println("Sending command: " + command);
        socketWriter.println(command);
        String serverResponse = socketReader.readLine();
        System.out.println("  >>> " + serverResponse);
    }

    public PrintWriter getSocketWriter(){
        return this.socketWriter;
    }

    public BufferedReader getSocketReader() {
        return this.socketReader;
    }


}
