import org.junit.Test;
import org.junit.runner.RunWith;

import helpers.MazeCanvas;
import helpers.Order;
import helpers.OrderedRunner;
import helpers.WClass;
import helpers.Maze;

@RunWith(OrderedRunner.class)
@Order(order = 100)
public class MazeCheckpoint1 {

    @Test
    @Order(order = 101)
    public void testMaze_Class() {
        WClass.checkClass("Maze",
                "public Maze(graphics.MazeCanvas)", 
                "public void Maze.genSnake()");
    }

    @Test
    @Order(order = 102)
    public void testMaze_genSnake() {
        MazeCanvas mc = new MazeCanvas(6, 9, 32);
        mc.open();
        Maze m = Maze.newInstance(mc);
        m.genSnake();
        mc.assertSnake();
        mc.close();
    }
}
