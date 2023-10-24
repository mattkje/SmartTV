package no.gruppe15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import no.gruppe15.command.Command;
import no.gruppe15.message.ErrorMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.MessageSerializer;

/**
 * Handles the TCP server socket(s).
 */
public class TvServer {
  public static final int PORT_NUMBER = 10025;
  public static final String CHANNEL_COUNT_COMMAND = "c";
  public static final String TURN_ON_COMMAND = "1";
  public static final String TURN_OFF_COMMAND = "0";
  public static final String GET_CHANNEL_COMMAND = "g";
  public static final String SET_CHANNEL_COMMAND = "s";
  public static final String OK_RESPONSE = "o";
  private static final String CHANNEL_COUNT_MESSAGE = "c";
  private static final String ERROR_MESSAGE = "e";
  private static final String CURRENT_CHANNEL_MESSAGE = "C";
  private final TvLogic logic;

  boolean isTcpServerRunning;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  public TvServer(TvLogic logic) {
    this.logic = logic;
  }

  /**
   * Start TCP server for this TV.
   */
  public void startServer() {
    ServerSocket listeningSocket = openListeningSocket();
    System.out.println("Server listening on port " + PORT_NUMBER);
    if (listeningSocket != null) {
      isTcpServerRunning = true;
      while (isTcpServerRunning) {
        Socket clientSocket = acceptNextClientConnection(listeningSocket);
        if (clientSocket != null) {
          System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());
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
      System.err.println("Could not open server socket: " + e.getMessage());
    }
    return listeningSocket;
  }

  private Socket acceptNextClientConnection(ServerSocket listeningSocket) {
    Socket clientSocket = null;
    try {
      clientSocket = listeningSocket.accept();
      socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);

    } catch (IOException e) {
      System.err.println("Could not accept client connection: " + e.getMessage());
    }
    return clientSocket;
  }

  private void handleClient(Socket clientSocket) {
    Message response;
    do {
      Command clientCommand = readClientRequest();
      System.out.println("Received from client: " + clientCommand);
      response = clientCommand.execute(logic);
      if (response != null) {
        sendResponseToClient(response);
      }
    } while (response != null);
  }

  /**
   * Read one message from the TCP socket - from the client.
   *
   * @return The received client message, or null on error
   */
  private Command readClientRequest() {
    Message clientCommand = null;
    try {
      String rawClientRequest = socketReader.readLine();

      clientCommand = MessageSerializer.fromString(rawClientRequest);
      if (!(clientCommand instanceof Command)) {
        System.err.println("Wrong message from the client: " + clientCommand);
        clientCommand = null;
      }
    } catch (IOException e) {
      System.err.println("Could not receive client request: " + e.getMessage());
    }
    return (Command) clientCommand;
  }

  /**
   * Send a response from the server to the client, over the TCP socket.
   *
   * @param response The response to send to the client, NOT including the newline
   */
  private void sendResponseToClient(Message response) {
    socketWriter.println(MessageSerializer.toString(response));
  }

}
