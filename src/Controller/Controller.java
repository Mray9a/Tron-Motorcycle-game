package Controller;

import Model.Arena;
import View.Game2D;
import View.HighScoreDisplay;
import View.View;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements KeyListener {
    private final Arena arena;
    private Game2D game2D;
    private View gameWindow;
    private HighScores highScores;
    private Timer gameTimer = null;
    private final int right = 0;
    private final int down = 1;
    private final int left = 2;
    private final int up = 3;

    public Controller(Arena arena, View gameWindow) {
        try {
            highScores = new HighScores(100);
        } catch (SQLException ignored) {}
        this.arena = arena;
        this.gameWindow = gameWindow;
        this.game2D = gameWindow.getView();
        initialViewSetup();
        // Set up game timer (10 updates per second)
        gameTimer = new Timer(100, e -> {
            if (arena.isGameOver()) {
                game2D.drawGameOverMessage(game2D.getGraphics(), arena.getWinner(), arena.getPlayer1Name(), arena.getPlayer2Name());
                if (arena.getWinner() != 0) {
                    updateLeaderboard();
                }
                gameTimer.stop();
            } else {
                arena.update();
                game2D.updateBikersData(
                        arena.getBiker1().getPos(), arena.getBiker2().getPos(),
                        arena.getBiker1().getTrail(), arena.getBiker2().getTrail()
                );
                game2D.repaint();
            }
        });
    }

    private void initialViewSetup() {
        gameWindow.setNewGameListener(e -> NewGame());
        gameWindow.setLeaderboardListener(e -> {
            HighScoreDisplay leaderboardDialog = new HighScoreDisplay(gameWindow.getFrame(), getBestScores());
            leaderboardDialog.setVisible(true);
        });
        game2D.setObstacles(arena.getObstacles());
        game2D.addKeyListener(this);
        game2D.setColors(arena.getBiker1().getColor(), arena.getBiker2().getColor());
    }

    private void updateLeaderboard() {
        String winnerName = arena.getWinnerName();
        if (winnerName.isEmpty()) {return;}
        ArrayList<HighScore> prevHighScores = null;
        try {
            prevHighScores = highScores.getHighScores();
        } catch (SQLException ignored) {}
        AtomicBoolean found = new AtomicBoolean(false);
        prevHighScores.stream().findFirst().ifPresent(highScore -> {
            if (highScore.name().equals(winnerName)) {
                found.set(true);
                try {
                    highScores.putHighScore(winnerName, highScore.score() + 1);
                } catch (SQLException ignored) {}
            }
        });
        if (found.get()) { return;}
        try {
            highScores.putHighScore(winnerName, 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<HighScore> getBestScores() {
        try {
            return new ArrayList<>(highScores.getHighScores().subList(0, Math.min(10, highScores.getHighScores().size())));
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                if (arena.getBiker1().getDirection() == up) {
                    arena.turnBiker1Left();
                } else if (arena.getBiker1().getDirection() == down) {
                    arena.turnBiker1Right();
                }
                break;
            case KeyEvent.VK_D:
                if (arena.getBiker1().getDirection() == down) {
                    arena.turnBiker1Left();
                } else if (arena.getBiker1().getDirection() == up) {
                    arena.turnBiker1Right();
                }
                break;
            case KeyEvent.VK_W:
                if (arena.getBiker1().getDirection() == right) {
                    arena.turnBiker1Left();
                } else if (arena.getBiker1().getDirection() == left) {
                    arena.turnBiker1Right();
                }
                break;
            case KeyEvent.VK_S:
                if (arena.getBiker1().getDirection() == left) {
                    arena.turnBiker1Left();
                } else if (arena.getBiker1().getDirection() == right) {
                    arena.turnBiker1Right();
                }
                break;

            case KeyEvent.VK_LEFT:
                if (arena.getBiker2().getDirection() == up) {
                    arena.turnBiker2Left();
                } else if (arena.getBiker2().getDirection() == down) {
                    arena.turnBiker2Right();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (arena.getBiker2().getDirection() == down) {
                    arena.turnBiker2Left();
                } else if (arena.getBiker2().getDirection() == up) {
                    arena.turnBiker2Right();
                }
                break;
            case KeyEvent.VK_UP:
                if (arena.getBiker2().getDirection() == right) {
                    arena.turnBiker2Left();
                } else if (arena.getBiker2().getDirection() == left) {
                    arena.turnBiker2Right();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (arena.getBiker2().getDirection() == left) {
                    arena.turnBiker2Left();
                } else if (arena.getBiker2().getDirection() == right) {
                    arena.turnBiker2Right();
                }
                break;
        }
    }

    public void NewGame() {
        arena.reset();
        gameTimer.start();
        game2D.setObstacles(arena.getObstacles());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}