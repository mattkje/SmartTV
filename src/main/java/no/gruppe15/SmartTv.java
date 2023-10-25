package no.gruppe15;

import javafx.application.Application;
import no.gruppe15.ui.SmartTvApp;

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
    Application.launch(SmartTvApp.class, args);
  }
}
