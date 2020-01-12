package helpers;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class MazeCanvas extends graphics.MazeCanvas {
    
    public MazeCanvas(int nRows, int nCols, int cellSize) {
        super(nRows, nCols, cellSize);
    }
    
    public void assertPause(int row, int col, String message, boolean test) {
        assertPause(message, test);
    }
    
    public void assertPause(String message, boolean test) {
        if (!test) {
            super.drawCaption(message);
            pause();
        }
        assertTrue(message, test);
    }
    
    public void assertSnake() {
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getCols(); c++) {
                CellState cs = new CellState(r, c);
                cs.getWallSides();
                cs.getPathSides();
                assertPause(
                        r, c,
                        String.format("@[%d, %d] Missing center.", r, c),
                        cs.PathSides.containsKey(Side.Center));
                if (r == 0 || r == getRows() - 1) {
                    assertPause(
                            r, c,
                            String.format("@[%d, %d] Wrong top/bottom walls.", r, c),
                            !cs.WallSides.contains(r == 0 ? Side.Bottom : Side.Top));
                    if (r == 0) {
                        assertPause(
                                r, c,
                                String.format("@[%d, %d] Wrong left/right walls.", r, c),
                                !cs.WallSides.contains(c % 2 == 0 ? Side.Left : Side.Right));
                        assertPause(
                                r, c,
                                String.format("@[%d, %d] Missing bottom path.", r, c),
                                cs.PathSides.containsKey(Side.Bottom));
                    } else {
                        assertPause(
                                r, c,
                                String.format("@[%d, %d] Wrong left/right walls.", r, c),
                                !cs.WallSides.contains(c % 2 == 0 ? Side.Right: Side.Left));
                        assertPause(
                                r, c,
                                String.format("@[%d, %d] Missing top path.", r, c),
                                cs.PathSides.containsKey(Side.Top));
                    }
                } else {
                    assertPause(
                            r, c,
                            String.format("@[%d, %d] Wrong top/down walls.", r, c),
                            !cs.WallSides.contains(Side.Top) && !cs.WallSides.contains(Side.Bottom));
                    assertPause(
                            r, c,
                            String.format("@[%d, %d] Missing top/bottom path.", r, c),
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
        
        if (extraWalls.size() != 0 || missedWalls.size() != 0) {
            Assert.fail(String.format("Incorrect walls on canvas @[%d, %d]. Missed: '%s'; Extra '%s'.",
                    row, col,
                    missedWalls.toString(),
                    extraWalls.toString()));
        }
    }
    
    public void assertShade(int row, int col, Color shadeColor) {
        CellState cs = new CellState(row, col);
        cs.getShade();
        if (shadeColor != null) {
            assertPause(
                    row, col,
                    String.format("Wrong shade on canvas @[%d, %d]. Actual: %s; Expected %s", 
                            row, col, cs.ShadeColor.toString(), shadeColor.toString()),
                    cs.ShadeColor.equals(shadeColor));
        } else {
            assertPause(
                    row, col,
                    String.format("No shade on canvas @[%d, %d]. Expected any non-white color.", 
                            row, col),
                    !cs.ShadeColor.equals(Color.WHITE));
        }
    }
}
