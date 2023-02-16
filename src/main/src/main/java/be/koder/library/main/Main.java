package be.koder.library.main;

import be.koder.library.swing.Facade;
import be.koder.library.swing.MainFrame;

public final class Main {
    public static void main(String[] args) {
        new MainFrame(new Facade(
                AppContext.addBook,
                AppContext.modifyBook,
                AppContext.removeBook,
                AppContext.uploadHardcover
        ));
    }
}