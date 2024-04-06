package it.unibo.io;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

        // Bottone che porta il giocatore al menu principale
        Button backButton = new Button("Menu principale");
        backButton.setOnAction(e -> {
            // Chiude la finestra attuale
            primaryStage.close(); 
            MenuGui menu = new MenuGui();
            Stage menuStage = new Stage();
            try {
                // Apre la finestra del menu
                menu.start(menuStage); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Layout per il bottone del menu
        HBox menuButtonLayout = new HBox(backButton);
        menuButtonLayout.setAlignment(Pos.TOP_RIGHT);//alto a destra

        // Utilizziamo un BorderPane per posizionare il layout dei livelli al centro e il bottone "Menu" nell'angolo in alto a destra
        BorderPane root = new BorderPane();
        root.setCenter(levelsLayout);
        root.setTop(menuButtonLayout);

        // Setta la scena dei livelli
        Scene levelsScene = new Scene(root, 800, 600);
        root.setStyle("-fx-background-color: black;");
        primaryStage.setScene(levelsScene);
        primaryStage.show();
    }
}
