import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class BlockCell extends ShadedCell {
    
    private static final Color _blockColor = Color.LIGHT_GRAY;

    public BlockCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col, _blockColor);
    }
    
    @Override
    public boolean getVisited() {
        return true;
    }
    
    @Override
    public List<Side> getWalls() {
        return new ArrayList<Side>();
    }

}
