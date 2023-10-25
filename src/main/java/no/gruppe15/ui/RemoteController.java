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

    // Append the button's text to the TextField
    textField.appendText(buttonText);

    if (timer != null) {
      // If a timer exists (i.e., a button was pressed within the last second), stop and reset it.
      timer.stop();
    }

    // Create a new timer with a 1-second delay
    timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
      // Send the command to the server after the delay
      String text = textField.getText();
      sendCommandToServer("c"+text);
      textField.setText("");
    }));
    timer.playFromStart(); // Restart the timer
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

  public void getNumberOfChannels() {
    sendCommandToServer("n");
  }
}
