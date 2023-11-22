package it.unibo.io;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuGui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MENU");

        // Bottone per iniziare a giocare
        Button startButton = new Button("Inizia a Giocare");
        startButton.setOnAction(e -> {
            primaryStage.close();
            startGame();
        });

        // Bottone per uscire dal gioco
        Button exitButton = new Button("Esci dal Gioco");
        exitButton.setOnAction(e -> {
            System.exit(0);
        });

// Layout del menu con i bottoni
        VBox menuLayout = new VBox(20);
        menuLayout.getChildren().addAll(startButton,exitButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: black;");
// Settiamo la scena del menu
        Scene menuScene = new Scene(menuLayout, 800, 600);
        primaryStage.setScene(menuScene);

        primaryStage.show();


    }

    /**
     * Inizia il gioco con una nuova istanza di SignorCervoGUI
     */
    private void startGame() {
        SignorCervoGUI gameCervoGUI = new SignorCervoGUI();
        Stage gameStage = new Stage();
        gameCervoGUI.start(gameStage);
    }

}
