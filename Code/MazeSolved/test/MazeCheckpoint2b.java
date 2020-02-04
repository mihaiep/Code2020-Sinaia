import org.junit.Test;
import org.junit.runner.RunWith;

import helpers.Maze;
import helpers.MazeCanvas;
import helpers.Order;
import helpers.OrderedRunner;
import helpers.WClass;
import helpers.Cell;
import helpers.EdgeCell;
import helpers.BlockCell;
import helpers.EntryCell;
import helpers.ExitCell;

@RunWith(OrderedRunner.class)
@Order(order = 220)
public class MazeCheckpoint2b {

    @Test
    @Order(order = 221)
    public void testMaze_Class() {
        WClass.checkClass("Maze",
                "public Cell Maze.getCell(int,int)",
                "public void Maze.initialize()");
    }
    
    @Test
    @Order(order = 222)
    public void testMaze_initialize() {
        MazeCanvas mc = new MazeCanvas(6, 9, 32);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.initialize();
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                if (r == 0 || c == 0 || r == mc.getRows()-1 || c == mc.getCols()-1) {
                    mc.assertShade(r, c, null);
                }
            }
        }
        mc.close();
    }
    
    @Test
    @Order(order = 223)
    public void testMaze_getCell() {
        MazeCanvas mc = new MazeCanvas(6, 9, 32);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.initialize();
        for (int r = 0; r < mc.getRows(); r++) {
            for (int c = 0; c < mc.getCols(); c++) {
                Cell cell = m.getCell(r, c);
                if (r == 0 || c == 0 || r == mc.getRows()-1 || c == mc.getCols()-1) {
                    cell.assertType(EdgeCell.class, EntryCell.class, ExitCell.class);
                } else {
                    cell.assertType(Cell.class, BlockCell.class);
                }
            }
        }
        mc.close();
    }
}
