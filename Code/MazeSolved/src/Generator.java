import java.util.List;
import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Generator extends Explorer {
    
    public Generator(Maze m, MazeCanvas mc) {
        super(m, mc);
    }
    
    @Override
    protected boolean onEnterCell(Cell cell, Side fromSide) {
        cell.removeWall(fromSide);
        return super.onEnterCell(cell, fromSide);
    }
    
    @Override
    protected List<Side> onGetNextSteps(Cell cell) {
        return shuffle(cell.getWalls());
    }
    
    @Override
    protected void onStepForward(Cell cell, Side toSide) {
        cell.removeWall(toSide);
        super.onStepForward(cell, toSide);
    }
}
