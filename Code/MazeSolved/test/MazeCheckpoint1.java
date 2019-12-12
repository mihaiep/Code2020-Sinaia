import org.junit.Test;

import helpers.MazeCanvasEx;
import helpers.MazeTest;
import helpers.WMaze;

public class MazeCheckpoint1 {

    @Test
    public void testMaze_Class() {
        MazeTest.checkClass("Maze",
                "public Maze(graphics.MazeCanvas)", 
                "public void Maze.genSnake()");
    }

    @Test
    public void testMaze_genSnake() {
        MazeCanvasEx mct = new MazeCanvasEx(6, 9, 32);
        mct.open();
        WMaze wm = new WMaze(mct);
        wm.genSnake();
        mct.check_getSnake();
        mct.close();
    }
}
