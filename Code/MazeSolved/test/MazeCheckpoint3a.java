import org.junit.Test;
import org.junit.runner.RunWith;

import helpers.Maze;
import helpers.MazeCanvas;
import helpers.Order;
import helpers.OrderedRunner;
import helpers.WClass;
import helpers.Cell;
import helpers.BlockCell;

@RunWith(OrderedRunner.class)
@Order(order = 300)
public class MazeCheckpoint3a {

    @Test
    @Order(order = 301)
    public void testBlockCell_Class() {
        WClass.checkClass("BlockCell extends ShadedCell extends Cell",
                "public BlockCell(graphics.MazeCanvas,int,int)");
    }
    
    @Test
    @Order(order = 302)
    public void testBlockCell_initialize() {
        MazeCanvas mc = new MazeCanvas(12, 18, 18);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.initialize();
        int expBlockCells = (int)((mc.getRows()-1) * (mc.getCols()-1) * .05);
        int actBlockCells = 0;
        for (int r = 1; r < mc.getRows()-1; r++) {
            for (int c = 1; c < mc.getCols()-1; c++) {
                Cell cell = m.getCell(r, c);
                if (cell instanceof BlockCell) {
                    actBlockCells++;
                }
            }
        }
        // with the naive logic to generate BlockCells (Math.random < 0.05), in extreme cases
        // we may have 0 BlockCells or possibly more than 5% of cells.
        mc.assertPause(
                String.format("Missing or wrong count of BlockCells. Actual: %d; Expected: <%d", 
                        actBlockCells,
                        expBlockCells),
                actBlockCells <= expBlockCells);
        mc.close();
    }
}
