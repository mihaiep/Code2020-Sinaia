import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import graphics.MazeCanvas.Side;
import helpers.Order;
import helpers.OrderedRunner;
import helpers.WClass;
import helpers.Maze;
import helpers.Cell;
import helpers.EdgeCell;
import helpers.MazeCanvas;
import helpers.Generator;
import helpers.Solver;

@RunWith(OrderedRunner.class)
@Order(order = 500)
public class MazeCheckpoint5a {
    
    @Test
    @Order(order = 501)
    public void testCell_Class() {
        WClass.checkClass("Cell",
                "public java.util.List Cell.getPaths()");
    }
    
    @Test
    @Order(order = 502)
    public void testCell_getPaths() {
        MazeCanvas mc = new MazeCanvas(12, 18, 24);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.initialize();
        Generator g = Generator.newInstance(mc, m);
        g.run();
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                Cell cell = m.getCell(r, c);
                List<Side> paths = cell.getPaths();
                mc.assertPathsMatch(r, c, paths.toArray(new Side[paths.size()]));
            }
        }
    }
    
    @Test
    @Order(order = 503)
    public void testEdgeCell_Class() {
        WClass.checkClass("EdgeCell extends ShadedCell extends Cell",
                "public java.util.List EdgeCell.getPaths()");
    }
    
    @Test
    @Order(order = 504)
    public void testEdgeCell_getPaths() {
        MazeCanvas mc = new MazeCanvas(9, 12, 32);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.initialize();
        EdgeCell ec = (EdgeCell)m.getCell(0, 0);
        mc.assertPathsMatch(0, 0);
        ec.removeWall(Side.Bottom);
        mc.assertPathsMatch(0, 0, Side.Bottom);
        ec = (EdgeCell)m.getCell(8, 6);
        mc.assertPathsMatch(8, 6);
        ec.removeWall(Side.Left);
        ec.removeWall(Side.Top);
        ec.removeWall(Side.Right);
        mc.assertPathsMatch(8, 6, Side.Left, Side.Top, Side.Right);
    }
    
    @Test
    @Order(order = 505)
    public void testSolver_Class() {
        WClass.checkClass("Solver",
                "public Solver(graphics.MazeCanvas,Maze)",
                "* java.util.List *.shuffle(java.util.List)",
                "* graphics.MazeCanvas$Side *.getOpposite(graphics.MazeCanvas$Side)",
                "* boolean *.run(Cell,graphics.MazeCanvas$Side)",
                "* boolean *.run()");
    }
    
    @Test
    @Order(order = 506)
    public void testSolver_shuffle() {
        MazeCanvas mc = new MazeCanvas(12, 18, 24);
        Maze m = Maze.newInstance(mc);
        Solver s = Solver.newInstance(mc, m);
        Side[] sides = {Side.Top, Side.Bottom, Side.Left, Side.Right, Side.Center};
        List<Side> shuffledList = s.shuffle(new ArrayList<Side>(Arrays.asList(sides)));
        assertTrue("Incorrect null result from Solver.shuffle()", shuffledList != null);
        boolean shuffled = false;
        for (int i = 0; !shuffled && i < sides.length; i++) {
            int j = shuffledList.indexOf(sides[i]);
            shuffled = (i != j);
        }
        assertTrue("Solver.shuffle() returned unchanged list.", shuffled);
    }
    
    @Test
    @Order(order = 507)
    public void testSolver_getOpposite() {
        MazeCanvas mc = new MazeCanvas(12, 18, 24);
        Maze m = Maze.newInstance(mc);
        Solver s = Solver.newInstance(mc, m);
        Side[] sides = {Side.Top, Side.Bottom, Side.Left, Side.Right, Side.Center};
        Side[] oppSides = {Side.Bottom, Side.Top, Side.Right, Side.Left, Side.Center};
        for (int i = 0; i < sides.length; i++) {
            Side result = s.getOpposite(sides[i]);
            assertTrue(
                    String.format("Incorrect opposite of %s; Actual: %s; Expected: %s", 
                            sides[i], result, oppSides[i]),
                    result == oppSides[i]);
        }
    }
}
