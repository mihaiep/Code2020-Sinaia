import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Explorer {
    protected MazeCanvas _mc;
    protected Maze _m;

    // Light-ish blue color marking the exploration path
    protected static Color _exploreColor = new Color(161, 224, 255);
    
    public Explorer(MazeCanvas mc, Maze m) {
        _mc = mc;
        _m = m;
    }

    // Returns the opposite side of a given {side}
    protected Side getOpposite(Side side) {
        Side opposite = Side.Center;
        if (side == Side.Top) {
            opposite = Side.Bottom;
        } else if (side == Side.Bottom) {
            opposite = Side.Top;
        } else if (side == Side.Left) {
            opposite = Side.Right;
        } else if (side == Side.Right) {
            opposite = Side.Left;
        }
        return opposite;
    }

    // Shuffles randomly the content of the {sides} list
    protected List<Side> shuffle(List<Side> sides) {
        List<Side> rndSides = new ArrayList<Side>();
        
        while (sides.size() > 0) {
            int r = (int)(Math.random() * sides.size());
            rndSides.add(sides.remove(r));
        }

        return rndSides;
    }

    // Default callback: when entering a cell from a side,
    // draw a path from that side to the center.
    protected boolean onEnterCell(Cell cell, Side fromSide) {
        int row = cell.getRow();
        int col = cell.getCol();

        if (fromSide != Side.Center) {
            _mc.drawPath(row,  col, fromSide,  _exploreColor);
        }
        _mc.drawCenter(row, col, _exploreColor);
        _mc.step(10);
        return false;
    }

    // Default callback: a generic explorer returns all
    // valid and opened sides of the cell, randomly shuffled
    protected List<Side> onGetNextSteps(Cell cell) {
        return shuffle(cell.getPaths());
    }

    // Default callback: when stepping forward out of a cell towards a side
    // draw a path from the center to that side.
    protected void onStepForward(Cell cell, Side toSide) {
        _mc.drawPath(cell.getRow(), cell.getCol(), toSide, _exploreColor);
    }

    // Default callback: when stepping back (returning) to a cell from a side
    // erase the path from that side to the cell's center.
    protected void onStepBack(boolean done, Cell cell, Side fromSide) {
        _mc.erasePath(cell.getRow(), cell.getCol(), fromSide);
    }

    // Default callback: when exiting a cell towards a side,
    // erase that path to that side (unless this is the very last step out, case
    // in which leave the center marked)
    protected void onExitCell(boolean done, Cell cell, Side toSide) {
        int row = cell.getRow();
        int col = cell.getCol();

        _mc.eraseCenter(row, col);
        if (toSide != Side.Center) {
            _mc.erasePath(row, col, toSide);
        }
    }

    // internal recursive exploration
    protected boolean run(Cell cell, Side fromSide) {
        cell.setVisited(true);

        // callback: entering the cell
        boolean done = onEnterCell(cell, fromSide);

        // callback: get list of possible next steps from this cell
        List<Side> walls = onGetNextSteps(cell);
        for (int s = 0; !done && s < walls.size(); s++) {
            Side toSide = walls.get(s);
            Cell neighbor = _m.getNeighbor(cell, toSide);
            if (!neighbor.getVisited()) {

                // callback: stepping forward, outside this cell
                onStepForward(cell, toSide);
                done = run(neighbor, getOpposite(toSide));

                // callback: stepping back (returning) into this cell
                onStepBack(done, cell, toSide);
            }
        }

        // callback: exiting the cell
        onExitCell(done, cell, fromSide);

        return done;
    }

    // Runs the explorer and returns the result of the exploration
    public boolean run() {
        boolean result = run(_m.getEntryCell(), Side.Center);

        // after exploration restore the internal state of the maze
        for (int r = 0; r < _mc.getRows(); r++) {
            for (int c = 0; c < _mc.getCols(); c++) {
                _m.getCell(r, c).setVisited(false);
            }
        }
        return result;
    }
}
