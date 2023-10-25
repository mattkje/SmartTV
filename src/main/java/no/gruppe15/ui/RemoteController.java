package no.gruppe15.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.PrintWriter;

/**
 * Controller class for the RemoteApp class.
 */
public class RemoteController {

  private PrintWriter printWriter;

  @FXML
  private TextField textField;
  private Timeline timer;

  public void setPrintWriter(PrintWriter printWriter) {
    this.printWriter = printWriter;
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

  public void getNumberOfChannels() {
    sendCommandToServer("n");
  }
}
