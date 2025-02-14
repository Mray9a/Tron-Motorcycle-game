package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JPanel {
    JMenuItem newGame;
    JMenuItem leaderboard;
    JFrame frame;
    Game2D view;

    public View(int width, int height) {
        frame = new JFrame("TRON Light Cycles");
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        view = new Game2D(width, height);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        newGame = new JMenuItem("New Game");

        menuBar.add(gameMenu);
        gameMenu.add(newGame);
        JMenu scores = new JMenu("Scores");
        leaderboard = new JMenuItem("Leaderboard");
        menuBar.add(scores);
        scores.add(leaderboard);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void setNewGameListener(ActionListener listener) {
        newGame.addActionListener(listener);
    }

    public void setLeaderboardListener(ActionListener listener) {
        leaderboard.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }

    public Game2D getView() {
        return view;
    }
}
