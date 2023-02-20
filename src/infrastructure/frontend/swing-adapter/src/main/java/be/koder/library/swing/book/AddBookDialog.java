package be.koder.library.swing.book;

import be.koder.library.api.AddBook;
import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.swing.MainFrame;
import be.koder.library.swing.translations.Translations;
import be.koder.library.vocabulary.book.Author;
import be.koder.library.vocabulary.book.BookId;
import be.koder.library.vocabulary.book.Isbn;
import be.koder.library.vocabulary.book.Title;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class AddBookDialog extends JDialog implements ActionListener, AddBookPresenter {

    private AddBook addBook;

    private JTextField isbnTextField;
    private JTextField titleTextField;
    private JTextField authorTextField;

    public AddBookDialog() {
        super(MainFrame.getInstance(), Translations.ADD_BOOK, true);

        setSize(new Dimension(640, 480));

        isbnTextField = new JTextField();

        titleTextField = new JTextField();

        authorTextField = new JTextField();

        final JPanel panel = (JPanel) getContentPane();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel(Translations.ISBN + ":"));
        panel.add(isbnTextField);
        panel.add(new JLabel(Translations.TITLE + ":"));
        panel.add(titleTextField);
        panel.add(new JLabel(Translations.AUTHOR + ":"));
        panel.add(authorTextField);
        pack();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Isbn isbn = Isbn.fromString(isbnTextField.getText());
        final Title title = Title.fromString(titleTextField.getText());
        final Author author = Author.fromString(authorTextField.getText());
        addBook.addBook(isbn, title, author, this);
    }

    @Override
    public void added(BookId bookId) {

    }

    @Override
    public void isbnAlreadyExists() {

    }
}