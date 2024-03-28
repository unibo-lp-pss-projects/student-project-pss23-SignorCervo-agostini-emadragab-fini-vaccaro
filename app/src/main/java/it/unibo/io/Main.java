package it.unibo.io;

import java.io.IOException;

import javafx.application.Application;

public class Main {

    static MenuGui gui = new MenuGui();
    
    public static void main(String[] args) throws InterruptedException, IOException {
        Application.launch(MenuGui.class, args);
    }
}