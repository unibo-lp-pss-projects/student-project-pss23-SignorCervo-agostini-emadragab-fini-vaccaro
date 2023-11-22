package it.unibo.io;
import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignorCervoGUI extends Application {

    private static TextArea terminal = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Signor Cervo Game");
        
        // Carica un'immagine (sostituisci "your_image.jpg" con il percorso del tuo file immagine)
        List<File> resources = GetResources.findResourcesDirectory(new File(System.getProperty("user.dir")));
        String imagePath = resources.get(5).toURI().toString().replace("file:/", "");
        Image image = new Image("file:" + imagePath); // Carica l'immagine

        ImageView imageView = new ImageView(image);

        // Imposta il ridimensionamento dell'immagine per adattarla allo spazio disponibile
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800); // Larghezza massima
        imageView.setFitHeight(400); // Altezza massima

        // Crea una TextArea per il "terminale" del gioco con sfondo nero e testo bianco
        terminal.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        terminal.setEditable(false);
        terminal.setText("Welcome to Signor Cervo.\n");

        // Crea un campo di input per il giocatore
        TextField userInput = new TextField();
        userInput.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        userInput.setPromptText("Type your command here");

        // Gestione dell'input del giocatore
        userInput.setOnAction(event -> {
            String command = userInput.getText().trim();
            if (!command.isEmpty()) {
                terminal.appendText("> " + command + "\n");
                // Gestisci il comando e aggiorna il terminale
                userInput.clear();
            }
        });

        // Crea il layout del gioco con l'immagine sopra e l'input/terminale sotto
        VBox gameLayout = new VBox(imageView, userInput, terminal);
        gameLayout.setAlignment(Pos.CENTER);

        // Imposta lo sfondo della scena a nero
        gameLayout.setStyle("-fx-background-color: black;");

        // Imposta la scena principale
        Scene scene = new Scene(gameLayout, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:" + resources.get(6).getAbsolutePath()));

        primaryStage.show();
    }

    public static void updateStatusTerminal(String text) {
        terminal.setText(text);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Image image = new Image("file:///C:/Users/Admin/Desktop/GitHub/SignorCervo/Nome/src/main/java/it/unibo/io/resource/signorcervo.jpg");
