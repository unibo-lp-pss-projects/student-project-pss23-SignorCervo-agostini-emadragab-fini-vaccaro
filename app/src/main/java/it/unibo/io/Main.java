package it.unibo.io;

import java.io.IOException;

import javafx.application.Application;

public class Main {

    SignorCervoGUI gui = new SignorCervoGUI();
    
    /**
    * Metodo principale per avviare l'applicazione.
    * @param args Argomenti da passare all'applicazione.
    * @throws InterruptedException Se si verifica un'interruzione durante l'esecuzione.
     @throws IOException Se si verifica un errore di input/output.
    */
    public static void main(String[] args) throws InterruptedException, IOException {
        Application.launch(SignorCervoGUI.class, args);   
    }
}