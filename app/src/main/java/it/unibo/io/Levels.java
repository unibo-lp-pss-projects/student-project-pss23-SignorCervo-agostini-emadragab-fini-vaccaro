package it.unibo.io;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Levels extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Livelli del gioco");

    // Layout per i livelli
    GridPane levelsLayout = new GridPane();
    levelsLayout.setAlignment(Pos.CENTER);
    levelsLayout.setHgap(10);
    levelsLayout.setVgap(10);

    // Aggiungi i bottoni dei livelli
    for (int i = 0; i < 6; i++) {
      Button levelButton = new Button("Livello " + (i + 1));
      levelsLayout.add(levelButton, i % 3, i < 3 ? 0 : 1);
    }

    // Setta la scena dei livelli
    Scene levelsScene = new Scene(levelsLayout, 800, 600);
    levelsLayout.setStyle("-fx-background-color: black;");
    primaryStage.setScene(levelsScene);
    primaryStage.show();
  }
}