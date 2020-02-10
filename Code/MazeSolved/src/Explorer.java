import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Explorer {

    protected MazeCanvas _mc;
    protected Maze _m;
    protected Color _fwdPathColor;
    protected Color _bktPathColor;
    
    public Explorer(MazeCanvas mc, Maze m, Color fwdPathColor, Color bktPathColor) {
        _mc = mc;
        _m = m;
        _fwdPathColor = fwdPathColor;
        _bktPathColor = bktPathColor;
    }
    
    protected List<Side> shuffle(List<Side> sides) {
        List<Side> shuffled = new ArrayList<Side>();
        
        while(sides.size() > 0) {
            int i = (int)(Math.random() * sides.size());
            shuffled.add(sides.remove(i));
        }
        
        return shuffled;
    }
    
    protected Side getOpposite(Side side) {
        if (side == Side.Top) {
            return Side.Bottom;
        } else if (side == Side.Bottom) {
            return Side.Top;
        } else if (side == Side.Left) {
            return Side.Right;
        } else if (side == Side.Right) {
            return Side.Left;
        } else {
            return Side.Center;
        }
    }
    
    public boolean run(Cell cell, Side fromSide) {
        cell.setVisited(true);
        boolean done = onEnterCell(cell, fromSide);
        List<Side> nextSteps = onGetNextSteps(cell);
       
        for (Side path : nextSteps) {
            if (!done) {
                Cell neighbor = _m.getNeighbor(cell, path);
                if (!neighbor.getVisited()) {
                    onStepForward(cell, path);
                    done = run(neighbor, getOpposite(path));
                    onStepBack(done, cell, path);
                }
            }
        }
        
        onExitCell(done, cell, fromSide);
        return done;
    }
    
    public boolean run() {
        for (int r = 0; r < _mc.getRows(); r++) {
            for (int c = 0; c < _mc.getCols(); c++) {
                _m.getCell(r, c).setVisited(false);
            }
        }
        return run(_m.getEntryCell(), Side.Center);
    }
    
    protected boolean onEnterCell(Cell cell, Side side) {
        if (_fwdPathColor != null) {
            _mc.drawPath(cell.getRow(), cell.getCol(), side, _fwdPathColor);
            _mc.drawCenter(cell.getRow(), cell.getCol(), _fwdPathColor);
        }
        return false;
    }
    
    protected List<Side> onGetNextSteps(Cell cell) {
        return new ArrayList<Side>();
    }
    
    protected void onStepForward(Cell cell, Side side) {
        if (_fwdPathColor != null) {
            _mc.drawPath(cell.getRow(), cell.getCol(), side, _fwdPathColor);
        }
    }
    
    protected void onStepBack(boolean done, Cell cell, Side side) {
        if (!done) {
            if (_bktPathColor != null) {
                _mc.drawPath(cell.getRow(), cell.getCol(), side, _bktPathColor);
            } else {
                _mc.erasePath(cell.getRow(), cell.getCol(), side);
            }
        }
    }
    
    protected void onExitCell(boolean done, Cell cell, Side side) {
        if (!done) {
            if (_bktPathColor != null) {
                _mc.drawCenter(cell.getRow(), cell.getCol(), _bktPathColor);
                _mc.drawPath(cell.getRow(), cell.getCol(), side, _bktPathColor);
            } else {
                _mc.eraseCenter(cell.getRow(), cell.getCol());
                _mc.erasePath(cell.getRow(), cell.getCol(), side);
            }
        }
    }
}
