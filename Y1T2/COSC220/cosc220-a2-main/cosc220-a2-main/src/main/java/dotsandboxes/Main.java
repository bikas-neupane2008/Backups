package dotsandboxes;

import javax.swing.*;
import java.awt.*;

/** Our main class that launches the app. */
public class Main {
    
    public static void main(String... args) throws Exception {

        JFrame mainWindow = new JFrame("Dots and Boxes");
        DotsAndBoxesGrid grid = new DotsAndBoxesGrid(15, 8, 2);

        // FIXME: Update this label to show your name and student number
        JLabel label = new JLabel("Andrew McKenzie: 220263507");

        JPanel borderPane = new JPanel(new BorderLayout());
        borderPane.add(label, BorderLayout.SOUTH);

        DotsAndBoxesUI dbUi = new DotsAndBoxesUI(grid);
        borderPane.add(dbUi.anchorPane, BorderLayout.CENTER);
        borderPane.add(dbUi.label, BorderLayout.NORTH);
        mainWindow.add(borderPane);

        mainWindow.pack();
        mainWindow.setVisible(true);

        // This sets what to do when we close the main window.
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
