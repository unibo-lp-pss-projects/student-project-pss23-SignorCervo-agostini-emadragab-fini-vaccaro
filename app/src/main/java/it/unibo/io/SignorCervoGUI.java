package it.unibo.io;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class SignorCervoGUI extends Application {

    private MediaPlayer mediaPlayer;
    private static List<File> resources;
    private static ImageView imageView = new ImageView();
    private static TextArea terminal = new TextArea();
    private static JsonReader j = new JsonReader();
    private static Game game = new Game();

    @Override
    public void start(Stage primaryStage) throws InterruptedException, IOException {
        primaryStage.setTitle("Signor Cervo Game");

        resources = GetResources.findResourcesDirectory(new File(System.getProperty("user.dir")), "resource");

        // Imposta il ridimensionamento dell'immagine per adattarla allo spazio disponibile
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800); // Larghezza massima
        imageView.setFitHeight(400); // Altezza massima

        // Crea una TextArea per il "terminale" del gioco con sfondo nero e testo bianco
        terminal.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        terminal.setEditable(false);
        j.getRule();

        // Crea un campo di input per il giocatore
        TextField userInput = new TextField();
        userInput.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        userInput.setPromptText("premi invio per andare avanti");

        // Gestione dell'input del giocatore
        userInput.setOnAction(event -> {
            terminal.clear();
            game.output();

            String command = userInput.getText().trim();
            if (!command.isEmpty()) {
                // Gestisci il comando e aggiorna il terminale
                terminal.clear();
                game.input(command);
                userInput.clear();
            }
        });

        // Crea il layout del gioco con l'immagine sopra e l'input/terminale sotto
        VBox gameLayout = new VBox(imageView, terminal, userInput);
        gameLayout.setAlignment(Pos.CENTER);

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
        menuButtonLayout.setAlignment(Pos.TOP_RIGHT); //angolo a destra
        
        // BorderPane per contenere il layout del gioco e il layout del bottone del menu
        BorderPane root = new BorderPane();
        root.setCenter(gameLayout); // Layout del gioco
        root.setTop(menuButtonLayout); // Layout del bottone del menu
        // Imposta lo sfondo della scena a nero
        root.setStyle("-fx-background-color: black;");

        // Imposta lo sfondo della scena a nero
        gameLayout.setStyle("-fx-background-color: black;");

        // Imposta la scena principale
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:" + getIcon("signorcervo.jpg")));

        initializeMediaPlayer();

        primaryStage.show();

    }

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

        // Set the music to loop indefinitely
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Start playing the background music
        mediaPlayer.play();
    }

    public static void updateStatusTerminal(String text) {
        terminal.appendText(text);
    }

    public static void updateImage(String imageName) {
        for (File resource : resources) {
            if (resource.getName().equals(imageName)) {
                Image newImage = new Image("file:" + resource.getAbsolutePath());
                imageView.setImage(newImage);
                return;
            }
        }
    }

    private String getIcon(String icon) {
        String iconPath = "";
        for (File resource : resources) {
            if (resource.getName().equals(icon)) {
                iconPath = resource.getAbsolutePath();
            }
        }
        return iconPath;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
