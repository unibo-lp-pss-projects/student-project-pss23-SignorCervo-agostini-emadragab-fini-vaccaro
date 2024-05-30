package it.unibo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuGui extends Application {

   @Override
   public void start(Stage primaryStage) throws Exception {
      primaryStage.setTitle("MENU");

      // Bottone per iniziare a giocare
      Button startButton = new Button("Nuova Partita");
      startButton.setOnAction(e -> {
         primaryStage.close();
         startGame();
      });

      // Bottone per continuare la partita salvata
      Button continueButton = new Button("Continua Partita");
      continueButton.setOnAction(e -> {
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
         startLevels();
      });

      // Bottone per uscire dal gioco
      Button exitButton = new Button("Esci dal Gioco");
      exitButton.setOnAction(e -> {
         System.exit(0);
      });

      // creo una lista dei bottoni
      List<Button> buttons = Arrays.asList(startButton, continueButton, levelsButton, exitButton);
      // imposto la larghezza e l'altezza minima di ciascun bottone
      for (Button button : buttons) {
         button.setMinWidth(100);
         button.setMinHeight(10); 
      }

      // Layout del menu con i bottoni
      VBox menuLayout = new VBox(20);
      menuLayout.getChildren().addAll(startButton, continueButton, levelsButton, exitButton);
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
      try {
         gameCervoGUI.start(gameStage);
      } catch (InterruptedException | IOException e) {
         e.printStackTrace();
      }
   }

   // fa partire la finestra dei livelli
   private void startLevels() {
      Levels levels = new Levels();
      Stage levelsStage = new Stage();
      try {
         levels.start(levelsStage);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // gestione dei vari casi quando si preme su Continua partita
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

      String content = new String(Files.readAllBytes(Paths.get("checkpoint.json")));
      primaryStage.close();
      JSONObject checkpoint = new JSONObject(content);
      loadGameFromCheckpoint(checkpoint); // se il file esiste e non è vuoto fai partire il gioco dal checkpoint
   }

   // carica il gioco usando il file di checkpoint
   private void loadGameFromCheckpoint(JSONObject checkpoint) {
      // partendo da nuova istanza di SignorCervoGUI
      SignorCervoGUI gameCervoGUI = new SignorCervoGUI();
      Stage gameStage = new Stage();
      Game game = new Game();
      try {
         gameCervoGUI.start(gameStage);

         // facciamo partire il gioco usando i dati che abbiamo salvato con questi metodi
         game.loadState(checkpoint.getJSONObject("gameState"));
         String imageUrl = checkpoint.getString("currentImage");
         Image image = new Image(imageUrl);
         gameCervoGUI.updateImageView(image);
         gameCervoGUI.updateTerminal(checkpoint.getString("terminalText"));

      } catch (InterruptedException | IOException e) {
         e.printStackTrace();
      }
   }
}
