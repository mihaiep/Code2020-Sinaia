import java.awt.Color;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Solver extends Explorer {
    
    // Strong red color marking the exploration path
    protected static final Color _pathColor = new Color(255, 48, 48);
    // Light-ish yellow color marking the backtrack path
    private static final Color _trackColor = new Color(240, 240, 161);

    public Solver(MazeCanvas mc, Maze m) {
        super(mc, m);
        _exploreColor = _pathColor;
    }
    
    @Override
    protected boolean onEnterCell(Cell cell, Side fromSide) {
        super.onEnterCell(cell, fromSide);
        return (cell instanceof ExitCell);
    }
    
    @Override
    protected void onStepBack(boolean done, Cell cell, Side fromSide) {
        if (!done) {
            _mc.drawPath(cell.getRow(), cell.getCol(), fromSide, _trackColor);
        }
    }
    
    @Override
    protected void onExitCell(boolean done, Cell cell, Side toSide) {
        if (!done) {
            int row = cell.getRow();
            int col = cell.getCol();
            _mc.drawCenter(row, col, _trackColor);
            if (toSide != Side.Center) {
                _mc.drawPath(row, col, toSide, _trackColor);
            }
        }
    }
}
