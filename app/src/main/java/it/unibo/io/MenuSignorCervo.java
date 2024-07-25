package it.unibo.io;

import javafx.application.Application;
import javafx.geometry.Insets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MenuSignorCervo extends Application {

   private Stage primaryStage;

   @Override
   public void start(Stage primaryStage) throws Exception {
      this.primaryStage = primaryStage;
      primaryStage.setTitle("MENU-SIGNORCERVO");

      // Bottone per iniziare il gioco da zero
      Button startNewGameButton = new Button("Nuova Partita");
      startNewGameButton.setOnAction(e -> {
         primaryStage.close();
         startGame(0);
      });

      // Bottone per continuare la partita salvata
      Button continueGameButton = new Button("Continua Partita");
      continueGameButton.setOnAction(e -> {
         try {
            handleContinuaPartitaButton(primaryStage);
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });

      // Bottone per andare ai livelli del gioco
      Button levelsButton = new Button("Livelli del gioco");
      levelsButton.setOnAction(e -> {
         primaryStage.close();
         menuLevelStart();
      });

      // Bottone per uscire dal gioco
      Button backToMainMenuButton = new Button("Torna al Menu principale");
      backToMainMenuButton.setOnAction(e -> {
         primaryStage.close();
         MenuMain menu = new MenuMain();
         Stage menuStage = new Stage();
         try {
            menu.start(menuStage);
         } catch (Exception e1) {
            e1.printStackTrace();
         }
      });

      // creo una lista dei bottoni
      List<Button> buttons = Arrays.asList(startNewGameButton, continueGameButton, levelsButton, backToMainMenuButton);
      // imposto la larghezza e l'altezza minima di ciascun bottone
      for(Button button:buttons){
         button.setMinWidth(100);
         button.setMinHeight(10);
      }

      // Layout del menu con i bottoni
      VBox menuLayout = new VBox(20);
      menuLayout.getChildren().addAll(startNewGameButton, continueGameButton, levelsButton, backToMainMenuButton);
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
   private void startGame(int level) {
      SignorCervoGUI gameCervoGUI = new SignorCervoGUI(new Game(level));
      Stage gameStage = new Stage();
      try {
         gameCervoGUI.start(gameStage);
      } catch (InterruptedException | IOException e) {
         e.printStackTrace();
      }
   }


   private void handleContinuaPartitaButton(Stage primaryStage) throws IOException {
      // controllo se il file checkpoint esiste oppure se è vuoto
      if (!Files.exists(Paths.get("checkpoint.json"))
            || new String(Files.readAllBytes(Paths.get("checkpoint.json"))).isEmpty()) {
         Alert errorAlert = new Alert(AlertType.ERROR);
         errorAlert.setTitle("Errore");
         errorAlert.setHeaderText("Nessun Checkpoint Salvato");
         errorAlert.setContentText("Nessun Checkpoint salvato. Inizia una nuova partita.");
         errorAlert.showAndWait();
         return;
      }
      // TODO else carica il file checkpoint.json e continua partita
   }

      private void menuLevelStart() {
         MenuLevel menuLevel = new MenuLevel();
         Stage levelStage = new Stage();
         try {
            menuLevel.start(levelStage);
         } catch (Exception e) {
            e.printStackTrace();
         }
   }
   }
