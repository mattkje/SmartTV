package no.gruppe15.remote.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.gruppe15.remote.RemoteLogic;
import no.gruppe15.tv.ClientHandler;
import no.gruppe15.tv.TvLogic;
import no.gruppe15.tv.TvServer;

import java.io.IOException;
import java.net.Socket;

import static no.gruppe15.tv.TvServer.PORT_NUMBER;
import static no.gruppe15.tv.TvServer.SERVER_HOST;

/**
 * GUI For the remote controller.
 */

public class RemoteApp extends Application {

  private FXMLLoader loader;

  private static RemoteController controller;

  private static ClientHandler remoteClient;
  private Thread remoteThread;

  public static void startApp(RemoteLogic logic) throws IOException {
    controller = new RemoteController();
    controller.setLogic(logic);
    remoteClient = new ClientHandler(new Socket(SERVER_HOST, PORT_NUMBER), new TvServer(new TvLogic(100)));
    launch();
  }

  public static void startAppOffline(RemoteLogic logic) {
    controller = new RemoteController();
    controller.setLogic(logic);
    launch();
  }
  @Override
  public void start(Stage primaryStage) throws Exception {

    loader = new FXMLLoader(this.getClass().getResource("/no/gruppe15/fxml/Remote.fxml"));
    if (isServerAvailable()) {
      remoteThread = new Thread(remoteClient);
      remoteThread.start();
      controller.setPrintWriter(remoteClient.getSocketWriter(), remoteClient.getSocketReader());
    }
    loader.setController(controller);
    Scene scene = new Scene(loader.load());
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.getIcons().add(new Image("/no/gruppe15/media/remote.png"));
    primaryStage.setTitle("");
    primaryStage.show();

  }

  private boolean isServerAvailable() {
    try {
      new Socket(SERVER_HOST, PORT_NUMBER).close();
      return true;
    } catch (IOException e) {
      controller.setStatusOffline();
      return false;
    }
  }
}
