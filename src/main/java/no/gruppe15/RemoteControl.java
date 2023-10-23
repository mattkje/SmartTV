package no.gruppe15;

import javafx.application.Application;
import no.gruppe15.ui.RemoteApp;

import static no.gruppe15.TvServer.PORT_NUMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Remote control for a TV - a TCP client.
 */
public class RemoteControl {
  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  public static void main(String[] args) {
    Thread remoteThread = new Thread(() -> {
      RemoteControl remoteControl = new RemoteControl();
      remoteControl.run();
    });
    remoteThread.start();

    Application.launch(RemoteApp.class, args);

  }

  private void run() {
    try {
      socket = new Socket("localhost", PORT_NUMBER);
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
      socketReader = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));

      Scanner userInputScanner = new Scanner(System.in);
      Boolean exit = false;
      while (!exit) {
        System.out.print("Enter a message: ");
        String input = userInputScanner.nextLine();

        socketWriter.println(input);

        if (input.equals("exit")){
          exit = true;
        }

        //TODO: ADD exception handling if there is a wrong command
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
}
