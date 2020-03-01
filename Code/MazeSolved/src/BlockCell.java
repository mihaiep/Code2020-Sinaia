import java.awt.Color;

import graphics.MazeCanvas;

public class BlockCell extends ShadedCell {

    private static final Color _blockShadeColor = Color.LIGHT_GRAY;
    
    public BlockCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col, _blockShadeColor);
    }
    
    @Override
    public boolean getVisited() {
        return true;
    }

}
