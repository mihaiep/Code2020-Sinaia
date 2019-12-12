import java.awt.Color;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class EdgeCell extends ShadedCell {
    
    protected List<Side> _edges;

    // Pale-red color for the shade of an edge cell
    private static final Color _edgeShadeColor = new Color(255, 224, 224);

    public EdgeCell(MazeCanvas mc, int row, int col, List<Side> edges) {
        super(mc, row, col, _edgeShadeColor);
        _edges = edges;
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
