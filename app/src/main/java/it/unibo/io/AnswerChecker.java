package it.unibo.io;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class AnswerChecker {
    private Label responseLabel;

    public AnswerChecker(Label responseLabel) {
        this.responseLabel = responseLabel;
    }

    public void checkAnswer(String selectedAnswer, String correctAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            responseLabel.setText("CORRECT!");
            responseLabel.setTextFill(Color.GREEN);
        } else {
            responseLabel.setText("WRONG");
            responseLabel.setTextFill(Color.RED);
        }

    }
}
