package helpers;

import java.util.ArrayList;
import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Cell extends WClass {
    protected MazeCanvas _mc;
    protected int _row;
    protected int _col;
    
    //Region: Constructor/instantiate/newInstance sequence
    public Cell(MazeCanvas mc, int row, int col) {
        _mc = mc;
        _row = row;
        _col = col;
    }
    
    public Cell(MazeCanvas mc, Object o) {
        super(o);
        _mc = mc;
        _row = getRow();
        _col = getCol();
    }
    
    @SuppressWarnings("unchecked")
    protected void instantiate() throws Exception {
        _wrapCtr = _wrapC.getConstructor(new Class[]{MazeCanvas.class, int.class, int.class});
        _wrapObj = _wrapCtr.newInstance(_mc, _row, _col);
    }
    
    public static Cell newInstance(MazeCanvas mc, int row, int col) {
        Cell c = new Cell(mc, row, col);
        return (Cell)c.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence
    
    public int getRow() {
        return (int)super.invoke("public int Cell.getRow()");
    }
    
    public int getCol() {
        return (int)super.invoke("public int Cell.getCol()");
    }
    
    @SuppressWarnings("unchecked")
    public List<Side> getWalls() {
        return (List<Side>)super.invoke("public java.util.List Cell.getWalls()");
    }
    
    public void removeWall(Side side) {
        super.invoke("public void Cell.removeWall(graphics.MazeCanvas$Side)", side);
    }
    
    public boolean getVisited() {
        return (boolean)super.invoke("public boolean Cell.getVisited()");
    }
    
    public void setVisited(boolean visited) {
        super.invoke("public void Cell.setVisited(boolean)", visited);
    }
    
    //Region: Test methods
    public void assertWalls(Side... sides) {
        List<Side> extraSides = this.getWalls();
        List<Side> missedSides = new ArrayList<Side>();
        
        for(Side side : sides) {
            if (!extraSides.contains(side)) {
                missedSides.add(side);
            } else {
                extraSides.remove(side);
            }
        }
        
        if (extraSides.size() != 0 || missedSides.size() != 0) {
            helpers.MazeCanvas hmc = (helpers.MazeCanvas) _mc;
            hmc.assertPause(
                    _row, _col,
                    String.format("Incorrect walls in cell @[%d, %d]. Missed: '%s'; Extra '%s'.", 
                            _row, _col,
                            missedSides.toString(),
                            extraSides.toString()),
                    false);
        }
    }
    
    public void assertType(Class<?>... types) {
        boolean matched = false;
        for(Class<?> type : types) {
            if (getClass() == type) {
                matched = true;
                break;
            }
        }
        ((helpers.MazeCanvas) _mc).assertPause(
                _row, _col,
                String.format("Wrong cell type @[%d, %d]. Actual: %s; Expected: %s",
                        _row, _col,
                        getClass().getName(), types.toString()),
                matched);
    }
    //EndRegion: Test methods
}
