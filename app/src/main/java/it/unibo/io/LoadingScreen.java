package it.unibo.io;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadingScreen {
    private Stage stage;
    private VBox loadingLayout;

    public LoadingScreen(Stage stage) {
        this.stage = stage;
        loadingLayout = new VBox(20);
        loadingLayout.setAlignment(Pos.CENTER);
        // loadingLayout.setStyle("-fx-background-color: black;");

        ProgressIndicator progressIndicator = new ProgressIndicator();
        Label loadingLabel = new Label("Loading...");

        loadingLayout.getChildren().addAll(progressIndicator, loadingLabel);
    }

    public void show() {
        Scene scene = new Scene(loadingLayout, 800, 600);
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
        });
    }
    
    public void hide() {
        Platform.runLater(stage::hide);
    }
}
