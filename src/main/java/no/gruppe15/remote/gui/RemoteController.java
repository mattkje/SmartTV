package no.gruppe15.remote.gui;

import javafx.animation.KeyFrame;
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
import no.gruppe15.command.*;
import no.gruppe15.message.Message;
import no.gruppe15.message.MessageSerializer;
import no.gruppe15.tv.ClientHandler;
import no.gruppe15.tv.TvLogic;
import no.gruppe15.tv.TvServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import static no.gruppe15.tv.TvServer.PORT_NUMBER;

/**
 * Controller class for the RemoteApp class.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 23.10.2023
 */
public class RemoteController implements Initializable {

  private PrintWriter printWriter;

  private BufferedReader socketReader;

  @FXML
  private TextField textField;
  @FXML
  private Label connection;
  private Timeline timer;

  /**
   * This method sets the printWriter and sockerReader
   *
   * @param printWriter
   * @param socketReader
   */
  public void setPrintWriter(PrintWriter printWriter, BufferedReader socketReader) {
    this.printWriter = printWriter;
    this.socketReader = socketReader;
  }


  /**
   * This method handles the number buttons on the remote. It reads the number text,
   * and parses it in the textfield before sending it in 1 second intervals.
   *
   * @param event
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
      sendCommandToServer(new SetChannelCommand(text));
      textField.setText("");
    }));
    timer.playFromStart();
  }

  /**
   * This method should provide feedback from the remote app
   * in the terminal.
   *
   * @param command Current command.
   */
  private void sendCommandToServer3(String command) {
    if (printWriter != null) {
      printWriter.println(command);
      String serverResponse = null;
      try {
        serverResponse = socketReader.readLine();
      } catch (IOException e) {
        printWriter = null;
        connection.setText("Connection lost");
        connection.setTextFill(Color.YELLOW);
      }
      System.out.println("Server Response: " + serverResponse);
    } else {
      System.err.println("PrintWriter is not set.");
    }
  }

  private Message sendCommandToServer(Command command) {
    String serializedCommand = MessageSerializer.toString(command);
    System.out.println("Sending command: " + serializedCommand);
    Message serverResponse;
    try {
      printWriter.println(serializedCommand);
      String rawServerResponse = socketReader.readLine();
      System.out.println("Server Response: " + rawServerResponse);
      serverResponse = MessageSerializer.fromString(rawServerResponse);
      if (serverResponse == null) {
        throw new IllegalStateException("Could not deserialize message: " + rawServerResponse);
      }
    } catch (IOException e) {
      System.err.println("Server error response: " + e.getMessage());
      printWriter = null;
      connection.setText("Connection lost");
      connection.setTextFill(Color.YELLOW);
      serverResponse = null;
    }
    return serverResponse;
  }

  /**
   * This method handles the turn on command.
   */
  public void turnOn() {
    sendCommandToServer(new TurnOnCommand());
  }

  /**
   * This method handles the turn-off command.
   */
  public void turnOff() {
    sendCommandToServer(new TurnOffCommand());
  }

  /**
   * This method handles the channel down command.
   */
  public void channelDown() {
    sendCommandToServer(new ChannelDownCommand());
  }

  /**
   * This method handles the channel up command.
   */
  public void channelUp() {
    sendCommandToServer(new ChannelUpCommand());
  }

  /**
   * This method handles the exit command.
   */
  public void exit() {
    sendCommandToServer(new IgnoreCommand());
  }

  /**
   * This method handles the number of channels command.
   */
  public void getNumberOfChannels() {
    sendCommandToServer(new ChannelCountCommand());
  }

  /**
   * The initialize method creates a timeline that checks if the remote has
   * a connection to the smart tv, and should provide feedback if connection is lost.
   *
   * @param url
   * @param resourceBundle
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Timeline timeline = new Timeline(new KeyFrame(
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
   * This method should create a new socket to reconnect the remote
   *
   * //TODO Shorten this (split or get from RemoteControl class?)
   */
  public void reConnect() {

    Platform.runLater(() -> {
      connection.setTextFill(Color.YELLOW);
      connection.setText("Connecting...");
    });
    new Thread(() -> {
      try {
        Socket socket = new Socket("localhost", PORT_NUMBER);
        ClientHandler remoteClient = new ClientHandler(socket, new TvServer(new TvLogic(100)));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        socketReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        remoteClient.run();
        System.out.println("Connecting success");
      } catch (IOException e) {
        System.err.println("Could not establish connection to the server: " + e.getMessage());
      }

    }).start();
  }

}
