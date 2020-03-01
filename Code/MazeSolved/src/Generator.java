import java.awt.Color;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Generator extends Explorer {

    public Generator(MazeCanvas mc, Maze m) {
        super(mc, m, new Color(161, 224, 255), null);
    }
    
    protected boolean onEnterCell(Cell cell, Side side) {
        _mc.step(10);
        cell.removeWall(side);
        super.onEnterCell(cell, side);
        return false;
    }
    
    protected List<Side> onGetNextSteps(Cell cell) {
        List<Side> walls = cell.getWalls();
        walls = this.shuffle(walls);
        return walls;
    }
    
    protected void onStepForward(Cell cell, Side side) {
        super.onStepForward(cell, side);
        cell.removeWall(side);
    }
}
