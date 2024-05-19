package it.unibo.io.model;

import java.util.List;
/**
 * Classe per la risposta della api
 */
public class TriviaResponse {

    int response_code;
    List<Result> results;

    public int getResponse_code() {
        return this.response_code;
    }

    public List<Result> getResults() {
        return this.results;
    }
}