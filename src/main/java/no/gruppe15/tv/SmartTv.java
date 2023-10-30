package no.gruppe15.tv;

import no.gruppe15.tv.gui.SmartTVController;
import no.gruppe15.tv.gui.SmartTvApp;

/**
 * A starting point for the SmartTV application.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 30.10.2023
 */
public class SmartTv {

  /**
   * Run the Smart TV, including the server.
   *
   * @param args Command line arguments, not used
   */
  public static void main(String[] args) {
    SmartTVController controller = new SmartTVController();
    TvLogic logic = new TvLogic(100);
    TvServer server = new TvServer(logic);
    Thread serverThread = new Thread(server::startServer);
    serverThread.start();
    SmartTvApp.startApp(controller);
  }
}
