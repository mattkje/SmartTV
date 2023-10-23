package no.gruppe15.ui;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class SmartTVController {
    @FXML
    private MediaView mediaView;

    public void initialize() {
        // Create a Media object with the path to the video file
        String videoPath = "/media/channel1.mp4";
        Media media = new Media(videoPath);

        // Create a MediaPlayer and set the media to it
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Set the MediaPlayer to the MediaView
        mediaView.setMediaPlayer(mediaPlayer);

        // Play the video
        mediaPlayer.play();
    }
}
