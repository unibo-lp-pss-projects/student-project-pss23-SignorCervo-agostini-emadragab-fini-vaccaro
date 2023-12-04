package it.unibo.io;

import java.io.IOException;

import javafx.application.Application;

public class Main {

    SignorCervoGUI gui = new SignorCervoGUI();
    
    public static void main(String[] args) throws InterruptedException, IOException {
        Application.launch(SignorCervoGUI.class, args);   
    }
}