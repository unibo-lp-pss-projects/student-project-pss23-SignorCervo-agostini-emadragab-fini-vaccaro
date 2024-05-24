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

    public void setType(String type) {
        this.type = type;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public void setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

}
