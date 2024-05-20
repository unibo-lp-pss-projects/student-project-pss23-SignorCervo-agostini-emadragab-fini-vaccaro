package it.unibo.io.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.util.List;

import it.unibo.io.model.Result;
import it.unibo.io.model.Trivia;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaQuizController {

    @FXML
    private Label questionLabel;

    @FXML
    private Label questionNumber;

    @FXML
    private RadioButton option1, option2, option3, option4;

    @FXML
    private ToggleGroup answerGroup;

    private List<Trivia> trivias = new ArrayList<>();
    private int currentIndex = 0;

    public void initialize() {
        // Inizializzazione del ToggleGroup se necessario
        if (answerGroup == null) {
            answerGroup = new ToggleGroup();
            option1.setToggleGroup(answerGroup);
            option2.setToggleGroup(answerGroup);
            option3.setToggleGroup(answerGroup);
            option4.setToggleGroup(answerGroup);
        }
        // this.questionNumber.setText(String.valueOf(currentIndex +1));
    }

    public void setQuestions(List<Result> results) {
        for (Result result : results) {
            Trivia trivia = new Trivia();
            trivia.setQuestion(result.getQuestion());
            trivia.setCorrect_answer(result.getCorrect_answer());
            trivia.setIncorrect_answers(result.getIncorrect_answers());
            trivia.updateAllAnswers();
            trivias.add(trivia);
        }
        displayNextQuestion();
    }

    public void displayNextQuestion() {
        if (currentIndex < trivias.size()) {
            Trivia currentTrivia = trivias.get(currentIndex);
            questionLabel.setText(currentTrivia.getQuestion());
            List<String> options = currentTrivia.getAll_answers();
            Collections.shuffle(options);

            option1.setText(options.size() > 0 ? options.get(0) : "");
            option2.setText(options.size() > 1 ? options.get(1) : "");
            option3.setText(options.size() > 2 ? options.get(2) : "");
            option4.setText(options.size() > 3 ? options.get(3) : "");

            answerGroup.getToggles().forEach(toggle -> {
                ((RadioButton) toggle).setSelected(false);
            });
            questionNumber.setText(String.valueOf(currentIndex +1));
            currentIndex++;
        }
    }

    @FXML
    private void handleSubmit() {
        RadioButton selected = (RadioButton) answerGroup.getSelectedToggle();
        if (selected != null) {
            System.out.println("Selected Answer: " + selected.getText());
        }
        String answer = selected.getText();
        

    }

    @FXML
    private void handleNext() {
        displayNextQuestion();
    }
}
