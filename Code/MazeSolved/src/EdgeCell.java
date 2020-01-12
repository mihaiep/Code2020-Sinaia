import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class EdgeCell extends ShadedCell {
    
    protected List<Side> _edges;

    // Pale-red color for the shade of an edge cell
    private static final Color _edgeShadeColor = new Color(255, 224, 224);

    public EdgeCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col, _edgeShadeColor);
        _edges = new ArrayList<Side>();
        if (row == 0) {
            _edges.add(Side.Top);
        }
        if (row == mc.getRows()-1) {
            _edges.add(Side.Bottom);
        }
        if (col == 0) {
            _edges.add(Side.Left);
        }
        if (col == mc.getCols()-1) {
            _edges.add(Side.Right);
        }
    }
    
    @Override
    public List<Side> getWalls() {
        List<Side> walls = super.getWalls();
        for(Side edge : _edges) {
            walls.remove(edge);
        }
        return walls;
    }
    
    @Override
    public List<Side> getPaths() {
        List<Side> paths = super.getPaths();
        for (Side edge : _edges) {
            paths.remove(edge);
        }
        return paths;
    }
}
