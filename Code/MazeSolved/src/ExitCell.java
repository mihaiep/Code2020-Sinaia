import java.awt.Color;
import graphics.MazeCanvas;

public class ExitCell extends EdgeCell {
    
    // Darkish-green shade color for the exit cell
    private static final Color _exitShadeColor = new Color(64, 255, 64);

    public ExitCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col);
        mc.drawShade(row, col, _exitShadeColor);
    }

}
