package it.unibo.io.model;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
/**
 * Classe da usare in locale
 */
public class Trivia {

    String type;
    String difficulty;
    String category;
    String question;
    List<String> all_answers;
    String correct_answer;
    List<String> incorrect_answers;

    public String getType() {
        return type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    
    public List<String> getAll_answers() {
        return all_answers;
    }

    public String getAnswer(int i) {
        return all_answers.get(i);
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
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
        // updateAllAnswers();
    }

    public void setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
        // updateAllAnswers();
    }

    @Override
    public String toString() {
        decode();
        return "Trivia [type=" + type + ", difficulty=" + difficulty + ", category=" + category + ", question="
                + question + ", all_answers=" + all_answers + "]";
    }

    private void decode() {
        type = StringEscapeUtils.unescapeHtml4(type);
        difficulty = StringEscapeUtils.unescapeHtml4(difficulty);
        category = StringEscapeUtils.unescapeHtml4(category);
        question = StringEscapeUtils.unescapeHtml4(question);
        correct_answer = StringEscapeUtils.unescapeHtml4(correct_answer);
        incorrect_answers.forEach(item -> {
            StringEscapeUtils.unescapeHtml4(item);
        });
    }

    public void updateAllAnswers() {
        decode();
        this.all_answers = new ArrayList<>(incorrect_answers); // Inizializza con le risposte errate
        all_answers.add(correct_answer); // Aggiunge la risposta corretta
        
        // Collections.shuffle(all_answers); // Mescola le risposte per randomizzarle
    }

}
