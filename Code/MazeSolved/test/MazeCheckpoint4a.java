import org.junit.Test;

import org.junit.Assert;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;

import graphics.MazeCanvas.Side;
import helpers.Order;
import helpers.OrderedRunner;
import helpers.WClass;
import helpers.Cell;
import helpers.Generator;
import helpers.BlockCell;
import helpers.Maze;
import helpers.MazeCanvas;

@RunWith(OrderedRunner.class)
@Order(order = 400)
public class MazeCheckpoint4a {

    @Test
    @Order(order = 401)
    public void testCell_Class_getSetVisited() {
        WClass.checkClass("Cell",
                "public boolean Cell.getVisited()",
                "public void Cell.setVisited(boolean)");
    }
    
    @Test
    @Order(order = 402)
    public void testCell_getSetVisited() {
        MazeCanvas mc = new MazeCanvas(6, 9, 32);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.initialize();
        Cell c = m.getCell(0, 0);
        Assert.assertFalse("Incorrect default value 'true' for Cell visited flag.", c.getVisited());
        c.setVisited(true);
        Assert.assertTrue("Failed to set Cell visited flag to 'true'.", c.getVisited());
        c.setVisited(false);
        Assert.assertFalse("Failed to reset Cell visited flag to 'false'.", c.getVisited());
    }
    
    @Test
    @Order(order = 402)
    public void testBlockCell_Class_getVisited() {
        WClass.checkClass("BlockCell extends ShadedCell extends Cell",
                "public boolean BlockCell.getVisited()");
    }
    
    @Test
    @Order(order = 403)
    public void testBlockCell_getVisited() {
        MazeCanvas mc = new MazeCanvas(16, 18, 20);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.initialize();
        
        // locate a BlockCell in the maze to use for testing
        BlockCell bc = null;
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                Cell cell = m.getCell(r, c);
                if (cell instanceof BlockCell) {
                    bc = (BlockCell) cell;
                }
            }
        }
        mc.assertPause("No BlockCell in the maze.", bc != null);
        Assert.assertTrue("Incorrect default value 'false' for BlockCell visited flag.", bc.getVisited());
        bc.setVisited(false);
        Assert.assertTrue("BlockCell visited flag should not be changeable to 'true'.", bc.getVisited());
    }
    
    @Test
    @Order(order = 404)
    public void testMaze_Class_getNeighbor() {
        WClass.checkClass("Maze",
                "public Cell Maze.getNeighbor(Cell,graphics.MazeCanvas$Side)");
    }
    
    @Test
    @Order(order = 405)
    public void testMaze_getNeighbor() {
        MazeCanvas mc = new MazeCanvas(16, 18, 20);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.initialize();
        Cell c = m.getCell(0, 0);
        Cell n = m.getNeighbor(c, Side.Right);
        mc.assertPause(0, 0, 
                "Wrong Left neighbor; Expected [0, 1]",
                n.getRow()==0 && n.getCol()==1);
        n = m.getNeighbor(c, Side.Bottom);
        mc.assertPause(0, 0,
                "Wrong Bottom neighbor; Expected [1, 0]",
                n.getRow()==1 && n.getCol()==0);
        n = m.getNeighbor(c, Side.Top);
        if (n != null) {
            mc.assertPause(0, 0,
                    "Wrong Bottom neighbor; Expected (null)",
                    n == null);
        }
        c = m.getCell(8, 9);
        n = m.getNeighbor(c, Side.Left);
        mc.assertPause(8, 9,
                "Wrong Left neighbor; Expected [8, 8]",
                n.getRow()==8 && n.getCol()==8);
        n = m.getNeighbor(c, Side.Top);
        mc.assertPause(8, 9,
                "Wrong Top neighbor; Expected [7, 9]",
                n.getRow()==7 && n.getCol()==9);
    }
    
    @Test
    @Order(order = 406)
    public void testGenerator_Class() {
        WClass.checkClass("Generator",
                "public Generator(graphics.MazeCanvas,Maze)",
                "* java.util.List *.shuffle(java.util.List)",
                "* graphics.MazeCanvas$Side *.getOpposite(graphics.MazeCanvas$Side)",
                "* boolean *.run(Cell,graphics.MazeCanvas$Side)",
                "* boolean *.run()");
    }
    
    @Test
    @Order(order = 407)
    public void testGenerator_shuffle() {
        MazeCanvas mc = new MazeCanvas(12, 18, 24);
        Maze m = Maze.newInstance(mc);
        Generator g = Generator.newInstance(mc, m);
        Side[] sides = {Side.Top, Side.Bottom, Side.Left, Side.Right, Side.Center};
        List<Side> shuffledList = g.shuffle(new ArrayList<Side>(Arrays.asList(sides)));
        assertTrue("Incorrect null result from Generator.shuffle()", shuffledList != null);
        boolean shuffled = false;
        for (int i = 0; !shuffled && i < sides.length; i++) {
            int j = shuffledList.indexOf(sides[i]);
            shuffled = (i != j);
        }
        assertTrue("Generator.shuffle() returned unchanged list.", shuffled);
    }
    
    @Test
    @Order(order = 408)
    public void testGenerator_getOpposite() {
        MazeCanvas mc = new MazeCanvas(12, 18, 24);
        Maze m = Maze.newInstance(mc);
        Generator g = Generator.newInstance(mc, m);
        Side[] sides = {Side.Top, Side.Bottom, Side.Left, Side.Right, Side.Center};
        Side[] oppSides = {Side.Bottom, Side.Top, Side.Right, Side.Left, Side.Center};
        for (int i = 0; i < sides.length; i++) {
            Side result = g.getOpposite(sides[i]);
            assertTrue(
                    String.format("Incorrect opposite of %s; Actual: %s; Expected: %s", 
                            sides[i], result, oppSides[i]),
                    result == oppSides[i]);
        }
    }
}
