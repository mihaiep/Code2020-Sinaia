import java.awt.Color;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class ExitCell extends EdgeCell {
    
    // Darkish-green shade color for the exit cell
    private static final Color _exitShadeColor = new Color(64, 255, 64);

    public ExitCell(MazeCanvas mc, int row, int col, List<Side> edges) {
        super(mc, row, col, edges);
        mc.drawShade(row, col, _exitShadeColor);
    }

}
