package no.gruppe15.remote.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.gruppe15.remote.RemoteLogic;

/**
 * GUI For the remote controller.
 */

public class RemoteApp extends Application {

  private static RemoteController controller;

  /**
   * This method should configure the remote application for startup.
   *
   * @param logic The Remote logic class
   */
  public static void startApp(RemoteLogic logic) {
    controller = new RemoteController();
    controller.setLogic(logic);
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader =
        new FXMLLoader(this.getClass().getResource("/no/gruppe15/fxml/Remote.fxml"));
    loader.setController(controller);
    Scene scene = new Scene(loader.load());
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.getIcons().add(new Image("/no/gruppe15/media/remote.png"));
    primaryStage.setTitle("");
    primaryStage.show();
  }
}
