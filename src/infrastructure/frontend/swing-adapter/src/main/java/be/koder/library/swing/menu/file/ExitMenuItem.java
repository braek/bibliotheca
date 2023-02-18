package be.koder.library.swing.menu.file;

import be.koder.library.swing.MainFrame;
import be.koder.library.swing.translations.Translations;

import javax.swing.*;

public final class ExitMenuItem extends JMenuItem {
    public ExitMenuItem() {
        setText(Translations.EXIT);
        addActionListener(e -> {
            MainFrame.getInstance().setVisible(false);
            MainFrame.getInstance().dispose();
            System.exit(0);
        });
    }
}