package it.unibo.io;

import java.io.IOException;
import javafx.application.Application;

/**
 * The `Main` class is the entry point for the Signor Cervo game.
 */

public class Main {

    SignorCervoGUI gui = new SignorCervoGUI();

    /**
     * The main method to launch the SignorCervoGUI application.
     *
     * @param args Command-line arguments.
     * @throws InterruptedException If the application is interrupted.
     * @throws IOException If an I/O error occurs.
     */
    
    public static void main(String[] args) throws InterruptedException, IOException {
        Application.launch(SignorCervoGUI.class, args);   
    }
}