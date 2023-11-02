package no.gruppe15.remote.gui;

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
import no.gruppe15.command.IgnoreCommand;
import no.gruppe15.command.SetChannelCommand;
import no.gruppe15.command.ToggleMuteCommand;
import no.gruppe15.command.TurnOffCommand;
import no.gruppe15.command.TurnOnCommand;
import no.gruppe15.remote.RemoteClient;

/**
 * Controller class for the RemoteApp class.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 02.11.2023
 */
public class RemoteController implements Initializable {

  private Timeline timeline;

  private RemoteClient logic;

  @FXML
  private TextField textField;
  @FXML
  private Label connection;
  private Timeline timer;

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


    feedbackUpdate(buttonText);
  }

  private void feedbackUpdate(String buttonText) {
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
  @FXML
  public void turnOn() {
    logic.sendCommand(new TurnOnCommand());
  }

  /**
   * This method handles the turn-off command.
   */
  @FXML
  public void turnOff() {
    logic.sendCommand(new TurnOffCommand());
  }

  /**
   * This method handles the channel down command.
   */
  @FXML
  public void channelDown() {
    logic.sendCommand(new ChannelDownCommand());
  }

  /**
   * This method handles the channel up command.
   */
  @FXML
  public void channelUp() {
    logic.sendCommand(new ChannelUpCommand());
  }

  /**
   * This method handles the exit command.
   */
  @FXML
  public void mute() {
    logic.sendCommand(new ToggleMuteCommand());
  }

  /**
   * This method handles the number of channels command.
   */
  @FXML
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
    if (logic.isServerRunning()) {
      connection.setText("Connected");
      connection.setTextFill(Color.LIME);
    } else {
      connection.setText("Disconnected");
      connection.setTextFill(Color.RED);
    }
  }

  /**
   * This method should create a new socket to reconnect the remote.
   */
  @FXML
  public void reConnect() {
    if (logic.isConnected()) {
      statusLoad("Already connected");
      logic.sendCommand(new IgnoreCommand());
      return;
    }
    statusLoad("Connecting...");
    if (logic.start()) {
      timeline.play();
    } else {
      PauseTransition delay = new PauseTransition(Duration.seconds(.5));
      delay.setOnFinished(event -> setStatusDisconnected());
      delay.play();
    }
  }

  private void statusLoad(String status) {
    Platform.runLater(() -> {
      connection.setTextFill(Color.YELLOW);
      connection.setText(status);
    });
  }

  /**
   * This method sets the logic.
   *
   * @param logic The RemoteLogic.
   */
  public void setLogic(RemoteClient logic) {
    this.logic = logic;
  }

  /**
   * Sets status to disconnected.
   */
  public void setStatusDisconnected() {
    Platform.runLater(() -> {
      timeline.pause();
      connection.setTextFill(Color.GRAY);
      connection.setText("Disconnected");
    });
  }
}
