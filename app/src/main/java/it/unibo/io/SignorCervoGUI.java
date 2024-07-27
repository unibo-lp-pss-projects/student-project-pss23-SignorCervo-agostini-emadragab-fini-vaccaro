package it.unibo.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.xml.validation.Validator;

import org.json.JSONObject;

/**
 * La classe SignorCervoGUI rappresenta l'interfaccia grafica del gioco "Signor
 * Cervo".
 * Estende la classe Application di JavaFX per creare l'interfaccia utente.
 */
public class SignorCervoGUI extends Application {

   private static Stage primaryStage;
   private MediaPlayer mediaPlayer;
   private static List<File> resources;
   private static ImageView imageView = new ImageView();
   private static VBox buttonLayout = new VBox(10);
   private static TextArea terminal = new TextArea();
   private static JsonReader j = new JsonReader();
   private static Game game;

   public SignorCervoGUI(Game game) {
      this.game = game;
   }

   /**
    * Metodo principale che avvia l'applicazione JavaFX.
    *
    * @param primaryStage lo stage principale dell'applicazione
    * @throws InterruptedException se si verifica un'interruzione durante
    *                              l'esecuzione
    * @throws IOException          se si verifica un errore di I/O durante
    *                              l'esecuzione
    */
   @Override
   public void start(Stage primaryStage) throws InterruptedException, IOException {
      this.primaryStage = primaryStage;
      primaryStage.setTitle("Signor Cervo Game");

      resources = GetResources.findResourcesDirectory(new File(System.getProperty("user.dir")), "resource");

      imageView.setPreserveRatio(true);
      imageView.setFitWidth(800);
      imageView.setFitHeight(400);

      terminal.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
      terminal.setEditable(false);
      j.getRule();

      // Bottone che porta il giocatore al menu principale
      Button backButton = new Button("Menu principale");
      backButton.setOnAction(e -> handleMenuPrincipaleButton());
      backButton.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
      // Layout per il bottone del menu
      HBox menuButtonLayout = new HBox(backButton);
      menuButtonLayout.setAlignment(Pos.TOP_RIGHT); // angolo a destra
      menuButtonLayout.setPadding(new Insets(10));

      VBox gameLayout = new VBox(10, menuButtonLayout, imageView, terminal);
      gameLayout.setAlignment(Pos.CENTER);
      gameLayout.setStyle("-fx-background-color: black;");
      buttonLayout.setAlignment(Pos.CENTER);
      Button answerButton = new Button("vai avanti");
      answerButton.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
      answerButton.setOnAction(event -> {
         terminal.clear();
         buttonLayout.getChildren().clear();
         game.output();
      });
      buttonLayout.getChildren().add(answerButton);
      gameLayout.getChildren().add(buttonLayout);

      Scene scene = new Scene(gameLayout, 800, 800, Color.BLACK);
      primaryStage.setScene(scene);
      primaryStage.getIcons().add(new Image("file:" + getIcon("signorcervo.jpg")));

      initializeMediaPlayer();
      primaryStage.show();
   }

   /**
    * Inizializza il MediaPlayer per riprodurre la musica di sottofondo.
    */
   private void initializeMediaPlayer() {
      String musicFile = "music.mp3";
      String musicPath = "";
      for (File resource : resources) {
         if (resource.getName().equals(musicFile)) {
            musicPath = resource.getAbsolutePath();
         }
      }
      Media sound = new Media(new File(musicPath).toURI().toString());
      mediaPlayer = new MediaPlayer(sound);
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      mediaPlayer.play();
   }

   /**
    * Aggiorna il terminale di stato con il testo fornito.
    *
    * @param text il testo da aggiungere al terminale di stato
    */
   public static void updateStatusTerminal(String text) {
      terminal.appendText(text);
   }

   public void updateTerminal(String text) {
      terminal.setText(text);
   }

   /**
    * Aggiorna i bottoni
    *
    * @param text il testo da aggiungere nei bottoni
    * @param i    il numero della risposta
    */
   public static void updateButton(String text, int i) {
      if (isDoubleOrInt(text)) {
         if (game.playerCoin(Integer.parseInt(text))) {
            return;
         }
      }
      Button answerButton = new Button(text);
      answerButton.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
      answerButton.setOnAction(event -> {
         terminal.clear();
         game.input(i);
         buttonLayout.getChildren().clear();
         new Thread(() -> {
            try {
               Thread.sleep(1000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            javafx.application.Platform.runLater(() -> {
               terminal.clear();
               if (game.output() == false)
                  menuLevel();
            });
         }).start();
      });

      buttonLayout.getChildren().add(answerButton);
   }

   /**
    * Aggiorna l'immagine visualizzata con l'immagine specificata.
    *
    * @param imageName il nome del file dell'immagine da visualizzare
    */
   public static void updateImage(String imageName) {
      for (File resource : resources) {
         if (resource.getName().equals(imageName)) {
            Image newImage = new Image("file:" + resource.getAbsolutePath());
            imageView.setImage(newImage);
            return;
         }
      }
   }

   public void updateImageView(Image image) {
      imageView.setImage(image);
   }

   /**
    * Ottiene il percorso dell'icona specificata.
    *
    * @param icon il nome del file dell'icona
    * @return il percorso completo del file dell'icona
    */
   private String getIcon(String icon) {
      String iconPath = "";
      for (File resource : resources) {
         if (resource.getName().equals(icon)) {
            iconPath = resource.getAbsolutePath();
         }
      }
      return iconPath;
   }

   /**
    * Metodo principale per avviare l'applicazione.
    *
    * @param args gli argomenti della riga di comando
    */
   public static void main(String[] args) {
      launch(args);
   }

   /**
    * Controlla se una stringa è un double o un intero.
    * 
    * @return -1 se non è una stringa valida, 0 se è un int, 1 se è un double
    */
   public static boolean isDoubleOrInt(String num) {
      try {
         Integer.parseInt(num);
         return true;
      } catch (Exception e) {
         try {
            Double.parseDouble(num);
            return true;
         } catch (Exception w) {
            return false;
         }
      }
   }

   // Metodo per aprire il menu dei livelli
   private static void menuLevel() {
      primaryStage.close();
      writeNumberToFile(game.getLevel() + 1);
      MenuLevel menuLevel = new MenuLevel();
      Stage levelStage = new Stage();
      try {
         menuLevel.start(levelStage);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // Metodo per salvare il numero del livello in un file
   public static void writeNumberToFile(int number) {
      try {
         // Trova o crea la directory 'date' all'interno del package java\it\nibo\io
         File resourcesDir = new File(System.getProperty("user.dir") + "/src/main/java/it/unibo/io/date");
         if (!resourcesDir.exists()) {
            resourcesDir.mkdirs();
            System.out.println("Directory 'date' creata: " + resourcesDir.getPath());
         }

         // Specifica il percorso del file 'level'
         File file = new File(resourcesDir, "level");

         // Stampa di debug per verificare il percorso assoluto
         System.out.println("Percorso assoluto del file: " + file.getAbsolutePath());

         // Crea il file se non esiste
         if (!file.exists()) {
            file.createNewFile();
            System.out.println("File 'level' creato: " + file.getPath());
         }

         // Scrive il numero nel file
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.valueOf(number));
            System.out.println("Numero scritto nel file: " + number);
         }
      } catch (IOException e) {
         System.err.println("Errore durante la scrittura del file: " + e.getMessage());
      }
   }

   private void handleMenuPrincipaleButton() {
      Alert exitAlert = new Alert(AlertType.CONFIRMATION);
      exitAlert.setTitle("Conferma Uscita");
      exitAlert.setHeaderText("Vuoi tornare al menu principale?");
      exitAlert.setContentText("Dovrai confermare se salvare il checkpoint.");

      ButtonType result = exitAlert.showAndWait().orElse(ButtonType.CANCEL);

      if (result == ButtonType.OK) {
         // stop la musica
         if (mediaPlayer != null) {
            mediaPlayer.stop();
         }

         Alert saveAlert = new Alert(AlertType.CONFIRMATION);
         saveAlert.setTitle("Salva Checkpoint");
         saveAlert.setHeaderText("Vuoi salvare il checkpoint?");
         saveAlert.setContentText("Potrai riprendere a giocare da questo punto.");

         ButtonType saveResult = saveAlert.showAndWait().orElse(ButtonType.CANCEL);

         if (saveResult == ButtonType.OK) {
            saveCheckpoint();
         }
         goToMainMenu();
      }
   }

   private void saveCheckpoint() {
      JSONObject checkpoint = new JSONObject();

      // dati da salvare nel file checkpoint.json
      checkpoint.put("gameState", game.getState());
      checkpoint.put("currentImage", imageView.getImage().getUrl());
      checkpoint.put("terminalText", terminal.getText());
      checkpoint.put("dialogo", game.getDialogo());

      try (FileWriter file = new FileWriter("checkpoint.json")) {
         file.write(checkpoint.toString());
         file.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void goToMainMenu() {
      // Chiude la finestra attuale
      if (primaryStage != null) {
         primaryStage.close();
      }
      MenuSignorCervo menu = new MenuSignorCervo();
      Stage menuStage = new Stage();
      try {
         // Apre la finestra del menu
         menu.start(menuStage);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }
}