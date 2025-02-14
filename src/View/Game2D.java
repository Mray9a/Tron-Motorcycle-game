package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game2D extends JPanel {
    private static final int CELL_SIZE = 20; // Size of each grid cell
    private final ArrayList<Point> obstacles = new ArrayList<>();
    private Color biker1Color;
    private Color biker2Color;
    private ArrayList<Point> biker1trail;
    private ArrayList<Point> biker2trail;
    private Point biker1pos;
    private Point biker2pos;

    public Game2D(int width, int height) {
        setPreferredSize(new Dimension(width * CELL_SIZE, height * CELL_SIZE));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
    }

    public void setColors(Color color1, Color color2) {
        biker1Color = color1;
        biker2Color = color2;
    }

    public void setObstacles(ArrayList<Point> obstacles) {
        this.obstacles.clear();
        this.obstacles.addAll(obstacles);
    }

    public void updateBikersData(Point biker1pos, Point biker2pos, ArrayList<Point> trail1, ArrayList<Point> trail2) {
        biker1trail = trail1;
        biker2trail = trail2;
        this.biker1pos = biker1pos;
        this.biker2pos = biker2pos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw obstacles
        g2d.setColor(Color.WHITE);
        for (Point obstacle : obstacles) {
            g2d.fillRect(
                    obstacle.x * CELL_SIZE,
                    obstacle.y * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
            );
        }

        // Draw Model.Biker 1's trail
        drawBikerTrail(g2d, biker1trail, biker1Color);

        // Draw Model.Biker 2's trail
        drawBikerTrail(g2d, biker2trail, biker2Color);

        // Draw current positions of bikers
        drawBikerPosition(g2d, biker1pos, biker1Color);
        drawBikerPosition(g2d, biker2pos, biker2Color);

    }

    private void drawBikerTrail(Graphics2D g2d, ArrayList<Point> trail, Color color) {
        if (trail == null) return;
        g2d.setColor(color);
        for (int i = 0; i < trail.size() - 1; i++) {
            g2d.fillRect(
                    trail.get(i).x * CELL_SIZE,
                    trail.get(i).y * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
            );
        }
    }
/*
    private void drawBikerPosition(Graphics2D g2d, Point bikerPos, Color color) {
        if (bikerPos == null) return;
        g2d.setColor(Color.DARK_GRAY);
        Image img = Toolkit.getDefaultToolkit().getImage("src/moto.png");

        // Calculate the position for the image
        int x = bikerPos.x * CELL_SIZE;
        int y = bikerPos.y * CELL_SIZE;

        // Draw the image
        g2d.drawImage(img, x, y, CELL_SIZE, CELL_SIZE, null);
    }*/

    private void drawBikerPosition(Graphics2D g2d, Point biker, Color c) {
        if (biker == null) return;
        // Draw yellow box for current biker position
        g2d.setColor(c.darker());
        g2d.fillArc(
                biker.x * CELL_SIZE,
                biker.y * CELL_SIZE,
                CELL_SIZE,
                CELL_SIZE,
                0,
                360
        );
    }

    public void drawGameOverMessage(Graphics g2d, int winner, String player1Name, String player2Name) {
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        String message = "Game Over - ";
        switch (winner) {
            case 1:
                message += player1Name + " Wins!";
                break;
            case 2:
                message += player2Name + " Wins!";
                break;
            case 3:
                message += "Draw!";
                break;
        }

        // Center the message
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = getHeight() / 2;

        g2d.drawString(message, x, y);
    }

}