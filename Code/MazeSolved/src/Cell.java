import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Cell {

    private MazeCanvas _mc;
    private int _row;
    private int _col;
    private List<Side> _walls;
    private boolean _visited;
    
    public Cell(MazeCanvas mc, int row, int col) {
        _mc = mc;
        _row = row;
        _col = col;
        _walls = new ArrayList<Side>();
        Collections.addAll(_walls, new Side[] {Side.Top, Side.Bottom, Side.Left, Side.Right});
        _visited = false;
        
        _mc.drawCell(row, col);
    }
    
    public int getRow() {
        return _row;
    }
    
    public int getCol() {
        return _col;
    }
    
    public List<Side> getWalls() {
        return new ArrayList<Side>(_walls);
    }
    
    public List<Side> getPaths() {
        List<Side> paths = new ArrayList<Side>();
        Collections.addAll(paths, new Side[] {Side.Top, Side.Bottom, Side.Left, Side.Right});
        for (Side side : _walls) {
            paths.remove(side);
        }
        return paths;
    }
    
    public void removeWall(Side side) {
        _walls.remove(side);
        _mc.eraseWall(_row, _col, side);
    }
    
    public boolean getVisited() {
        return _visited;
    }
    
    public void setVisited(boolean visited) {
        _visited = visited;
    }
}
