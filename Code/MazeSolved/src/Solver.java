import java.awt.Color;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Solver extends Explorer {
    
    public Solver(MazeCanvas mc, Maze m) {
        super(mc, m, new Color(255, 48, 48), new Color(240, 240, 161));
    }
    
    protected boolean onEnterCell(Cell cell, Side side) {
        _mc.step(10);
        super.onEnterCell(cell, side);
        return (cell instanceof ExitCell);
    }
    
    protected List<Side> onGetNextSteps(Cell cell) {
        return shuffle(cell.getPaths());
    }
    
}
