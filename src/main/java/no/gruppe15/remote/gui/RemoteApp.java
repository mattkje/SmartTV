package no.gruppe15.remote.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.gruppe15.tv.ClientHandler;
import no.gruppe15.tv.TvLogic;
import no.gruppe15.tv.TvServer;

import java.io.IOException;
import java.net.Socket;

import static no.gruppe15.tv.TvServer.PORT_NUMBER;

/**
 * GUI For the remote controller.
 */

public class RemoteApp extends Application{

  private FXMLLoader loader;

  private static RemoteController controller;

  private static ClientHandler remoteClient;

  public static void startApp() throws IOException {
    controller = new RemoteController();
    remoteClient = new ClientHandler(new Socket("localhost", PORT_NUMBER), new TvServer(new TvLogic(100)));
    launch();
  }
  @Override
  public void start(Stage primaryStage) throws Exception {




    loader = new FXMLLoader(this.getClass().getResource("/no/gruppe15/fxml/Remote.fxml"));
    controller.setPrintWriter(remoteClient.getSocketWriter(), remoteClient.getSocketReader());
    loader.setController(controller);
    Scene scene = new Scene(loader.load());
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.getIcons().add(new Image("/no/gruppe15/media/remote.png"));
    primaryStage.setTitle("");
    primaryStage.show();

    Thread remoteThread = new Thread(remoteClient::run);
    remoteThread.start();
  }
}
