package no.gruppe15.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

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
        throw new RuntimeException(e);
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
    sendCommandToServer("m");
  }

  public void getNumberOfChannels() {
    sendCommandToServer("n");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if (printWriter != null){
      connection.setText("Connected");
      connection.setTextFill(Color.LIME);
    } else {
      connection.setText("Not connected");
      connection.setTextFill(Color.RED);
    }
  }
}
