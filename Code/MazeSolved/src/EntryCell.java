import java.awt.Color;
import graphics.MazeCanvas;

public class EntryCell extends EdgeCell {
    
    // Darkish-blue shade color for the entrance cell
    private static final Color _entryShadeColor = new Color(64, 64, 255);
    
    public EntryCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col);
        mc.drawShade(row, col, _entryShadeColor);
    }
}
