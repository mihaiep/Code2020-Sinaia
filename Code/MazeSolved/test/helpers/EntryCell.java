package helpers;

import graphics.MazeCanvas;

public class EntryCell extends EdgeCell {
    
    //Region: Constructor/instantiate/newInstance sequence
    public EntryCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col);
    }
    
    public EntryCell(MazeCanvas mc, Object o) {
        super(mc, o);
    }
    
    protected void instantiate() throws Exception {
        _wrapObj = getCtor("public EntryCell(graphics.MazeCanvas,int,int)")
                .newInstance(_mc, _row, _col);
    }
    
    public static EdgeCell newInstance(MazeCanvas mc, int row, int col) {
        EntryCell ec = new EntryCell(mc, row, col);
        return (EntryCell) ec.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence
}
