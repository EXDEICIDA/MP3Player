module datavisualizers.mediaplayerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media; // Add this line

    opens datavisualizers.mediaplayerapp to javafx.fxml;
    exports datavisualizers.mediaplayerapp;
}
