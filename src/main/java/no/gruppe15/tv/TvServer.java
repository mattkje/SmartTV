package no.gruppe15.tv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import no.gruppe15.message.Message;

/**
 * This class is responsible for managing the TCP server socket(s) for the TV application.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 30.10.2023
 */
public class TvServer {

  public static final int PORT_NUMBER = 10025;

  public static final String SERVER_HOST = "localhost";

  private final TvLogic logic;

  boolean isTcpServerRunning;

  private final List<ClientHandler> connectedClients = new ArrayList<>();
  private ServerSocket listeningSocket;

  public TvServer(TvLogic logic) {
    this.logic = logic;
  }

  /**
   * Start TCP server for this TV.
   */
  public void startServer() {
    listeningSocket = openListeningSocket();
    System.out.println("Server listening on port " + PORT_NUMBER);
    if (listeningSocket != null) {
      isTcpServerRunning = true;
      while (isTcpServerRunning) {
        ClientHandler clientHandler = acceptNextClientConnection(listeningSocket);
        if (clientHandler != null) {
          connectedClients.add(clientHandler);
          clientHandler.start();
        }
      }
    }
  }

  public void stopServer() {
    isTcpServerRunning = false;
    try {
      listeningSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Server stopped.");
  }

  /**
   * Opens a server socket to listen for incoming client connections on the specified port.
   *
   * @return The opened ServerSocket if successful, or null on failure.
   */
  private ServerSocket openListeningSocket() {
    try {
      return new ServerSocket(PORT_NUMBER);
    } catch (IOException e) {
      System.err.println("Could not open server socket: " + e.getMessage());
      return null;
    }
  }


  /**
   * Accepts the next client connection from the given ServerSocket and
   * creates a ClientHandler for it.
   *
   * @param listeningSocket The ServerSocket to accept the connection from.
   * @return The ClientHandler for the new client if successful, or null on failure.
   */
  private ClientHandler acceptNextClientConnection(ServerSocket listeningSocket) {
    try {
      Socket clientSocket = listeningSocket.accept();
      System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());
      return new ClientHandler(clientSocket, this);
    } catch (IOException e) {
      System.err.println("Could not accept client connection: " + e.getMessage());
      return null;
    }
  }

  /**
   * Get the associated TV logic.
   *
   * @return The TV logic
   */
  public TvLogic getTvLogic() {
    return logic;
  }

  /**
   * Send a message to all currently connected clients.
   *
   * @param message The message to send
   */
  public void sendResponseToAllClients(Message message) {
    for (ClientHandler clientHandler : connectedClients) {
      clientHandler.sendToClient(message);
    }
  }

  public void clientDisconnected(ClientHandler clientHandler) {
    connectedClients.remove(clientHandler);
  }
}

