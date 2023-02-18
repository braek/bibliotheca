package be.koder.library.swing.menu.file;

import be.koder.library.swing.translations.Translations;

import javax.swing.*;

public final class FileMenu extends JMenu {
    public FileMenu() {
        setText(Translations.FILE);
        add(new AddBookMenuItem());
        add(new ExitMenuItem());
    }
}
