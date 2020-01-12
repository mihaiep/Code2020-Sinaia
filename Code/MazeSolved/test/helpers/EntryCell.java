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
    
    @SuppressWarnings("unchecked")
    protected void instantiate() throws Exception {
        _wrapCtr = _wrapC.getConstructor(new Class[]{MazeCanvas.class, int.class, int.class});
        _wrapObj = _wrapCtr.newInstance(_mc, _row, _col);
    }
    
    public static EdgeCell newInstance(MazeCanvas mc, int row, int col) {
        EntryCell ec = new EntryCell(mc, row, col);
        return (EntryCell) ec.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence
}
