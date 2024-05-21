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

    ApiService apiService = RetrofitClient.getClient("https://opentdb.com/").create(ApiService.class);
    Call<TriviaResponse> call = apiService.getTriviaCall();

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL url = getClass().getResource("/layouts/TriviaQuiz.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        // Impostazione della scena e visualizzazione dello stage
        Scene scene = new Scene(root, 400, 350);
        
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
        TriviaQuizController controller = loader.getController();

        getTrivia(controller);

    }

    private void getTrivia(TriviaQuizController controller) {
        call.enqueue(new Callback<TriviaResponse>() {

            @Override
            public void onResponse(Call<TriviaResponse> call, Response<TriviaResponse> response) {
                if (response.isSuccessful()) {
                    // Gestione della risposta
                    List<Result> results = response.body().getResults();
                    System.out.println("size di results = " + "  " + results.size());

                    Platform.runLater(() -> {
                        controller.setQuestions(results);
                        call.cancel();
                    });

                }
            }

            @Override
            public void onFailure(Call<TriviaResponse> call, Throwable t) {
                // Gestione dell'errore
                t.printStackTrace();
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
