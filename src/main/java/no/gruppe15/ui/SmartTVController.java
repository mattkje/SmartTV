package no.gruppe15.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import no.gruppe15.TvLogic;
import no.gruppe15.TvServer;

import java.net.URL;
import java.util.ResourceBundle;

public class SmartTVController implements Initializable {
    @FXML
    private MediaView mediaView;
    @FXML
    private Label status;
    @FXML
    private Label channelNumber;
    @FXML
    private HBox channelBox;


    /**
     * This method sets the corresponding media to the current channel.
     * if the channel is empty or set to "static" the MediaView will display a static image.
     * @param channel the current channel
     */
    public void setChannelMedia(String channel) {
        if (channel.isEmpty()) {
            channel = "1";
        }
        String videoPath = "/no/gruppe15/media/static.mp4";
        if (!channel.equals("0")){
            videoPath = "/no/gruppe15/media/channel" + channel + ".mp4";
            channelNumber.setText(channel);
            status.setText("ON");
            channelBox.setVisible(true);
        }

        Media media = new Media(getClass().getResource(videoPath).toExternalForm());

        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });

        mediaPlayer.play();

    }

    /**
     * Initializable...
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setChannelMedia("0");
        status.setText("OFF");
        channelBox.setVisible(false);

        turnOnTv();
    }

    public void turnOnTv(){
        setChannelMedia("0");
    }
}
