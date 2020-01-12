import java.awt.Color;

import graphics.MazeCanvas;

public class BlockCell extends ShadedCell {
    
    private static final Color _blockColor = Color.LIGHT_GRAY;

    public BlockCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col, _blockColor);
    }
    
    @Override
    public boolean getVisited() {
        return true;
    }

}
