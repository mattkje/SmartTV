package no.gruppe15.remote;

import no.gruppe15.remote.gui.RemoteApp;

/**
 * This class represents a starting point for the Remote application.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 30.10.2023
 */

public class RemoteMain {

  /**
   * This method is the entry point for starting the remote application.
   *
   * @param args Command-line arguments (not used in this application).
   */
  public static void main(String[] args) {
    RemoteLogic logic = new RemoteLogic();
    if (logic.start()) {
      System.out.println("Starting in connected mode");
    } else {
      System.out.println("Starting in disconnected mode");
    }
    RemoteApp.startApp(logic);
    logic.stop();
  }
}
