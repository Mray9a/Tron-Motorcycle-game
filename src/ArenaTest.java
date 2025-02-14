
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.*;
import Model.*;

public class ArenaTest {
    private Arena arena;
    private final int WIDTH = 100;
    private final int HEIGHT = 80;

    @Before
    public void setUp() {
        arena = new Arena(WIDTH, HEIGHT, "Player1", "Player2", Color.BLUE, Color.RED);
    }

    @Test
    public void testInitialization() {
        assertEquals(WIDTH, arena.getWidth());
        assertEquals(HEIGHT, arena.getHeight());
        assertEquals("Player1", arena.getPlayer1Name());
        assertEquals("Player2", arena.getPlayer2Name());
        assertFalse(arena.isGameOver());
        assertEquals(0, arena.getWinner());

        Biker biker1 = arena.getBiker1();
        Biker biker2 = arena.getBiker2();
        assertEquals(new Point(3, HEIGHT/2), biker1.getPos());
        assertEquals(new Point(WIDTH-4, HEIGHT/2), biker2.getPos());
        assertEquals(0, biker1.getDirection());
        assertEquals(2, biker2.getDirection());
    }

    @Test
    public void testBikerMovement() {
        arena.update();
        assertEquals(new Point(4, HEIGHT/2), arena.getBiker1().getPos());
        assertEquals(new Point(WIDTH-5, HEIGHT/2), arena.getBiker2().getPos());
    }

    @Test
    public void testTurning() {
        arena.turnBiker1Left();
        arena.turnBiker2Right();
        assertEquals(3, arena.getBiker1().getDirection());
        assertEquals(3, arena.getBiker2().getDirection());
    }

    @Test
    public void testCollisionWithWall() {
        // Move biker1 to left wall
        for(int i = 0; i < 5; i++) {
            arena.getBiker1().turnLeft();
            arena.getBiker1().turnLeft();
            arena.update();
        }
        assertTrue(arena.isGameOver());
        assertEquals(2, arena.getWinner());
        assertEquals("Player2", arena.getWinnerName());
    }

    @Test
    public void testHeadOnCollision() {
        // Move bikers toward each other
        for(int i = 0; i < WIDTH/2; i++) {
            arena.update();
        }
        assertTrue(arena.isGameOver());
        assertEquals(3, arena.getWinner());
        assertEquals("", arena.getWinnerName());
    }

    @Test
    public void testReset() {
        arena.update();
        arena.update();
        arena.reset();

        assertFalse(arena.isGameOver());
        assertEquals(0, arena.getWinner());
        assertEquals(new Point(3, HEIGHT/2), arena.getBiker1().getPos());
        assertEquals(new Point(WIDTH-4, HEIGHT/2), arena.getBiker2().getPos());
    }

    @Test
    public void testNoMovementAfterGameOver() {
        // Force game over
        while(!arena.isGameOver()) {
            arena.update();
        }

        Point biker1Pos = arena.getBiker1().getPos();
        Point biker2Pos = arena.getBiker2().getPos();

        arena.update();
        assertEquals(biker1Pos, arena.getBiker1().getPos());
        assertEquals(biker2Pos, arena.getBiker2().getPos());
    }

    @Test
    public void testNoTurningAfterGameOver() {
        // Force game over
        while(!arena.isGameOver()) {
            arena.update();
        }

        int biker1Dir = arena.getBiker1().getDirection();
        int biker2Dir = arena.getBiker2().getDirection();

        arena.turnBiker1Left();
        arena.turnBiker2Right();

        assertEquals(biker1Dir, arena.getBiker1().getDirection());
        assertEquals(biker2Dir, arena.getBiker2().getDirection());
    }

    @Test
    public void testObstacleCollection() {
        arena.addObstacle(10, 10);
        assertTrue(arena.getObstacles().contains(new Point(10, 10)));
    }
}