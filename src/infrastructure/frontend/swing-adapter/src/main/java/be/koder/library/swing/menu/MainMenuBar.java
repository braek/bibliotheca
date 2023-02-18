package be.koder.library.swing.menu;

import be.koder.library.swing.menu.file.FileMenu;

import javax.swing.*;

public final class MainMenuBar extends JMenuBar {

    public static final MainMenuBar INSTANCE = new MainMenuBar();

    private MainMenuBar() {
        add(new FileMenu());
    }
}