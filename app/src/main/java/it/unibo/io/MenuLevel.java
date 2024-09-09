package it.unibo.io;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MenuLevel extends Application {

   private Stage primaryStage;
   private Player player;

   public MenuLevel(Player player) {
      this.player = player;
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      this.primaryStage = primaryStage;
      primaryStage.setTitle("MENU-LEVELS");

      // Creazione dei bottoni per i livelli
      Button startButton = createLevelButton("Livello 1", 0);
      Button startButton1 = createLevelButton("Livello 2", 1);
      Button startButton2 = createLevelButton("Livello 3", 2);
      Button startButton3 = createLevelButton("Livello 4", 3);
      Button startButton4 = createLevelButton("Livello 5", 4);
      Button startButton5 = createLevelButton("Livello 6", 5);

      // Bottone per uscire dal gioco
      Button exitButton = new Button("Indietro");
      exitButton.setOnAction(e -> {
         primaryStage.close();
         MenuSignorCervo menu = new MenuSignorCervo();
         Stage menuStage = new Stage();
         try {
            menu.start(menuStage);
         } catch (Exception e1) {
            e1.printStackTrace();
         }
      });

      int level = readNumberFromFile();
      disableButton(startButton1);
      disableButton(startButton2);
      disableButton(startButton3);
      disableButton(startButton4);
      disableButton(startButton5);

      if (level >= 1)
         enableButton(startButton1);
      if (level >= 2)
         enableButton(startButton2);
      if (level >= 3)
         enableButton(startButton3);
      if (level >= 4)
         enableButton(startButton4);
      if (level >= 5)
         enableButton(startButton5);

      // Layout del menu con i bottoni in griglia 2x3
      GridPane gridPane = new GridPane();
      gridPane.setHgap(150); // Spazio orizzontale tra i bottoni
      gridPane.setVgap(100); // Spazio verticale tra i bottoni
      gridPane.setPadding(new Insets(100)); // Spazio interno del GridPane
      gridPane.setAlignment(Pos.CENTER);
      gridPane.add(startButton, 0, 0);
      gridPane.add(startButton1, 1, 0);
      gridPane.add(startButton2, 0, 1);
      gridPane.add(startButton3, 1, 1);
      gridPane.add(startButton4, 0, 2);
      gridPane.add(startButton5, 1, 2);

      // Layout principale con BorderPane
      BorderPane borderPane = new BorderPane();
      borderPane.setCenter(gridPane);
      BorderPane.setMargin(exitButton, new Insets(50)); // Margine intorno al pulsante Esci
      borderPane.setBottom(exitButton);
      BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);

      // Imposta lo stile del layout principale
      borderPane.setStyle("-fx-background-color: black;");

      // Settiamo la scena del menu
      Scene menuScene = new Scene(borderPane, 800, 600);
      primaryStage.setScene(menuScene);
      primaryStage.show();

   }

   /**
    * Inizia il gioco con una nuova istanza di SignorCervoGUI
    */
   private void startGame(int level) {
      SignorCervoGUI gameCervoGUI = new SignorCervoGUI(new Game(level, player));
      Stage gameStage = new Stage();
      try {
         gameCervoGUI.start(gameStage);
      } catch (InterruptedException | IOException e) {
         e.printStackTrace();
      }
   }

   // Metodo per disabilitare un bottone e applicare lo stile grigio spento
   private void disableButton(Button button) {
      button.setDisable(true);
      button.setStyle("-fx-background-color: grey; -fx-text-fill: white;");
   }

   // Metodo per abilitare un bottone e ripristinare lo stile originale
   private void enableButton(Button button) {
      button.setDisable(false);
      button.setStyle(null);
   }

   // Metodo per creare un bottone di livello
   private Button createLevelButton(String text, int level) {
      Button button = new Button(text);
      button.setOnAction(e -> {
         Stage stage = (Stage) button.getScene().getWindow();
         stage.close();
         startGame(level);
      });
      return button;
   }

   // Metodo per leggere un numero da un file e creare la cartella/file se non esistono
   public static int readNumberFromFile() {
      int number = 0;
      try {
         File resourcesDir = new File(System.getProperty("user.dir") + "/src/main/java/it/unibo/io/progress");
         if (!resourcesDir.exists()) {
            resourcesDir.mkdirs();
            System.out.println("Directory 'progress' creata: " + resourcesDir.getPath());
         }

         File file = new File(resourcesDir, "level");

         System.out.println("Percorso assoluto del file: " + file.getAbsolutePath());

         if (!file.exists()) {
            file.createNewFile();
            System.out.println("File 'level' creato: " + file.getPath());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
               writer.write(String.valueOf(number));
               System.out.println("Numero iniziale 0 scritto nel file.");
            }
         } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
               String line = reader.readLine();
               if (line != null) {
                  number = Integer.parseInt(line);
                  System.out.println("Numero letto dal file: " + number);
               }
            }
         }
      } catch (IOException e) {
         System.err.println("Errore durante la lettura/scrittura del file: " + e.getMessage());
      }
      return number;
   }
}
