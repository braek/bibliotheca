package be.koder.library.swing;

import javax.swing.*;
import java.awt.*;

public final class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        setTitle("Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setVisible(true);
    }
}