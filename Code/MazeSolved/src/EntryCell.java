import java.awt.Color;

import graphics.MazeCanvas;

public class EntryCell extends EdgeCell {

    private final Color _entryShadeColor = Color.BLUE;
    
    public EntryCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col);
        mc.drawShade(row, col, _entryShadeColor);
    }

}
