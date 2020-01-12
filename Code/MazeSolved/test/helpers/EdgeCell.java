package helpers;

import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class EdgeCell extends ShadedCell {

    //Region: Constructor/instantiate/newInstance sequence
    public EdgeCell(MazeCanvas mc, int row, int col) {
        super(mc, row, col, null);
    }
    
    public EdgeCell(MazeCanvas mc, Object o) {
        super(mc, o);
    }
    
    @SuppressWarnings("unchecked")
    protected void instantiate() throws Exception {
        _wrapCtr = _wrapC.getConstructor(new Class[]{MazeCanvas.class, int.class, int.class});
        _wrapObj = _wrapCtr.newInstance(_mc, _row, _col);
    }
    
    public static EdgeCell newInstance(MazeCanvas mc, int row, int col) {
        EdgeCell ec = new EdgeCell(mc, row, col);
        return (EdgeCell) ec.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence
    
    @SuppressWarnings("unchecked")
    public List<Side> getWalls() {
        return (List<Side>)super.invoke("public java.util.List EdgeCell.getWalls()");
    }
}
