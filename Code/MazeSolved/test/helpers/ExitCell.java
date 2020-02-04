package helpers;

import graphics.MazeCanvas;

public class ExitCell extends EdgeCell {
    
    //Region: Constructor/instantiate/newInstance sequence
    public ExitCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col);
    }
    
    public ExitCell(MazeCanvas mc, Object o) {
        super(mc, o);
    }
    
    protected void instantiate() throws Exception {
        _wrapObj = getCtor("public ExitCell(graphics.MazeCanvas,int,int)")
                .newInstance(_mc, _row, _col);
    }
    
    public static ExitCell newInstance(MazeCanvas mc, int row, int col) {
        ExitCell ec = new ExitCell(mc, row, col);
        return (ExitCell) ec.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence
}
