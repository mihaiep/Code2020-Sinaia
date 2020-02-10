import java.awt.Color;

import graphics.MazeCanvas;

public class ExitCell extends EdgeCell {

    private final Color _exitShadeColor = Color.GREEN;
    
    public ExitCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col);
        mc.drawShade(row, col, _exitShadeColor);
    }

}
