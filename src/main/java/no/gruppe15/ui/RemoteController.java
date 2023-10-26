package no.gruppe15.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import no.gruppe15.RemoteControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import static no.gruppe15.TvServer.PORT_NUMBER;

/**
 * Controller class for the RemoteApp class.
 */
public class RemoteController implements Initializable {

  private PrintWriter printWriter;

  private BufferedReader socketReader;

  @FXML
  private TextField textField;

  @FXML
  private Label connection;
  private Timeline timer;

  public void setPrintWriter(PrintWriter printWriter, BufferedReader socketReader) {
    this.printWriter = printWriter;
    this.socketReader = socketReader;
  }


  @FXML
  private void onNumberButtonClicked(ActionEvent event) {
    Button button = (Button) event.getSource();
    String buttonText = button.getText();


    textField.appendText(buttonText);

    if (timer != null) {

      timer.stop();
    }

    // Create a new timer with a 1-second delay
    timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

      String text = textField.getText();
      sendCommandToServer("c"+text);
      textField.setText("");
    }));
    timer.playFromStart();
  }

  private void sendCommandToServer(String command) {
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
      System.out.println(command);
      System.out.println("Server Response: " + serverResponse);
      System.out.print("Enter a message: ");
    } else {
      System.err.println("PrintWriter is not set.");
    }
  }

  public void turnOn() {
    sendCommandToServer("1");
  }

  public void turnOff() {
    sendCommandToServer("0");
  }

  public void channelDown() {
    sendCommandToServer("c2");
  }

  public void channelUp() {
    sendCommandToServer("c4");
  }
  public void mute() {
    sendCommandToServer("exit");
  }

  public void getNumberOfChannels() {
    sendCommandToServer("n");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Create a Timeline that updates the connection status every N milliseconds
    Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(1000), // Update every 1 second, change this as needed
            event -> updateConnectionStatus()));
    timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
    timeline.play();
  }

  private void updateConnectionStatus() {
    if (printWriter != null) {
      connection.setText("Connected");
      connection.setTextFill(Color.LIME);
    } else {
      connection.setText("Not connected");
      connection.setTextFill(Color.RED);
    }

  }

  public void reConnect() {
    Platform.runLater(() -> {
      connection.setTextFill(Color.YELLOW);
      connection.setText("Connecting...");
    });
    new Thread(() -> {
      try {
        Socket socket = new Socket("localhost", PORT_NUMBER);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        socketReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        Scanner userInputScanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
          System.out.print("Enter a message: ");
          String input = userInputScanner.nextLine();

          printWriter.println(input);

          if (input.equals("exit")) {
            exit = true;
          }

          // TODO: ADD exception handling if there is a wrong command
          String serverResponse = socketReader.readLine();
          System.out.println("Server Response: " + serverResponse);
        }

        printWriter.close();
        socketReader.close();
        socket.close();

      } catch (IOException e) {
        System.err.println("Could not establish connection to the server: " + e.getMessage());
      }

    }).start();
  }

}
