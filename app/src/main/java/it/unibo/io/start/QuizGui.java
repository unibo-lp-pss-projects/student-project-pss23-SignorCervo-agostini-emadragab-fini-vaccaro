package it.unibo.io.start;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import it.unibo.io.api.remote.ApiService;
import it.unibo.io.api.remote.RetrofitClient;
import it.unibo.io.model.Result;
import it.unibo.io.model.Trivia;
import it.unibo.io.model.TriviaResponse;
import javafx.application.Application;
import javafx.application.Platform;
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

    Trivia trivia = new Trivia();
    List<Trivia> trivias = new ArrayList<>();

    private Label questionLabel;
    private List<RadioButton> optionButtons = new ArrayList<>();
    private ToggleGroup group = new ToggleGroup();
    
    private List<Result> results;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        getTrivia(root);
        Scene scene = new Scene(root, 700, 400);

        // Etichetta per il quesito
        questionLabel = new Label("Caricamento del quesito...");
        root.getChildren().add(questionLabel);

        primaryStage.setOnCloseRequest(event -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Impostazione della scena e visualizzazione dello stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trivia Quiz");
        primaryStage.show();

    }

    private void getTrivia(VBox root) {
        call.enqueue(new Callback<TriviaResponse>() {

            @Override
            public void onResponse(Call<TriviaResponse> call, Response<TriviaResponse> response) {
                if (response.isSuccessful()) {
                    // Gestione della risposta
                    results = response.body().getResults();
                    System.out.println("size di results = " + "  " + results.size());

                    map(results);

                    Platform.runLater(() -> {
                        questionLabel.setText(trivia.getQuestion());
                        List<String> allAnswers = new ArrayList<>(trivia.getAll_answers());
                        
                        Collections.shuffle(allAnswers); // Mescola le risposte

                        // Gruppo per i RadioButton
                        group = new ToggleGroup();
                        
                        // Creazione dei RadioButton per le opzioni
                        for (int i = 0; i < (trivia.getAll_answers().size()); i++) {
                            RadioButton rb = new RadioButton();
                            rb.setToggleGroup(group);
                            rb.setText(trivia.getAnswer(i));
                            optionButtons.add(rb);
                            System.out.println("creato");
                            root.getChildren().add(rb);
                        }
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

    private void map(List<Result> results) {
        results.forEach(element -> {
            trivia.setCorrect_answer(element.getCorrect_answer());
            trivia.setIncorrect_answers(element.getIncorrect_answers());
            // size = trivia.getAll_answers().size();
            
            trivia.setQuestion(element.getQuestion());
            trivia.setType(element.getType());
            trivia.setCategory(element.getCategory());
            trivia.setDifficulty(element.getDifficulty());
            
            trivia.updateAllAnswers();
            trivias.add(trivia);
            // System.out.println("size delle risposte vale =" + size);
        });
    }

    @Override
    public void stop() throws Exception {
        
        if (call != null) {
            System.out.println("sto eliminando la call");
            call.cancel();
        }

        

        System.out.println("sto uscendo dal thread di javafx");
        // Termino il thread di JavaFX
        Platform.exit();
        System.exit(0);
    }

}
