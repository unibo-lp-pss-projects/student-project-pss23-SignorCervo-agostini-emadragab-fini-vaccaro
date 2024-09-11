package it.unibo.io.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.io.AnswerChecker;
import it.unibo.io.model.Trivia;
import it.unibo.io.service.TriviaService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class TriviaQuizController {

    @FXML
    private Label questionLabel;

    @FXML
    private Label questionNumber;

    @FXML
    private RadioButton option1, option2, option3, option4;

    @FXML
    private ToggleGroup answerGroup = new ToggleGroup();

    @FXML
    private Label responseLabel ;

    private List<Trivia> trivias = new ArrayList<>();
    private int currentIndex = 0;
    private TriviaService triviaService;
    private AnswerChecker answerChecker;

    public void initialize() {
        answerChecker = new AnswerChecker(responseLabel);
        triviaService = new TriviaService();
        loadTriviaQuestions();
    }

    public void loadTriviaQuestions() {
        triviaService.loadTriviaQuestions(this::setQuestions, Throwable::printStackTrace);

    }

    public void setQuestions(List<Trivia> trivias) {
        this.trivias = trivias;
        displayNextQuestion();
    }

    public void displayNextQuestion() {
        responseLabel.setText("");
        if (currentIndex < trivias.size()) {
            Trivia currentTrivia = trivias.get(currentIndex);
            questionLabel.setText(currentTrivia.getQuestion());
            List<String> options = currentTrivia.getAll_answers();
            Collections.shuffle(options);
            // Pulizia dei radio button esistenti
            answerGroup.getToggles().clear();

            // Impostazione del testo per i radio button esistenti o loro nascondimento se non necessari
            RadioButton[] buttons = { option1, option2, option3, option4 };
            for (int i = 0; i < buttons.length; i++) {
                if (i < options.size()) {
                    buttons[i].setText(options.get(i));
                    buttons[i].setVisible(true);
                    buttons[i].setToggleGroup(answerGroup);
                } else {
                    buttons[i].setVisible(false);
                    buttons[i].setToggleGroup(null);
                }
            }
            questionNumber.setText(String.valueOf(currentIndex + 1));
            currentIndex++;
        }
    }

    @FXML
    private void handleSubmit() {
        RadioButton selected = (RadioButton) answerGroup.getSelectedToggle();
        if (selected != null) {
            String userAnswer = selected.getText();
            String triviaAnswer = trivias.get(currentIndex - 1).getCorrect_answer();
            
            answerChecker.checkAnswer(userAnswer, triviaAnswer);
        } else {
            responseLabel.setText("CHOOSE AN OPTION");
            responseLabel.setTextFill(Color.RED);
        }

    }

    @FXML
    private void handleNext() {
        displayNextQuestion();
    }
}
