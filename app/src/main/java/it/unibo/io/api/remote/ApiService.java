package it.unibo.io.api.remote;
import it.unibo.io.model.TriviaResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api.php?amount=5")
    Call<TriviaResponse> getTriviaCall();

}
