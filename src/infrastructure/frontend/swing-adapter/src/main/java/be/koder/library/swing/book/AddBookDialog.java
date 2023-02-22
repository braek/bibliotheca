package be.koder.library.swing.book;

import be.koder.library.api.AddBook;
import be.koder.library.api.presenter.AddBookPresenter;
import be.koder.library.swing.MainFrame;
import be.koder.library.swing.translations.Translations;
import be.koder.library.vocabulary.book.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class AddBookDialog extends JDialog implements ActionListener, AddBookPresenter {

    private AddBook addBook;

    private final JTextField isbnTextField = new JTextField();
    private final JTextField titleTextField = new JTextField();
    private final JTextField authorTextField = new JTextField();

    public AddBookDialog() {
        super(MainFrame.getInstance(), Translations.ADD_BOOK, true);

        setSize(new Dimension(640, 480));

        final JPanel panel = (JPanel) getContentPane();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel(Translations.ISBN + ":"));
        panel.add(isbnTextField);
        panel.add(new JLabel(Translations.TITLE + ":"));
        panel.add(titleTextField);
        panel.add(new JLabel(Translations.AUTHOR + ":"));
        panel.add(authorTextField);
        panel.add(new JLabel(" "));
        final JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(this);
        panel.add(addBookButton);
        pack();
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        try {
            final Isbn isbn = Isbn.fromString(isbnTextField.getText());
            final Title title = Title.fromString(titleTextField.getText());
            final Author author = Author.fromString(authorTextField.getText());
            addBook.addBook(isbn, title, author, this);
        } catch (final InvalidIsbnException e) {
            JOptionPane.showMessageDialog(this, "ISBN is invalid", Translations.WARNING, JOptionPane.WARNING_MESSAGE);
        } catch (final InvalidTitleException e) {
            JOptionPane.showMessageDialog(this, "Title is invalid", Translations.WARNING, JOptionPane.WARNING_MESSAGE);
        } catch (final InvalidAuthorException e) {
            JOptionPane.showMessageDialog(this, "Author is invalid", Translations.WARNING, JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void added(BookId bookId) {

    }

    @Override
    public void isbnAlreadyExists() {

    }
}