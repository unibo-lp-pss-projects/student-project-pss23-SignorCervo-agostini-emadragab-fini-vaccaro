package it.unibo.io;

import it.unibo.io.start.QuizGui;
import it.unibo.io.service.TriviaService;
import it.unibo.io.model.Trivia;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class QuizManager {

    private Stage stage;

    public QuizManager(Stage stage) {
        this.stage = stage;
    }

    public void startQuiz() {
        LoadingScreen loadingScreen = new LoadingScreen(stage);
        loadingScreen.show();

        TriviaService triviaService = new TriviaService();
        triviaService.loadTriviaQuestions(
            trivias -> {
                loadingScreen.hide();
                Platform.runLater(() -> showQuiz(trivias));
            },
            throwable -> {
                loadingScreen.hide();
                Platform.runLater(this::showErrorScreen);
            }
        );
    }

    private void showQuiz(List<Trivia> trivias) {
        QuizGui quizGui = new QuizGui(trivias);
        Stage quizStage = new Stage();
        try {
            quizGui.start(quizStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorScreen() {
        VBox errorLayout = new VBox(20);
        errorLayout.setAlignment(Pos.CENTER);
        errorLayout.setStyle("-fx-background-color: black;");

        Label errorLabel = new Label("Failed to load trivia questions. Please try again.");
        errorLabel.setTextFill(Color.RED);
        Button retryButton = new Button("Retry");
        retryButton.setOnAction(e -> startQuiz());

        errorLayout.getChildren().addAll(errorLabel, retryButton);

        Scene errorScene = new Scene(errorLayout, 800, 600);
        stage.setScene(errorScene);
        stage.show();
    }
}
