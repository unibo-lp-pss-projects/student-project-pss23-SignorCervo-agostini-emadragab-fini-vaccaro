package it.unibo.io.model;

import java.util.List;

/**
 * Classe di mezzo fra remoto e locale (DTO)
 */
public class Result {

    String type;
    String difficulty;
    String category;
    String question;
    String correct_answer;
    List<String> incorrect_answers;

    public String getType() {
        return this.type;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public String getCategory() {
        return this.category;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getCorrect_answer() {
        return this.correct_answer;
    }

    public List<String> getIncorrect_answers() {
        return this.incorrect_answers;
    }

}
