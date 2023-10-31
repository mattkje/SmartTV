package no.gruppe15.remote;

import java.io.IOException;
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
   * @throws IOException If there is an issue with IO operations.
   */
  public static void main(String[] args) throws IOException {
    RemoteLogic logic = new RemoteLogic();
    if (logic.start()) {
      RemoteApp.startApp(logic);
      logic.stop();
    } else {
      RemoteApp.startAppOffline(logic);
      logic.stop();
    }
  }
}
