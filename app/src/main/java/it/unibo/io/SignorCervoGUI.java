package it.unibo.io;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * La classe SignorCervoGUI rappresenta l'interfaccia grafica del gioco "Signor Cervo".
 * Estende la classe Application di JavaFX per creare l'interfaccia utente.
 */
public class SignorCervoGUI extends Application {

    private MediaPlayer mediaPlayer;
    private static List<File> resources;
    private static ImageView imageView = new ImageView();
    private static TextArea terminal = new TextArea();
    private static JsonReader j  = new JsonReader();
    private static Game game = new Game();

    /**
     * Metodo principale che avvia l'applicazione JavaFX.
     *
     * @param primaryStage lo stage principale dell'applicazione
     * @throws InterruptedException se si verifica un'interruzione durante l'esecuzione
     * @throws IOException se si verifica un errore di I/O durante l'esecuzione
     */
    @Override
    public void start(Stage primaryStage) throws InterruptedException, IOException {
        primaryStage.setTitle("Signor Cervo Game");

        resources = GetResources.findResourcesDirectory(new File(System.getProperty("user.dir")), "resource");

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800);
        imageView.setFitHeight(400);

        terminal.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        terminal.setEditable(false);
        j.getRule();

        TextField userInput = new TextField();
        userInput.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        userInput.setPromptText("premi invio per andare avanti");
        userInput.setOnAction(event -> {
            terminal.clear();
            game.output();

            String command = userInput.getText().trim();
            if (!command.isEmpty()) {
                terminal.clear();
                game.input(command);
                userInput.clear();
            }
        });

        VBox gameLayout = new VBox(imageView, terminal, userInput);
        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(gameLayout, 800, 600, Color.BLACK);
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
}
