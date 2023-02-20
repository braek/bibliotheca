package be.koder.library.swing.menu.file;

import be.koder.library.swing.book.AddBookDialog;
import be.koder.library.swing.translations.Translations;

import javax.swing.*;

public final class AddBookMenuItem extends JMenuItem {
    public AddBookMenuItem() {
        setText(Translations.ADD_BOOK);
        addActionListener(e -> {
            var addBookDialog = new AddBookDialog();
            addBookDialog.setVisible(true);
        });
    }
}