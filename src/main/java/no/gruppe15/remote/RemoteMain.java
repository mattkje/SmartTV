package no.gruppe15.remote;

import no.gruppe15.remote.gui.RemoteApp;

/**
 * This class represents a starting point for the Remote application.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 02.11.2023
 */

public class RemoteMain {

  /**
   * This method is the entry point for starting the remote application.
   *
   * @param args Command-line arguments (not used in this application).
   */
  public static void main(String[] args) {
    RemoteClient client = new RemoteClient();
    if (client.start()) {
      System.out.println("Server found: " + client.getServerHost());
    } else {
      System.out.println("No server was found. Starting in disconnected mode");
    }
    RemoteApp.startApp(client);
    client.stop();
  }
}
