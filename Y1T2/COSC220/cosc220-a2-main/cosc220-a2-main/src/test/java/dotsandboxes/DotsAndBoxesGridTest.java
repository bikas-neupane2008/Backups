package dotsandboxes;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DotsAndBoxesGridTest {
    /*
     * Because Test classes are classes, they can have fields, and can have static fields.
     * This field is a logger. Loggers are like a more advanced println, for writing messages out to the console or a log file.
     */
    private static final Logger logger = LogManager.getLogger(DotsAndBoxesGridTest.class);

    /*
     * Tests are functions that have an @Test annotation before them.
     * The typical format of a test is that it contains some code that does something, and then one
     * or more assertions to check that a condition holds.
     */

    /**
     * Testing that the box is not detected as complete as the sides are filled in.
     * @result boxComplete will return false
     */
    @Test
    public void boxCompleteDetectsIncompleteBoxes() {
        DotsAndBoxesGrid testGrid = new DotsAndBoxesGrid(5, 5, 2);
        assertFalse(testGrid.boxComplete(0, 0));
        testGrid.drawHorizontal(0,0,0);
        assertFalse(testGrid.boxComplete(0, 0));
        testGrid.drawHorizontal(0,1,0);
        assertFalse(testGrid.boxComplete(0, 0));
        testGrid.drawVertical(0,0,0);
        assertFalse(testGrid.boxComplete(0, 0));
    }

    /**
     * Testing that the box is detected as complete if all the sides are filled in.
     * @result boxComplete will return true
     */
    @Test
    public void boxCompleteDetectsCompletedBoxes() {
        DotsAndBoxesGrid testGrid = new DotsAndBoxesGrid(5, 5, 2);
        testGrid.drawHorizontal(0,0,0);
        testGrid.drawHorizontal(0,1,0);
        testGrid.drawVertical(0,0,0);
        testGrid.drawVertical(1, 0, 0);
        assertTrue(testGrid.boxComplete(0, 0));
    }

    /**
     * Testing that it detects if a line is drawn on top of an existing line.
     * @result IllegalArgumentException will be thrown
     */
    @Test
    public void drawMethodsDetectRedrawnLines() {
        DotsAndBoxesGrid testGrid = new DotsAndBoxesGrid(5, 5, 2);
        testGrid.drawHorizontal(0,0,0);
        assertThrows(IllegalStateException.class, () -> {
            testGrid.drawHorizontal(0,0,0);
        });

        testGrid.drawVertical(0,0,0);
        assertThrows(IllegalStateException.class, () -> {
            testGrid.drawVertical(0,0,0);
        });
    }
}
