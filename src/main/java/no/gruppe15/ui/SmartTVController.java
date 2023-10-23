package no.gruppe15.ui;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class SmartTVController {
    @FXML
    private MediaView mediaView;

    public void initialize() {
        setChannelMedia("1");
    }


    public void setChannelMedia(String channel) {
        if (channel.isEmpty()) {
            channel = "1";
        }

        String videoPath = "/media/channel" + channel + ".mp4";
        Media media = new Media(getClass().getResource(videoPath).toExternalForm());

        MediaPlayer mediaPlayer = new MediaPlayer(media);


        if (mediaPlayer.getMedia() == null) {
            String staticVideoPath = "/media/static.mp4";
            Media staticMedia = new Media(getClass().getResource(staticVideoPath).toExternalForm());
            mediaPlayer = new MediaPlayer(staticMedia);
        }


        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.play();
    }

}
