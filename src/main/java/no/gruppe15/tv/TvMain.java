package no.gruppe15.tv;

import no.gruppe15.tv.gui.SmartTvApp;
import no.gruppe15.tv.gui.SmartTvController;

/**
 * A starting point for the SmartTV application.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 02.11.2023
 */
public class TvMain {

  /**
   * Run the Smart TV, including the server.
   *
   * @param args Command line arguments, not used
   */
  public static void main(String[] args) {
    SmartTvController controller = new SmartTvController();
    TvLogic logic = new TvLogic(100);
    logic.setController(controller);
    TvServer server = new TvServer(logic);
    Thread serverThread = new Thread(server::startServer);
    serverThread.start();
    SmartTvApp.startApp(controller);
    server.stopServer();
  }
}
