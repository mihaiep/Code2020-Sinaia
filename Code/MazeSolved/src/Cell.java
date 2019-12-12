import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Cell {
    protected MazeCanvas _mc;
    protected int _row, _col;
    protected ArrayList<Side> _walls;
    protected boolean _visited;

    public Cell(MazeCanvas mc, int row, int col) {
        _mc = mc;
        _row = row;
        _col = col;
        _walls = new ArrayList<Side>();
        Collections.addAll(_walls, new Side[] {Side.Top, Side.Bottom, Side.Left, Side.Right});
        _mc.drawCell(_row, _col);
    }
    
    public int getRow() {
        return _row;
    }
    
    public int getCol() {
        return _col;
    }

    public boolean getVisited() {
        return _visited;
    }
    
    public void setVisited(boolean visited) {
        _visited = visited;
    }
    
    public List<Side> getWalls() {
        return new ArrayList<Side>(_walls);
    }
    
    public List<Side> getPaths() {
        List<Side> paths = new ArrayList<Side>();
        for (Side s : Side.values()) {
            if (s != Side.Center && !_walls.contains(s)) {
                paths.add(s);
            }
        }
        return paths;
    }
    
    public void removeWall(Side side) {
        _mc.eraseWall(_row, _col, side);
        _walls.remove((Side)side);
    }
}
