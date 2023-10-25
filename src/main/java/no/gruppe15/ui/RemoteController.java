package no.gruppe15.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller class for the RemoteApp class.
 */
public class RemoteController {

  @FXML
  private TextField textField;

  @FXML
  private void onNumberButtonClicked(ActionEvent event) {
    Button button = (Button) event.getSource();
    String buttonText = button.getText();

    // Append the button's text to the TextField or Label
    textField.appendText(buttonText);
  }

  public void turnOn() {
    //TODO Implement this feature
  }

  public void turnOff() {
    //TODO Implement this feature
  }

  public void getNumberOfChannels() {
    //TODO Implement this feature
  }
}
