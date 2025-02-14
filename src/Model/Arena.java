package Model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Arena {
    private final int width;
    private final int height;
    private final ArrayList<Point> obstacles;
    private Biker biker1;
    private Biker biker2;
    private boolean gameOver;
    private int winner; // 0: no winner yet, 1: biker1 won, 2: biker2 won, 3: draw
    private final String player1Name;
    private final String player2Name;

    public Arena(int width, int height, String player1Name, String player2Name, Color color1, Color color2) {
        this.width = width;
        this.height = height;
        this.obstacles = new ArrayList<>();
        this.gameOver = false;
        this.winner = 0;
        try {
            loadLevel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Initialize bikers at opposite sides
        this.biker1 = new Biker(3, height / 2, 0, color1);  // Starts from left, moving right
        this.biker2 = new Biker(width - 4, height / 2, 2, color2);   // Starts from right, moving left
        // Store player names
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    private void loadLevel() throws IOException {
        String levelPath = "data/level" + (int) (Math.random() * 10) + ".txt";
        BufferedReader br = new BufferedReader(new FileReader(levelPath));
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char blockType : line.toCharArray()) {
                if (blockType == '0') {
                    addObstacle(x, y);
                }
                x++;
            }
            y++;
        }
    }

    public String getWinnerName() {
        if (winner == 1) return player1Name;
        if (winner == 2) return player2Name;
        return "";
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void addObstacle(int x, int y) {
        obstacles.add(new Point(x, y));
    }

    public void update() {
        if (gameOver) return;

        // Update biker positions
        biker1.update();
        biker2.update();

        // Check for collisions and boundaries
        boolean biker1Collision = checkBikerCollision(biker1, biker2.getTrail());
        boolean biker2Collision = checkBikerCollision(biker2, biker1.getTrail());

        // Update game state
        if (biker1Collision && biker2Collision) {
            gameOver = true;
            winner = 3; // Draw
        } else if (biker1Collision) {
            gameOver = true;
            winner = 2; // Model.Biker 2 wins
        } else if (biker2Collision) {
            gameOver = true;
            winner = 1; // Model.Biker 1 wins
        }
    }

    private boolean checkBikerCollision(Biker biker, ArrayList<Point> otherTrail) {
        return biker.checkCollision(otherTrail, obstacles) || biker.checkBounds(width, height);
    }

    // Control methods for Model.Biker 1
    public void turnBiker1Left() {
        if (!gameOver) biker1.turnLeft();
    }

    public void turnBiker1Right() {
        if (!gameOver) biker1.turnRight();
    }

    // Control methods for Model.Biker 2
    public void turnBiker2Left() {
        if (!gameOver) biker2.turnLeft();
    }

    public void turnBiker2Right() {
        if (!gameOver) biker2.turnRight();
    }

    // Getters for game state
    public boolean isGameOver() {
        return gameOver;
    }

    public int getWinner() {
        return winner;
    }

    public Biker getBiker1() {
        return biker1;
    }

    public Biker getBiker2() {
        return biker2;
    }

    public ArrayList<Point> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Method to reset the game
    public void reset() {
        // Create new bikers with same settings
        obstacles.clear();
        try {
            loadLevel();
        } catch (IOException ignored) {
        }
        this.biker1 = new Biker(3, height / 2, 0, biker1.getColor());  // Starts from left, moving right
        this.biker2 = new Biker(width - 4, height / 2, 2, biker2.getColor());   // Starts from right, moving left

        this.gameOver = false;
        this.winner = 0;
    }
}