package no.calmmatt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SmartTV {

    public static final int PORT_NUMBER = 10025;
    boolean isTvOn;

    int numberOfChannels;

    int currentChannel;

    boolean isTCPServerRunning;

    private BufferedReader socketReader;

    private PrintWriter socketWriter;

    /**
     * Create an instance of SmartTV
     *
     * @param numberOfChannels The total number of channels.
     */
    public SmartTV(int numberOfChannels){
        if (numberOfChannels < 1){
            throw new IllegalArgumentException("Number of channels must be positive");
        }

        this.numberOfChannels = numberOfChannels;
        isTvOn = false;
        currentChannel = 1;
    }

    public static void main(String[] args){
        SmartTV tv = new SmartTV(13);
        tv.startServer();
    }

    /**
     * Start TCP server for this tv.
     */
    private void startServer() {
        ServerSocket listeningSocket = openListeningSocket();
        System.out.println("Server listening on port " + PORT_NUMBER);
        if (listeningSocket != null){
            isTCPServerRunning = true;
            while (isTCPServerRunning){
                Socket clientSocket = acceptNestClientConnection(listeningSocket);
                if (clientSocket != null){
                    System.out.println("New client connected from "+ clientSocket.getRemoteSocketAddress());
                    handleClient(clientSocket);
                }
            }

        }
    }


    private ServerSocket openListeningSocket() {
        ServerSocket listeningSocket = null;
        try {
            listeningSocket = new ServerSocket(PORT_NUMBER);
        } catch (IOException e) {
            System.err.println("Could not open server socket " + e.getMessage());
        }
        return listeningSocket;
    }

    private Socket acceptNestClientConnection(ServerSocket listeningSocket) {
        Socket clientSocket = null;

        try {
            clientSocket = listeningSocket.accept();
            socketReader = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Could not " + e.getMessage());
        }
        return clientSocket;
    }

    private void handleClient(Socket clientSocket) {


        String response;
        do {
            String clientRequest = readClientRequest();
            System.out.println("Recieved from client " + clientRequest);
            response = handleClientRequest(clientRequest);
            if(response != null){
                sendResponseToClient(response);
            }
        } while (response != null);

    }


    private String handleClientRequest(String clientRequest) {
        String response = null;
        if (clientRequest != null){
            if (clientRequest.equals("n")){
                if (isTvOn){
                    response = "n" + numberOfChannels;
                } else {
                    response = "e";
                }

            }
        }

        return response;
    }

    private void sendResponseToClient(String response) {
        socketWriter.println(response);
    }

    private String readClientRequest() {
        String clientRequest = null;
        try {
            clientRequest = socketReader.readLine();
        } catch (IOException e) {
            System.err.println("Could not recieve client request " + e.getMessage());
        }
        return clientRequest;
    }
}
