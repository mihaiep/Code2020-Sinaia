package helpers;

import java.awt.Color;

import graphics.MazeCanvas;

public class ShadedCell extends Cell {
    protected Color _shadeColor;
    
    //Region: Constructor/instantiate/newInstance sequence
    public ShadedCell(MazeCanvas mc, int row, int col, Color shadeColor) {
        super(mc, row, col);
        _shadeColor = shadeColor;
    }
    
    public ShadedCell(MazeCanvas mc, Object o) {
        super(mc, o);
    }
    
    @SuppressWarnings("unchecked")
    protected void instantiate() throws Exception {
        _wrapCtr = _wrapC.getConstructor(new Class[]{MazeCanvas.class, int.class, int.class, java.awt.Color.class});
        _wrapObj = _wrapCtr.newInstance(_mc, _row, _col, _shadeColor);
    }
    
    public static ShadedCell newInstance(MazeCanvas mc, int row, int col, Color shadeColor) {
        ShadedCell c = new ShadedCell(mc, row, col, shadeColor);
        return (ShadedCell) c.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence
    
}
