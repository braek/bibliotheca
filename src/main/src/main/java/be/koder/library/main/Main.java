package be.koder.library.main;

import be.koder.library.swing.Facade;
import be.koder.library.swing.MainFrame;

// Wires everything together and initialize frontend
public final class Main {
    public static void main(String[] args) {
        new MainFrame(new Facade(
                AppContext.listBooks,
                AppContext.addBook,
                AppContext.modifyBook,
                AppContext.removeBook,
                AppContext.uploadHardcover
        ));
    }
}