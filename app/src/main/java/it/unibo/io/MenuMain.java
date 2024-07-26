package it.unibo.io;

import it.unibo.io.start.QuizGui;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MAIN-MENU");

        // Bottone per iniziare a giocare
        Button startButton = new Button("Gioca a SignorCervo");
        startButton.setOnAction(e -> {
            primaryStage.close();
            menuStart();
        });

        Button quizButton = new Button("Gioca a QuizTrivia");
        quizButton.setOnAction(e -> {
            primaryStage.close();
            startQuiz();
        });

        // Bottone per uscire dal gioco
        Button exitButton = new Button("Esci dal Gioco");
        exitButton.setOnAction(e -> {
            System.exit(0);
        });

        // Layout del menu con i bottoni
        VBox menuLayout = new VBox(20);
        menuLayout.getChildren().addAll(startButton, quizButton, exitButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: black;");
        // Settiamo la scena del menu
        Scene menuScene = new Scene(menuLayout, 800, 600);
        primaryStage.setScene(menuScene);

        primaryStage.show();


    }

    private void startQuiz() {
        QuizGui quizGui = new QuizGui();
        Stage quizStage = new Stage();
        try {
            quizGui.start(quizStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    private void menuStart() {
      MenuSignorCervo menuSG = new MenuSignorCervo();
      Stage levelStage = new Stage();
      try {
          menuSG.start(levelStage);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

}
