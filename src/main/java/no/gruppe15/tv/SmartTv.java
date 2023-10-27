package no.gruppe15.tv;

import javafx.application.Application;
import no.gruppe15.tv.gui.SmartTVController;
import no.gruppe15.tv.gui.SmartTvApp;

/**
 * Run the whole Smart TV, including the TCP socket communication.
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
    TvServer server = new TvServer(logic, controller);
    Thread serverThread = new Thread(server::startServer);
    serverThread.start();
    SmartTvApp.startApp(controller);
  }
}
