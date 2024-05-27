package it.unibo.io.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import it.unibo.io.api.remote.ApiService;
import it.unibo.io.api.remote.RetrofitClient;
import it.unibo.io.model.Result;
import it.unibo.io.model.Trivia;
import it.unibo.io.model.TriviaResponse;
import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.apache.commons.text.StringEscapeUtils;

public class TriviaService {
    private ApiService apiService;

    public TriviaService() {
        this.apiService = RetrofitClient.getClient("https://opentdb.com/").create(ApiService.class);
    }

    public void loadTriviaQuestions(Consumer<List<Trivia>> onTriviaLoaded, Consumer<Throwable> onError) {
        Call<TriviaResponse> call = apiService.getTriviaCall();
        call.enqueue(new Callback<TriviaResponse>() {

            @Override
            public void onResponse(Call<TriviaResponse> call, Response<TriviaResponse> response) {
                if (response.isSuccessful()) {
                    List<Trivia> trivias = response.body().getResults().stream().map(result -> {
                        decodeResult(result);
                        Trivia trivia = new Trivia();
                        trivia.setQuestion(result.getQuestion());
                        trivia.setCorrect_answer(result.getCorrect_answer());
                        trivia.setIncorrect_answers(result.getIncorrect_answers());
                        trivia.updateAllAnswers();
                        return trivia;
                    }).collect(Collectors.toList());
                    Platform.runLater(() -> {
                        onTriviaLoaded.accept(trivias);
                    });

                }
            }

            @Override
            public void onFailure(Call<TriviaResponse> call, Throwable t) {
                Platform.runLater(() -> {
                    onError.accept(t);
                });
            }

        });
    }
    
    private void decodeResult(Result result) {
        result.setType(StringEscapeUtils.unescapeHtml4(result.getType()));
        result.setDifficulty(StringEscapeUtils.unescapeHtml4(result.getDifficulty()));
        result.setCategory(StringEscapeUtils.unescapeHtml4(result.getCategory()));
        result.setQuestion(StringEscapeUtils.unescapeHtml4(result.getQuestion()));
        result.setCorrect_answer(StringEscapeUtils.unescapeHtml4(result.getCorrect_answer()));
        result.setIncorrect_answers(result.getIncorrect_answers().stream()
                .map(StringEscapeUtils::unescapeHtml4)
                .collect(Collectors.toList()));
    }

}
