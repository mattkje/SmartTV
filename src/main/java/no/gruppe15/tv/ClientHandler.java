package no.gruppe15.tv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import no.gruppe15.command.Command;
import no.gruppe15.message.CurrentChannelMessage;
import no.gruppe15.message.Message;
import no.gruppe15.message.MessageSerializer;
import no.gruppe15.message.TvStateMessage;

/**
 * This class is responsible for handling the TCP communication for one client.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 30.10.2023
 */
public class ClientHandler extends Thread {
  private final Socket socket;

  private final TvServer server;

  private final BufferedReader socketReader;

  private final PrintWriter socketWriter;

  /**
   * Creates an instance of ClientHandler.
   *
   * @param socket Socket associated with this client
   * @param server Reference to the main TCP server class
   * @throws IOException When something goes wrong with establishing the input or output streams
   */
  public ClientHandler(Socket socket, TvServer server) throws IOException {
    this.server = server;
    this.socket = socket;
    socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    socketWriter = new PrintWriter(socket.getOutputStream(), true);
  }

  /**
   * This method is responsible for handling client requests and executing commands.
   */
  @Override
  public void run() {
    Message response;
    while ((response = executeClientCommand()) != null) {
      if (isBroadcastMessage(response)) {
        server.sendResponseToAllClients(response);
      } else {
        sendToClient(response);
      }
    }
    System.out.println("Client " + socket.getRemoteSocketAddress() + " leaving");
    server.clientDisconnected(this);
  }


  /**
   * Reads a client request and executes the corresponding command.
   *
   * @return The response message from the executed command or null if the command is null.
   */
  private Message executeClientCommand() {
    Command clientCommand = readClientRequest();
    if (clientCommand == null) {
      return null;
    }
    System.out.println("Received a " + clientCommand.getClass().getSimpleName());
    return clientCommand.execute(server.getTvLogic());
  }

  /**
   * Checks if the given message is a broadcast message.
   *
   * @param response The response to check.
   * @return True if the response is a broadcast message, false otherwise.
   */
  private boolean isBroadcastMessage(Message response) {
    return response instanceof TvStateMessage
        || response instanceof CurrentChannelMessage;
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
        if (clientCommand != null) {
          System.err.println("Wrong message from the client: " + clientCommand);
        }
        clientCommand = null;
      }
    } catch (IOException e) {
      System.err.println("Could not receive client request: " + e.getMessage());
    } catch (NullPointerException e1) {
      System.out.println("Client lost connection");
    }
    assert clientCommand instanceof Command;
    return (Command) clientCommand;
  }

  /**
   * Send a response from the server to the client, over the TCP socket.
   *
   * @param message The message to send to the client
   */
  public void sendToClient(Message message) {
    socketWriter.println(MessageSerializer.toString(message));
  }

}
