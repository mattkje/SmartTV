package no.gruppe15.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.gruppe15.RemoteControl;

/**
 * GUI For the remote controller.
 */

public class RemoteApp extends Application {

  private FXMLLoader loader;
  @Override
  public void start(Stage primaryStage) throws Exception {
    RemoteController controller = new RemoteController();
    RemoteControl remoteControl = new RemoteControl();

    Thread remoteThread = new Thread(remoteControl::run);
    remoteThread.start();


    loader = new FXMLLoader(this.getClass().getResource("/no/gruppe15/fxml/Remote.fxml"));
    controller.setPrintWriter(remoteControl.getSocketWriter(), remoteControl.getSocketReader());
    loader.setController(controller);
    Scene scene = new Scene(loader.load());
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.getIcons().add(new Image("/no/gruppe15/media/remote.png"));
    primaryStage.setTitle("");
    primaryStage.show();
  }
}
