import java.awt.Color;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class EntryCell extends EdgeCell {
    
    // Darkish-blue shade color for the entrance cell
    private static final Color _entryShadeColor = new Color(64, 64, 255);
    
    public EntryCell(MazeCanvas mc, int row, int col, List<Side> edges) {
        super(mc, row, col, edges);
        mc.drawShade(row, col, _entryShadeColor);
    }
}
