package no.gruppe15.remote;

import static no.gruppe15.tv.TvServer.PORT_NUMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Application;
import no.gruppe15.command.Command;
import no.gruppe15.message.MessageSerializer;
import no.gruppe15.remote.gui.RemoteApp;


/**
 * Remote control for a TV - a TCP client.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 30.10.2023
 */
public class RemoteLogic {

  private static final String SERVER_HOST = "localhost";
  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  public static void main(String[] args) {
    Application.launch(RemoteApp.class, args);
  }

  /**
   * Attempts to establish a connection to the remote server. If successful,
   * initializes the necessary communication streams and sets up the connection.
   *
   * @return True if the connection to the server was successfully established; false otherwise.
   */
  public boolean start() {
    boolean connected = false;
    try {
      socket = new Socket(SERVER_HOST, PORT_NUMBER);
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      connected = true;
    } catch (IOException e) {
      System.err.println("Could not connect to the server: " + e.getMessage());
    }
    return connected;
  }

  /**
   * Stop the TCP client - close connection to the TV (server).
   */
  public void stop() {
    if (socket != null) {
      try {
        socket.close();
        socket = null;
        socketReader = null;
        socketWriter = null;
      } catch (IOException e) {
        System.err.println("Could not close the socket: " + e.getMessage());
      }
    }
  }

  /**
   * Send a command to the TV.
   *
   * @param command The command to send
   */
  public void sendCommand(Command command) {
    if (socketWriter != null && socketReader != null) {
      try {
        socketWriter.println(MessageSerializer.toString(command));
      } catch (Exception e) {
        System.err.println("Could not send a command: " + e.getMessage());
      }
    }
  }

  /**
   * This method checks if socket is null.
   *
   * @return true if socket is not null, false otherwise.
   */
  public boolean isServerRunning() {
    return socket != null;
  }

  /**
   * This method checks if the socket can write a byte.
   *
   * @return True if the socket is able to write byte, false otherwise.
   */
  public boolean isConnected() {
    if (socket != null && socket.isConnected() && !socket.isClosed()) {
      try {
        socket.getOutputStream().write(0);
        return true;
      } catch (IOException e) {
        return false;
      }
    }
    return false;
  }
}