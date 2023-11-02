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
 * <p>Code Inspiration:
 * The foundation of this class is inspired by the work of Girts Strazdins.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 02.11.2023
 * @see <a href="https://github.com/strazdinsg/datakomm-tools/tree/master" target="_blank">External Repository</a>
 */
public class TvServer {

  public static final int PORT_NUMBER = 1238;

  private final TvLogic logic;

  boolean isServerRunning;

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

    if (listeningSocket == null) {
      System.err.println("Failed to open the listening socket. Server cannot start.");
      return;
    }

    System.out.println("Server is now listening on port " + PORT_NUMBER);

    isServerRunning = true;

    while (isServerRunning) {
      ClientHandler clientHandler = acceptNextClientConnection(listeningSocket);

      if (clientHandler != null) {
        connectedClients.add(clientHandler);
        clientHandler.start();
      }
    }
  }


  /**
   * Stops the TCP server and releases associated resources.
   */
  public void stopServer() {
    isServerRunning = false;
    try {
      listeningSocket.close();
    } catch (IOException e) {
      System.err.println("An error occurred while stopping the server");
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
      System.err.println(
          "Failed to open the server socket on port " + PORT_NUMBER + ": " + e.getMessage());
      return null;
    }
  }

  /**
   * Send a message to all currently connected clients.
   *
   * @param message The message to send
   */
  public void broadcastMessageToAllClients(Message message) {
    connectedClients.forEach(clientHandler -> clientHandler.sendResponseToClient(message));
  }

  /**
   * This method should remove any disconnected clients.
   *
   * @param clientHandler The current client handler.
   */
  public void removeDisconnectedClient(ClientHandler clientHandler) {
    connectedClients.remove(clientHandler);
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


}

