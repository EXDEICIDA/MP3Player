package datavisualizers.mediaplayerapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private Pane pane;
    @FXML private Label songLabel;
    @FXML private Button playButton, pauseButton, resetButton, previousButton, nextButton;
    @FXML private ComboBox<String> speedBox;
    @FXML private Slider volumeSlider;
    @FXML private ProgressBar songProgressBar;

    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private int songNumber;
    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs = new ArrayList<>();
        directory = new File("music");
        files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                songs.add(file);
            }
        }

        speedBox.getItems().addAll("0.25", "0.5", "1", "1.25", "1.5", "2");
        speedBox.getSelectionModel().select("1"); // Default speed is 100%

        if (!songs.isEmpty()) {
            loadMedia(songs.get(songNumber)); // Load the first song
        }
    }

    private void loadMedia(File song) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        media = new Media(song.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(song.getName());

        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));
        mediaPlayer.currentTimeProperty().addListener(observable -> {
            songProgressBar.setProgress(mediaPlayer.getCurrentTime().toMillis() / media.getDuration().toMillis());
        });

        mediaPlayer.setOnReady(() -> mediaPlayer.play());
    }

    @FXML
    private void playMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    private void pauseMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void resetMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    @FXML
    private void previousMedia() {
        if (songNumber > 0) {
            songNumber--;
            loadMedia(songs.get(songNumber));
        }
    }

    @FXML
    private void nextMedia() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
            loadMedia(songs.get(songNumber));
        }
    }

    @FXML
    private void changeSpeed(ActionEvent event) {
        if (mediaPlayer != null) {
            String value = speedBox.getSelectionModel().getSelectedItem();
            mediaPlayer.setRate(Double.parseDouble(value));
        }
    }
}
