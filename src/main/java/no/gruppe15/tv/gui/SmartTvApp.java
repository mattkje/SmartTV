package no.gruppe15.tv.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * GUI for the smart tv.
 */
public class SmartTvApp extends Application {

  private static SmartTVController controller;

  /**
   * Start app method
   *
   * @param controller controller for the SmartTV
   */
  public static void startApp(SmartTVController controller) {
    SmartTvApp.controller = controller;
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/no/gruppe15/fxml/SmartTV.fxml"));
    loader.setController(controller);
    Scene scene = new Scene(loader.load());
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.getIcons().add(new Image("/no/gruppe15/media/tv.png"));
    primaryStage.setTitle("SmartTV");
    primaryStage.show();
  }
}