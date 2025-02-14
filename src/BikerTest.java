import Model.Biker;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.*;
import java.util.ArrayList;

public class BikerTest {
    private Biker biker;
    private final int START_X = 100;
    private final int START_Y = 100;
    private final int MAP_WIDTH = 80;
    private final int MAP_HEIGHT = 60;

    @Before
    public void setUp() {
        biker = new Biker(START_X, START_Y, 0, Color.BLUE);
    }

    @Test
    public void testInitialization() {
        assertEquals(new Point(START_X, START_Y), biker.getPos());
        assertEquals(0, biker.getDirection());
        assertTrue(biker.isAlive());
        assertEquals(Color.BLUE, biker.getColor());
        assertEquals(1, biker.getTrail().size());
    }

    @Test
    public void testMovement() {
        biker.update(); // Move right
        assertEquals(new Point(START_X + 1, START_Y), biker.getPos());

        biker.turnRight(); // Face down
        biker.update();
        assertEquals(new Point(START_X + 1, START_Y + 1), biker.getPos());

        biker.turnRight(); // Face left
        biker.update();
        assertEquals(new Point(START_X, START_Y + 1), biker.getPos());

        biker.turnRight(); // Face up
        biker.update();
        assertEquals(new Point(START_X, START_Y), biker.getPos());
    }

    @Test
    public void testTurning() {
        assertEquals(0, biker.getDirection()); // Initially facing right

        biker.turnLeft();
        assertEquals(3, biker.getDirection()); // Facing up

        biker.turnRight();
        assertEquals(0, biker.getDirection()); // Facing right again

        biker.turnRight();
        assertEquals(1, biker.getDirection()); // Facing down
    }

    @Test
    public void testTrailGeneration() {
        assertEquals(1, biker.getTrail().size());

        for(int i = 0; i < 3; i++) {
            biker.update();
        }

        assertEquals(4, biker.getTrail().size());
        assertEquals(new Point(START_X, START_Y), biker.getTrail().get(0));
        assertEquals(new Point(START_X + 1, START_Y), biker.getTrail().get(1));
        assertEquals(new Point(START_X + 2, START_Y), biker.getTrail().get(2));
        assertEquals(new Point(START_X + 3, START_Y), biker.getTrail().get(3));
        assertEquals(new Point(START_X + 3, START_Y), biker.getPos());
    }

    @Test
    public void testObstacleCollision() {
        ArrayList<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(START_X + 1, START_Y));

        biker.update();
        assertTrue(biker.checkCollision(new ArrayList<>(), obstacles));
        assertFalse(biker.isAlive());
    }

    @Test
    public void testTrailCollision() {
        ArrayList<Point> otherTrail = new ArrayList<>();
        otherTrail.add(new Point(START_X + 1, START_Y));

        biker.update();
        assertTrue(biker.checkCollision(otherTrail, new ArrayList<>()));
        assertFalse(biker.isAlive());
    }

    @Test
    public void testSelfCollision() {
        // Create a square pattern
        biker.update();
        biker.turnRight();
        biker.update();
        biker.turnRight();
        biker.update();
        biker.turnRight();
        biker.update();

        assertTrue(biker.checkCollision(new ArrayList<>(), new ArrayList<>()));
        assertFalse(biker.isAlive());
    }

    @Test
    public void testBoundaryCollision() {
        // Test right boundary
        for(int i = 0; i < MAP_WIDTH; i++) {
            biker.update();
        }
        assertTrue(biker.checkBounds(MAP_WIDTH, MAP_HEIGHT));
        assertFalse(biker.isAlive());

        // Test with new biker for left boundary
        biker = new Biker(START_X, START_Y, 2, Color.BLUE);
        for(int i = 0; i < START_X + 1; i++) {
            biker.update();
        }
        assertTrue(biker.checkBounds(MAP_WIDTH, MAP_HEIGHT));
    }

    @Test
    public void testDeadBikerDoesNotUpdate() {
        biker.checkBounds(-1, -1); // Kill the biker
        Point lastPosition = biker.getPos();
        biker.update();
        assertEquals(lastPosition, biker.getPos());
    }
}