package helpers;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MazeCanvas extends graphics.MazeCanvas {
    
    private static final Side[] _allSides = {Side.Left, Side.Right, Side.Top, Side.Bottom };
    
    private int rowOffset(Side side) {
        return side == Side.Top ? -1 : side == Side.Bottom ? 1 : 0;
    }
    
    private boolean validRowCol(int row, int col) {
        return (row >= 0 && row < this.getRows())
            && (col >= 0 && col < this.getCols());
    }
    
    private boolean validStep(int fromRow, int fromCol, Side toSide) {
        boolean valid = false;
        int toRow = fromRow + rowOffset(toSide);
        int toCol = fromCol + colOffset(toSide);
        
        if (validRowCol(toRow, toCol)) {
            CellState fcs = new CellState(fromRow, fromCol);
            fcs.getCenter();
            fcs.getWallSides();
            CellState tcs = new CellState(toRow, toCol);
            tcs.getCenter();
            tcs.getWallSides();
            valid = fcs.CenterColor.equals(tcs.CenterColor)
                 && !fcs.WallSides.contains(toSide)
                 && !tcs.WallSides.contains(oppSide(toSide));
        }
        
        return valid;
    }
    
    private int colOffset(Side side) {
        return side == Side.Left ? -1 : side == Side.Right ? 1 : 0;
    }
    
    private Side oppSide(Side side) {
        int c = side.ordinal();
        return Side.values()[c&6|c&1^1^c>>2];
    }
    
    private Set<Side> openSides(Set<Side> sides) {
        Set<Side> oppSides = new HashSet<Side>(Arrays.asList(new Side[] {Side.Left, Side.Right, Side.Top, Side.Bottom}));
        for(Side side : sides) {
            oppSides.remove(side);
        }
        return oppSides;
    }
    
    private boolean isBlockCell(int r, int c) {
        CellState cs = new CellState(0, 0);
        cs.getShade();
        Color edgeShade = cs.ShadeColor;
        cs = new CellState(r, c);
        cs.getShade();
        Color shade = cs.ShadeColor;
        return !shade.equals(edgeShade) && !shade.equals(Color.WHITE);
    }
    
    @Override
    public boolean pause() {
        return false;
    }
    
    @Override
    public boolean step() {
        return false;
    }
    
    @Override
    public boolean step(long millis) {
        return false;
    }
    
    public MazeCanvas(int nRows, int nCols, int cellSize) {
        super(nRows, nCols, cellSize);
    }
    
    public void assertPause(int row, int col, String message, boolean test) {
        if (!test) {
            CellState cs = new CellState(row, col);
            cs.drawDbgFrame();
        }
        assertPause(String.format("@[%d,%d] %s", row, col, message), test);
    }
    
    public void assertPause(String message, boolean test) {
        if (!test) {
            super.drawCaption(message);
            super.pause();
        }
        assertTrue(message, test);
    }
    
    public void assertSnake() {
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getCols(); c++) {
                CellState cs = new CellState(r, c);
                cs.getWallSides();
                cs.getPathSides();
                assertPause(r, c, "Missing center.",
                        cs.PathSides.containsKey(Side.Center));
                if (r == 0 || r == getRows() - 1) {
                    assertPause(r, c, "Wrong top/bottom walls.",
                            !cs.WallSides.contains(r == 0 ? Side.Bottom : Side.Top));
                    if (r == 0) {
                        assertPause(r, c, "Wrong left/right walls.",
                                !cs.WallSides.contains(c % 2 == 0 ? Side.Left : Side.Right));
                        assertPause(r, c, "Missing bottom path.",
                                cs.PathSides.containsKey(Side.Bottom));
                    } else {
                        assertPause(r, c, "Wrong left/right walls.",
                                !cs.WallSides.contains(c % 2 == 0 ? Side.Right: Side.Left));
                        assertPause(r, c, "Missing top path.",
                                cs.PathSides.containsKey(Side.Top));
                    }
                } else {
                    assertPause(r, c, "Wrong top/down walls.",
                            !cs.WallSides.contains(Side.Top) && !cs.WallSides.contains(Side.Bottom));
                    assertPause(r, c, "Missing top/bottom path.",
                            cs.PathSides.containsKey(Side.Top) && cs.PathSides.containsKey(Side.Bottom));
                }
            }
        }
    }
    
    public void assertWalls(int row, int col, Side... walls) {
        CellState cs = new CellState(row, col);
        cs.getWallSides();
        List<Side> extraWalls = new ArrayList<Side>(cs.WallSides);
        List<Side> missedWalls = new ArrayList<Side>();
        for (Side wall : walls) {
            if (!cs.WallSides.contains(wall)) {
                missedWalls.add(wall);
            } else {
                extraWalls.remove(wall);
            }
        }
        
        assertPause(
                row, col,
                String.format("Incorrect walls. Missed: '%s'; Extra '%s'.",
                    missedWalls.toString(),
                    extraWalls.toString()),
                extraWalls.size() == 0 && missedWalls.size() == 0);
    }
    
    public void assertPaths(int row, int col, Side... paths) {
        CellState cs = new CellState(row, col);
        cs.getPathSides();
        List<Side> extraPaths = new ArrayList<Side>(cs.PathSides.keySet());
        List<Side> missedPaths = new ArrayList<Side>();
        for (Side path : paths) {
            if (!cs.PathSides.containsKey(path)) {
                missedPaths.add(path);
            } else {
                extraPaths.remove(path);
            }
        }
        
        assertPause(
                row, col,
                String.format("Incorrect paths. Missed: '%s'; Extra '%s'.",
                    missedPaths.toString(),
                    extraPaths.toString()),
                extraPaths.size() == 0 && missedPaths.size() == 0);
    }
    
    public void assertShade(int row, int col, Color shadeColor) {
        CellState cs = new CellState(row, col);
        cs.getShade();
        if (shadeColor != null) {
            assertPause(
                    row, col,
                    String.format("Wrong shade on canvas. Actual: %s; Expected %s", 
                            cs.ShadeColor.toString(), shadeColor.toString()),
                    cs.ShadeColor.equals(shadeColor));
        } else {
            assertPause(
                    row, col,
                    "No shade on canvas. Expected any non-white color.",
                    !cs.ShadeColor.equals(Color.WHITE));
        }
    }
    
    public void assertWallsMatch(int row, int col) {
        CellState cs = new CellState(row, col);
        cs.getWallSides();
        for (Side wall: cs.WallSides) {
            int nr = row + rowOffset(wall);
            int nc = col + colOffset(wall);
            if (validRowCol(nr, nc)) {
                CellState ncs = new CellState(nr, nc);
                ncs.getWallSides();
                assertPause(row,col,
                        String.format("Walls mismatch on %s side.", wall.toString()),
                        ncs.WallSides.contains(oppSide(wall)));
            }
        }
    }
    
    public void assertPathsMatch(int row, int col, Side... sides) {
        CellState cs = new CellState(row, col);
        cs.getWallSides();
        Set<Side> missedPaths = openSides(cs.WallSides);
        Set<Side> extraPaths = new HashSet<Side>();
        for(Side side : sides) {
            if (missedPaths.contains(side)) {
                missedPaths.remove(side);
            } else {
                extraPaths.add(side);
            }
        }
        assertPause(row,col,
            String.format("Incorrect walls in cell. Missed: '%s'; Extra '%s'.", 
                    missedPaths.toString(),extraPaths.toString()),
            missedPaths.size()==0 && extraPaths.size()==0);
    }
    
    public void assertCentersMatch(String name1, int row1, int col1, String name2, int row2, int col2) {
        CellState cs1 = new CellState(row1, col1);
        cs1.getCenter();
        CellState cs2 = new CellState(row2, col2);
        cs2.getCenter();
        assertPause(
                String.format("%s @[%d,%d] and %s @[%d, %d] have mismatched centers.",
                        name1, row1, col1, name2, row2, col2),
                cs1.CenterColor.equals(cs2.CenterColor));
    }
    
    public void assertGenerated() {
        int nRows = this.getRows();
        int nCols = this.getCols();
        List<Integer> explore = new ArrayList<Integer>();
        List<Integer> done = new ArrayList<Integer>();
        
        // iterative bfs algorithm starting from 0,0
        explore.add(0);
        while(explore.size() > 0) {
            int loc = explore.remove(0);
            done.add(loc);
            int r = loc / nCols;
            int c = loc % nCols;
            CellState cs = new CellState(r, c);
            cs.getWallSides();
            for(Side openSide : openSides(cs.WallSides)) {
                int nr = r + rowOffset(openSide);
                int nc = c + colOffset(openSide);
                loc = nr * nCols + nc;
                if (!done.contains(loc)) {
                    explore.add(nr * nCols + nc);
                }
            }
        }
        
        // check all cells either block or visited
        for (int loc = 0; loc < nRows * nCols; loc++) {
            int r = loc / nCols;
            int c = loc % nCols;
            CellState cs = new CellState(r, c);
            cs.getShade();
            assertPause(r, c, "Cell was not explored.", isBlockCell(r, c) || done.contains(loc));
        }
    }

    public void assertVisitedNeighbors(int row, int col) {
        CellState cs = new CellState(row, col);
        cs.getCenter();
        cs.getShade();
        
        assertPause(row, col, "Visited cell missing center path.", 
                !cs.ShadeColor.equals(cs.CenterColor));
        
        int nMarked = 0;
        for (Side s : _allSides) {
            int r = row + this.rowOffset(s);
            int c = col + this.colOffset(s);
            if (validRowCol(r, c)) {
                CellState ncs = new CellState(r, c);
                ncs.getCenter();
                ncs.getShade();
                if (!ncs.ShadeColor.equals(ncs.CenterColor)) {
                    nMarked++;
                }
            }
        }
        
        assertPause(row, col,
                String.format("Wrong count of visited neighbors; Expected >= 1; Actual: %d.", nMarked),
                nMarked >= 1);
    }
    
    public void assertPathSolved(int fromRow, int fromCol, int toRow, int toCol) {
        List<Integer> path = new ArrayList<Integer>();
        CellState cs = new CellState(fromRow, fromCol);
        cs.getCenter();
        boolean done;
        do {
            done = true;
            path.add(fromRow * this.getCols() + fromCol);
            int nextRow = -1;
            int nextCol = -1;
            
            for (Side s : _allSides) {
                if (validStep(fromRow, fromCol, s)) {
                    int r = fromRow + rowOffset(s);
                    int c = fromCol + colOffset(s);
                    if (!path.contains(r * this.getCols() + c)) {
                        assertPause(fromRow, fromCol,
                                String.format("Multiple outbound paths: @[%d, %d], @[%d, %d].", nextRow, nextCol, r, c),
                                nextRow == -1 && nextCol == -1);
                        nextRow = r;
                        nextCol = c;
                        done = false;
                    }
                }
            }
            
            if (!done) {
                fromRow = nextRow;
                fromCol = nextCol;
            }
        } while(!done);
        
        assertPause(fromRow, fromCol, 
                String.format("Path ended at wrong location; Expected @[%d, %d]", toRow, toCol),
                fromRow == toRow && fromCol == toCol);
    }
}
