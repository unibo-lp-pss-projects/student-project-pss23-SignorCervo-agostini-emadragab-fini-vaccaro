package it.unibo.io;

import javafx.application.Application;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

import java.util.Optional;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public class MenuSignorCervo extends Application {

   private Stage primaryStage;
   private int dialogo;

   @Override
   public void start(Stage primaryStage) throws Exception {
      this.primaryStage = primaryStage;
      primaryStage.setTitle("MENU-SIGNORCERVO");

      // Bottone per iniziare il gioco da zero
      Button startNewGameButton = new Button("Nuova Partita");
      startNewGameButton.setOnAction(e -> {
         showConfirmationAlert();
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
      for (Button button : buttons) {
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
         updateLevelFile(level);
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
      String content = new String(Files.readAllBytes(Paths.get("checkpoint.json")));
      primaryStage.close();
      JSONObject checkpoint = new JSONObject(content);
      int dialogo = checkpoint.getInt("dialogo");  // Leggi dialogo dal checkpoint
      loadGameFromCheckpoint(checkpoint, dialogo); // se il file esiste e non è vuoto fai partire il gioco dal checkpoint
   }

   // carica il gioco usando il file di checkpoint
   private void loadGameFromCheckpoint(JSONObject checkpoint, int dialogo) {
      // partendo da nuova istanza di SignorCervoGUI
      Game game = new Game(dialogo);
      SignorCervoGUI gameCervoGUI = new SignorCervoGUI(game);
      Stage gameStage = new Stage();
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

   private void menuLevelStart() {
      MenuLevel menuLevel = new MenuLevel();
      Stage levelStage = new Stage();
      try {
         menuLevel.start(levelStage);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // Metodo per azzerare il file level
   private void resetLevelFile() {
      SignorCervoGUI.writeNumberToFile(0);
   }

   // Metodo per aggiornare il file level con il progresso
   private void updateLevelFile(int level) {
      SignorCervoGUI.writeNumberToFile(level);
   }

   private void showConfirmationAlert() {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Conferma Nuova Partita");
      alert.setHeaderText("Sei sicuro?");
      alert.setContentText("Se inizi una nuova partita perderai tutti i tuoi progressi.");

      ButtonType buttonTypeYes = new ButtonType("Si");
      ButtonType buttonTypeNo = new ButtonType("No");

      alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == buttonTypeYes) {
         primaryStage.close();
         resetLevelFile();
         startGame(0);
      } else {
         alert.close();
      }
   }
}
