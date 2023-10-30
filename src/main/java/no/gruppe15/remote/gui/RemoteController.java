package no.gruppe15.remote.gui;

import static no.gruppe15.tv.TvServer.PORT_NUMBER;
import static no.gruppe15.tv.TvServer.SERVER_HOST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import no.gruppe15.command.ChannelCountCommand;
import no.gruppe15.command.ChannelDownCommand;
import no.gruppe15.command.ChannelUpCommand;
import no.gruppe15.command.SetChannelCommand;
import no.gruppe15.command.ToggleMuteCommand;
import no.gruppe15.command.TurnOffCommand;
import no.gruppe15.command.TurnOnCommand;
import no.gruppe15.remote.RemoteLogic;
import no.gruppe15.tv.ClientHandler;
import no.gruppe15.tv.TvLogic;
import no.gruppe15.tv.TvServer;

/**
 * Controller class for the RemoteApp class.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 23.10.2023
 */
public class RemoteController implements Initializable {

  private PrintWriter printWriter;

  private BufferedReader socketReader;

  private ClientHandler remoteClient;

  private Timeline timeline;

  private RemoteLogic logic;

  @FXML
  private TextField textField;
  @FXML
  private Label connection;
  private Timeline timer;
  private Socket socket;

  /**
   * Sets the PrintWriter and BufferedReader for communication with the remote server.
   *
   * @param printWriter  The PrintWriter for writing to the server.
   * @param socketReader The BufferedReader for reading from the server.
   */
  public void setPrintWriter(PrintWriter printWriter, BufferedReader socketReader) {
    this.printWriter = printWriter;
    this.socketReader = socketReader;
  }


  /**
   * This method handles the number buttons on the remote. It reads the number text,
   * and parses it in the textfield before sending it in 1 second intervals.
   *
   * @param event The event associated with the method call.
   */
  @FXML
  private void onNumberButtonClicked(ActionEvent event) {
    Button button = (Button) event.getSource();
    String buttonText = button.getText();

    textField.appendText(buttonText);

    if (timer != null) {

      timer.stop();
    }

    timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

      String text = textField.getText();
      logic.sendCommand(new SetChannelCommand(text));
      textField.setText("");
    }));
    timer.playFromStart();
  }


  /**
   * This method handles the turn on command.
   */
  public void turnOn() {
    logic.sendCommand(new TurnOnCommand());
  }

  /**
   * This method handles the turn-off command.
   */
  public void turnOff() {
    logic.sendCommand(new TurnOffCommand());
  }

  /**
   * This method handles the channel down command.
   */
  public void channelDown() {
    logic.sendCommand(new ChannelDownCommand());
  }

  /**
   * This method handles the channel up command.
   */
  public void channelUp() {
    logic.sendCommand(new ChannelUpCommand());
  }

  /**
   * This method handles the exit command.
   */
  public void mute() {
    logic.sendCommand(new ToggleMuteCommand());
  }

  /**
   * This method handles the number of channels command.
   */
  public void getNumberOfChannels() {
    logic.sendCommand(new ChannelCountCommand());
  }

  /**
   * Initializes the remote control application by creating a timeline that periodically checks
   * if the remote has a connection to the smart TV. It provides feedback if the connection is lost.
   *
   * @param url            The location used to resolve relative paths for the root object,
   *                       or null if the location is not known.
   * @param resourceBundle The resource bundle to be used for localization,
   *                       or null if this is not needed.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    timeline = new Timeline(new KeyFrame(
        Duration.millis(1000),
        event -> updateConnectionStatus()));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  /**
   * This method checks whether the remote is connected to the smart tv.
   */
  private void updateConnectionStatus() {
    if (printWriter != null) {
      connection.setText("Connected");
      connection.setTextFill(Color.LIME);
    } else {
      connection.setText("Not connected");
      connection.setTextFill(Color.RED);
    }

  }

  /**
   * This method should create a new socket to reconnect the remote.
   */
  public void reConnect() {
    Platform.runLater(() -> {
      connection.setTextFill(Color.YELLOW);
      connection.setText("Connecting...");
    });

    if (logic.start()) {
      timeline.play();
      setPrintWriter(logic.getSocketWriter(), logic.getSocketReader());
    } else {
      PauseTransition delay = new PauseTransition(Duration.seconds(.5));
      delay.setOnFinished(event -> setStatusOffline());
      delay.play();
    }

  }

  public void setLogic(RemoteLogic logic) {
    this.logic = logic;
  }

  public void setStatusOffline() {
    Platform.runLater(() -> {
      timeline.pause();
      connection.setTextFill(Color.GRAY);
      connection.setText("Offline");
    });
  }

}
