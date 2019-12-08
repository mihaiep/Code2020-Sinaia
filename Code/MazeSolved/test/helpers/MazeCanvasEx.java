package helpers;
import static org.junit.Assert.assertTrue;
import graphics.MazeCanvas;

public class MazeCanvasEx extends MazeCanvas {

    private void assertPause(String message, boolean test) {
        if (!test) {
            System.out.println(message);
            pause();
        }
        assertTrue(message, test);
    }
    
    public MazeCanvasEx(int nRows, int nCols, int cellSize) {
        super(nRows, nCols, cellSize);
    }
    
    public void check_getSnake() {
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getCols(); c++) {
                CellState cs = new CellState(r, c);
                cs.getWallSides();
                cs.getPathSides();
                assertPause(
                        String.format("@[%d, %d] Missing center.", r, c),
                        cs.PathSides.containsKey(Side.Center));
                if (r == 0 || r == getRows() - 1) {
                    assertPause(
                            String.format("@[%d, %d] Wrong top/bottom walls.", r, c),
                            !cs.WallSides.contains(r == 0 ? Side.Bottom : Side.Top));
                    if (r == 0) {
                        assertPause(
                                String.format("@[%d, %d] Wrong left/right walls.", r, c),
                                !cs.WallSides.contains(c % 2 == 0 ? Side.Left : Side.Right));
                        assertPause(
                                String.format("@[%d, %d] Missing bottom path.", r, c),
                                cs.PathSides.containsKey(Side.Bottom));
                    } else {
                        assertPause(
                                String.format("@[%d, %d] Wrong left/right walls.", r, c),
                                !cs.WallSides.contains(c % 2 == 0 ? Side.Right: Side.Left));
                        assertPause(
                                String.format("@[%d, %d] Missing top path.", r, c),
                                cs.PathSides.containsKey(Side.Top));
                    }
                } else {
                    assertPause(
                            String.format("@[%d, %d] Wrong top/down walls.", r, c),
                            !cs.WallSides.contains(Side.Top) && !cs.WallSides.contains(Side.Bottom));
                    assertPause(
                            String.format("@[%d, %d] Missing top/bottom path.", r, c),
                            cs.PathSides.containsKey(Side.Top) && cs.PathSides.containsKey(Side.Bottom));
                }
            }
        }
    }
}
