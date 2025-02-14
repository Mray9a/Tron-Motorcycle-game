package Model;

import java.awt.*;
import java.util.ArrayList;

public class Biker {
    private int x;
    private int y;           // Position coordinates
    private int speed;          // Movement speed
    private int direction;         // 0: right, 1: down, 2: left, 3: up
    private boolean isAlive;
    private final Color color;           // Model.Biker's color
    private final ArrayList<Point> trail; // Light trail coordinates

    public Biker(int startX, int startY, int startDirection, Color bikerColor) {
        this.x = startX;
        this.y = startY;
        this.direction = startDirection;
        this.speed = 1;         // Default speed
        this.isAlive = true;
        this.color = bikerColor;
        this.trail = new ArrayList<>();
        trail.add(new Point(x, y)); // Add starting position to trail
    }

    public void update() {
        if (!isAlive) return;

        // Update position based on direction
        switch (direction) {
            case 0: // Right
                x += speed;
                break;
            case 1: // Down
                y += speed;
                break;
            case 2: // Left
                x -= speed;
                break;
            case 3: // Up
                y -= speed;
                break;
        }

        // Add new position to trail
        trail.add(new Point(x, y));
    }

    // Turn methods
    public void turnLeft() {
        direction = (direction + 3) % 4;
    }

    public void turnRight() {
        direction = (direction + 1) % 4;
    }

    // Collision detection with all elements
    public boolean checkCollision(ArrayList<Point> otherTrail, ArrayList<Point> obstacles) {
        Point currentPos = new Point(x, y);

        // Check collision with obstacles
        for (Point obstacle : obstacles) {
            if (currentPos.equals(obstacle)) {
                isAlive = false;
                return true;
            }
        }

        // Check collision with other trail
        for (Point p : otherTrail) {
            if (currentPos.equals(p)) {
                isAlive = false;
                return true;
            }
        }

        // Check self-collision (excluding the last few points)
        for (int i = 0; i < trail.size() - 3; i++) {
            if (currentPos.equals(trail.get(i))) {
                isAlive = false;
                return true;
            }
        }

        return false;
    }

    // Check if position is out of bounds
    public boolean checkBounds(int mapWidth, int mapHeight) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
            isAlive = false;
            return true;
        }
        return false;
    }

    // Getters and setters
    public Point getPos() {
        return new Point(x, y);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Point> getTrail() {
        return trail;
    }

    public int getDirection() {
        return direction;
    }

}