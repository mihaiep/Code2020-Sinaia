import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import graphics.MazeCanvas.Side;
import helpers.Order;
import helpers.OrderedRunner;

import helpers.Maze;
import helpers.Cell;
import helpers.BlockCell;
import helpers.MazeCanvas;
import helpers.Generator;

@RunWith(OrderedRunner.class)
@Order(order = 420)
public class MazeCheckpoint4b {

    private static MazeCanvas mc;
    private static Maze m;
    private static Generator g;
    
    @BeforeClass
    public static void init() {
        mc = new MazeCanvas(12, 18, 24);
        mc.open();
        m = Maze.newInstance(mc);
        m.initialize();
        g = Generator.newInstance(mc, m);
        mc.assertPause("Generator.run() returned true. Expected: false", !g.run());
    }
    
    @AfterClass
    public static void terminate() {
        mc.close();
    }
    
    @Test
    @Order(order = 421)
    public void testGenerator_allVisited() {
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                Cell cell = m.getCell(r, c);
                mc.assertPause(r, c, "Cell is not visited", cell.getVisited());
            }
        }
    }
    
    @Test
    @Order(order = 422)
    public void testGenerator_blockCell_walled() {
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                Cell cell = m.getCell(r, c);
                if (cell instanceof BlockCell) {
                    cell.assertWalls(Side.Top, Side.Left, Side.Bottom, Side.Right);
                    mc.assertWalls(r, c, Side.Top, Side.Left, Side.Bottom, Side.Right);
                }
            }
        }
    }
    
    @Test
    @Order(order = 423)
    public void testGenerator_wallsMatch() {
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                mc.assertWallsMatch(r, c);
            }
        }
    }
    
    @Test
    @Order(order = 424)
    public void testGenerator_complete() {
        mc.assertGenerated();
    }
}
