package no.gruppe15.ui;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class represents the controller for the SmartTV user interface.
 * Use setChannelMedia(String channelNumber) to set graphical channel
 * <p>
 * TODO: Connect this to the TvLogic class
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 23.10.2023
 */
public class SmartTVController implements Initializable {
  @FXML
  private MediaView mediaView;
  @FXML
  private Label status;
  @FXML
  private Label channelNumber;
  @FXML
  private Label signal;
  @FXML
  private HBox channelBox;

  private MediaPlayer mediaPlayer;


  /**
   * This method sets the corresponding media to the current channel.
   * if the channel is empty or set to "0" the MediaView will display a static image
   * indicating the tv either is off, or invalid channel.
   *
   * @param channel the current channel
   */
  public void setChannelMedia(String channel) {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    if (channel.isEmpty()) {
      return;
    }
    if (channel.equals("0")) {
        Platform.runLater(() -> {
            mediaView.setVisible(false);
            status.setText("OFF");
            channelBox.setVisible(false);
        });

      return;
    }
    mediaView.setVisible(true);
    String videoPath = getVideoPath(channel);
    Media media = createMedia(videoPath, channel);
    mediaPlayer = createMediaPlayer(media);


    setupMediaView();
    setupMediaPlayer();
  }

  /**
   * This method finds the channel media file given the channel number.
   *
   * @param channel Current channel number
   * @return the channel media file path as a string.
   */
  private String getVideoPath(String channel) {
    Platform.runLater(() -> {
      channelNumber.setText(channel);
      status.setText("ON");
      channelBox.setVisible(true);
    });

    return "/no/gruppe15/media/channel" + channel + ".mp4";
  }

  /**
   * This method should add a channel splash screen for the current channel.
   *
   * @param videoPath The videoPath corresponding to given channel.
   * @param channel   Current channel number.
   * @return Media corresponding to the given channel.
   */
  private Media createMedia(String videoPath, String channel) {
    try {
      channelDisplay();
      Platform.runLater(() -> signal.setText(" Channel " + channel + " "));

      return new Media(Objects.requireNonNull(getClass()
          .getResource(videoPath)).toExternalForm());
    } catch (NullPointerException e) {
      Platform.runLater(() -> signal.setText(" NO SIGNAL "));
      return new Media(Objects.requireNonNull(getClass()
          .getResource("/no/gruppe15/media/static.mp4")).toExternalForm());
    }
  }

  /**
   * This method returns a new mediaPlayer with a given media.
   *
   * @param media Current media.
   * @return MediaPlayer with a given media.
   */
  private MediaPlayer createMediaPlayer(Media media) {
    return new MediaPlayer(media);
  }

  /**
   * Sets the mediaPlayer for the mediaView.
   */
  private void setupMediaView() {
    mediaView.setMediaPlayer(mediaPlayer);
  }

  /**
   * This method loops the media, and it also gives
   * the MediaPlayer time to load.
   */
  private void setupMediaPlayer() {
    mediaPlayer.setOnEndOfMedia(() -> {
      mediaPlayer.seek(Duration.ZERO);
      mediaPlayer.play();
    });
    //mediaPlayer.setMute(true);

    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
    pause.setOnFinished(event -> {
      mediaPlayer.play();
    });
    pause.play();
  }

  /**
   * This method is responsible for displaying the selected channel.
   * for 2 seconds on the tv display
   */
  public void channelDisplay() {
    Timeline timeline = new Timeline(new KeyFrame(
        Duration.seconds(2),
        event -> {
          signal.setText("");
        }));
    timeline.play();
  }

  /**
   * Initializes the controller when the associated FXML file is loaded.
   *
   * @param url            The location used to resolve relative paths for the root object or null if the location is not known.
   * @param resourceBundle The resource bundle to be used, or null if the root object was not located by a named location.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    setChannelMedia("0");
    status.setText("OFF");
    channelBox.setVisible(false);
  }


}
