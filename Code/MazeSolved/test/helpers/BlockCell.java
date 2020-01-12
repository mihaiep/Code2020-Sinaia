package helpers;

import graphics.MazeCanvas;

public class BlockCell extends ShadedCell {

    //Region: Constructor/instantiate/newInstance sequence
    public BlockCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col, null);
    }
    
    public BlockCell(MazeCanvas mc, Object o) {
        super(mc, o);
    }
    
    @SuppressWarnings("unchecked")
    protected void instantiate() throws Exception {
        _wrapCtr = _wrapC.getConstructor(new Class[]{MazeCanvas.class, int.class, int.class});
        _wrapObj = _wrapCtr.newInstance(_mc, _row, _col);
    }
    
    public static BlockCell newInstance(MazeCanvas mc, int row, int col) {
        BlockCell bc = new BlockCell(mc, row, col);
        return (BlockCell) bc.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence

    @Override
    public boolean getVisited() {
        return (boolean)super.invoke("public boolean BlockCell.getVisited()");
    }
}
