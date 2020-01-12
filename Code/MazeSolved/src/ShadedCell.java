import java.awt.Color;

import graphics.MazeCanvas;

public class ShadedCell extends Cell {

    protected Color _shadeColor;
    
    public ShadedCell(MazeCanvas mc, int row, int col, Color shadeColor) {
        super(mc, row, col);
        _shadeColor = shadeColor;
        _mc.drawShade(row, col, _shadeColor);
    }

}
