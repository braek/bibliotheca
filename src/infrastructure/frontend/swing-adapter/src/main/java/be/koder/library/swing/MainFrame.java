package be.koder.library.swing;

import be.koder.library.swing.menu.MainMenuBar;
import be.koder.library.swing.translations.Translations;

import javax.swing.*;
import java.awt.*;

public final class MainFrame extends JFrame {

    public static MainFrame instance;

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    private MainFrame() throws HeadlessException {
        setJMenuBar(MainMenuBar.INSTANCE);
        setTitle(Translations.LIBRARY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
    }

    public void showIt() {
        setVisible(true);
    }
}