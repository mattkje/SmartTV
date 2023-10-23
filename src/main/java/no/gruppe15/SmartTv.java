package no.gruppe15;

import javafx.application.Application;
import no.gruppe15.ui.SmartTVApp;

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

    /*
    TvLogic logic = new TvLogic(13);
    TvServer server = new TvServer(logic);
    server.startServer();
    Application.launch(SmartTVApp.class, args);
     */


    TvLogic logic = new TvLogic(13);

    // Start the server in a separate thread
    Thread serverThread = new Thread(() -> {
      TvServer server = new TvServer(logic);
      server.startServer();
    });
    serverThread.start();

    // Launch the GUI in the main thread
    Application.launch(SmartTVApp.class, args);

  }
}
