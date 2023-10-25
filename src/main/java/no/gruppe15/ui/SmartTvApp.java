package no.gruppe15.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.gruppe15.TvLogic;
import no.gruppe15.TvServer;

/**
 * GUI for the smart tv.
 */
public class SmartTvApp extends Application {
  private FXMLLoader loader;

  @Override
  public void start(Stage primaryStage) throws Exception {
    SmartTVController controller = new SmartTVController();
    TvLogic logic = new TvLogic(100);
    TvServer server = new TvServer(logic, controller);
    Thread serverThread = new Thread(server::startServer);
    serverThread.start();


    loader = new FXMLLoader(this.getClass().getResource("/no/gruppe15/fxml/SmartTV.fxml"));
    loader.setController(controller);
    Scene scene = new Scene(loader.load());
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.getIcons().add(new Image("/no/gruppe15/media/tv.png"));
    primaryStage.setTitle("SmartTV");
    primaryStage.show();
  }
}