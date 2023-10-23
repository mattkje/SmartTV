package no.gruppe15;


import static no.gruppe15.TvServer.PORT_NUMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Remote control for a TV - a TCP client.
 */
public class RemoteControl {
  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  public static void main(String[] args) {
    RemoteControl remoteControl = new RemoteControl();
    remoteControl.run();
  }

  private void run() {
    try {
      socket = new Socket("localhost", PORT_NUMBER);
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
      socketReader = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));

      sendCommandToServer("1");
      sendCommandToServer("n");
      sendCommandToServer("c");


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
}
