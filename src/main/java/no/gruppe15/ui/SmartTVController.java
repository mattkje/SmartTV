package no.gruppe15.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class SmartTVController implements Initializable {
    @FXML
    private MediaView mediaView;


    /**
     * This method sets the corresponding media to the current channel.
     * if the channel is empty or set to "static" the MediaView will display a static image.
     * @param channel the current channel
     */
    public void setChannelMedia(String channel) {
        if (channel.isEmpty()) {
            channel = "1";
        }

        String videoPath = "/media/channel" + channel + ".mp4";
        Media media = new Media(getClass().getResource(videoPath).toExternalForm());

        MediaPlayer mediaPlayer = new MediaPlayer(media);


        if (mediaPlayer.getMedia() == null || channel.equals("static")) {
            String staticVideoPath = "/media/static.mp4";
            Media staticMedia = new Media(getClass().getResource(staticVideoPath).toExternalForm());
            mediaPlayer = new MediaPlayer(staticMedia);
        }


        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.play();
    }

    /**
     * Initializable...
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setChannelMedia("1");
    }
}
