package it.unibo.io;

import java.io.IOException;
import javafx.application.Application;

/**
 * The `Main` class is the entry point for the Signor Cervo game.
 */

public class Main {

    static MenuMain gui = new MenuMain();
    
    /**
    * Metodo principale per avviare l'applicazione.
    * @param args Argomenti da passare all'applicazione.
    * @throws InterruptedException Se si verifica un'interruzione durante l'esecuzione.
     @throws IOException Se si verifica un errore di input/output.
    */
    public static void main(String[] args) throws InterruptedException, IOException {
        Application.launch(MenuMain.class, args);
    }
}