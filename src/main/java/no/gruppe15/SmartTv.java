package no.gruppe15;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import no.gruppe15.ui.SmartTVApp;
import no.gruppe15.ui.SmartTVController;

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
    Application.launch(SmartTVApp.class, args);
  }
}
