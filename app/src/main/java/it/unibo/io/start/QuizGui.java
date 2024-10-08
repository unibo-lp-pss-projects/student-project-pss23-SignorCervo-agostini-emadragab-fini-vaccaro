package it.unibo.io.start;

import java.util.ArrayList;
import java.util.Collections;

import java.net.URL;
import java.util.List;

import it.unibo.io.api.remote.ApiService;
import it.unibo.io.api.remote.RetrofitClient;
import it.unibo.io.controller.TriviaQuizController;
import it.unibo.io.model.Result;
import it.unibo.io.model.Trivia;
import it.unibo.io.model.TriviaResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizGui extends Application {

    private List<Trivia> trivias;
    
    public QuizGui(List<Trivia> trivias) {
        this.trivias = trivias;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL url = getClass().getResource("/layouts/TriviaQuiz.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        TriviaQuizController controller = loader.getController();
        controller.setQuestions(trivias);

        // Impostazione della scena e visualizzazione dello stage
        Scene scene = new Scene(root, 600, 450);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trivia Quiz");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    @Override
    public void stop() throws Exception {

        System.out.println("sto uscendo dal thread di javafx");
        // Termino il thread di JavaFX
        Platform.exit();
        System.exit(0);
    }

}
