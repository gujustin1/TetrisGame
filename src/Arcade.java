/* Created by Chris Gu and Justin Gu
 * Date: March 2018
 * Tetris Project
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Arcade extends JFrame {
    public Arcade() throws FileNotFoundException, FontFormatException, IOException {
        super("AP Java Arcade");

        JavaArcade game= new Tetris(800, 500);

        GameStats display= new GameStats(game); // passing in a JavaArcade, therefore I know I can
                                                // call getHighScore(),
                                                // getScore()

        ControlPanel controls= new ControlPanel(game, display); // Also passing in JavaArcade to
                                                                // ControlPanel, I know
                                                                // you will respond to buttons

        JPanel panel= new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 5, 0, 5));
        panel.add(display, BorderLayout.NORTH);
        panel.add((JPanel) game, BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);

        Container c= getContentPane();
        c.add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args)
        throws FileNotFoundException, FontFormatException, IOException {
        Arcade window= new Arcade();
        window.setBounds(100, 100, 850, 650);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
