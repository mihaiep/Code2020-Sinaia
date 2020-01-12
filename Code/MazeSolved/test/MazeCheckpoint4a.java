import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import graphics.MazeCanvas.Side;
import helpers.Order;
import helpers.OrderedRunner;
import helpers.WClass;
import helpers.Cell;
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
                String.format("Wrong Left neighbor: [%d, %d]; Expected [0, 1]", n.getRow(), n.getCol()),
                n.getRow()==0 && n.getCol()==1);
        n = m.getNeighbor(c, Side.Bottom);
        mc.assertPause(0, 0,
                String.format("Wrong Bottom neighbor: [%d, %d]; Expected [1, 0]", n.getRow(), n.getCol()),
                n.getRow()==1 && n.getCol()==0);
        n = m.getNeighbor(c, Side.Top);
        if (n != null) {
            mc.assertPause(0, 0,
                    String.format("Wrong Bottom neighbor: [%d, %d]; Expected (null)", n.getRow(), n.getCol()),
                    n == null);
        }
        c = m.getCell(8, 9);
        n = m.getNeighbor(c, Side.Left);
        mc.assertPause(8, 9,
                String.format("Wrong Left neighbor: [%d, %d]; Expected [8, 8]", n.getRow(), n.getCol()),
                n.getRow()==8 && n.getCol()==8);
        n = m.getNeighbor(c, Side.Top);
        mc.assertPause(8, 9,
                String.format("Wrong Top neighbor: [%d, %d]; Expected [7, 9]", n.getRow(), n.getCol()),
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
}
