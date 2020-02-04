import java.awt.Color;
import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Maze {
    private MazeCanvas _mc;
    private Cell[][] _grid;
    private Cell _entryCell;
    private Cell _exitCell;

    // Checkpoint 1 practice with MazeCanvas
    public void genSnake() {
        Color darkRed = new Color(
                Color.RED.getRed()*2/3, 
                Color.RED.getGreen()*2/3, 
                Color.red.getBlue()*2/3);
        for (int r = 0; r < _mc.getRows(); r++) {
            for (int c = 0; c < _mc.getCols(); c++) {
                _mc.drawCell(r, c);
                if (r == 0 || r == _mc.getRows() - 1) {
                    _mc.eraseWall(r, c, r == 0 ? Side.Bottom : Side.Top);
                    _mc.drawCenter(r, c, darkRed);
                    if (r == 0) {
                        _mc.eraseWall(r, c, c % 2 == 0 ? Side.Left : Side.Right);
                        _mc.drawPath(r, c, Side.Bottom, Color.RED);
                        _mc.drawPath(r, c, c % 2 == 0 ? Side.Left : Side.Right, Color.RED);
                    } else {
                        _mc.eraseWall(r, c, c % 2 == 0 ? Side.Right : Side.Left);
                        _mc.drawPath(r, c, Side.Top, Color.RED);
                        _mc.drawPath(r, c, c % 2 == 0 ? Side.Right : Side.Left, Color.RED);
                    }
                } else {
                    _mc.eraseWall(r, c, Side.Top);
                    _mc.eraseWall(r, c, Side.Bottom);
                    _mc.drawPath(r, c, Side.Top, Color.RED);
                    _mc.drawCenter(r, c, Color.RED);
                    _mc.drawPath(r, c, Side.Bottom, Color.RED);
                }
            }
        }
    }

    /**
     * Constructs the maze object and provides it with the {mazeCanvas} GUI engine
     */
    public Maze(MazeCanvas mc) {
        _mc = mc;
        _grid = new Cell[_mc.getRows()][_mc.getCols()];
    }

    /**
     * Initialize the internal structure of the maze.
     */
    public void initialize() {
        // count how many edge cells are there in this maze
        int nPerim = 2 * (_mc.getRows() + _mc.getCols()) - 4;
        // generate random entrance position: [0 .. (nPerim-1)]
        int nEnterPos = (int)(Math.random()*nPerim);
        // generate random exit position offset from nEnterPos: [1..(nPerim-2)]
        int nExitPos = (nEnterPos + (int)(Math.random() * (nPerim-2)) + 1) % nPerim;

        for (int r = 0; r < _grid.length; r++) {
            for (int c = 0; c < _grid[r].length; c++) {
                if (r == 0 || c == 0 || r == _grid.length-1 || c == _grid[r].length-1) {
                    if (nEnterPos == 0) {
                        _entryCell = new EntryCell(_mc, r, c);
                        _grid[r][c] = _entryCell;
                    } else if (nExitPos == 0) {
                        _exitCell = new ExitCell(_mc, r, c);
                        _grid[r][c] = _exitCell;
                    } else {
                        _grid[r][c] = new EdgeCell(_mc, r, c);
                    }
                    nEnterPos--;
                    nExitPos--;
                } else if (Math.random() < .05){
                    _grid[r][c] = new BlockCell(_mc, r, c);
                } else {
                    _grid[r][c] = new Cell(_mc, r, c);
                }
            }
        }
    }

    /**
     * Returns the entrance in the maze.
     */
    public Cell getEntryCell() {
        return _entryCell;
    }

    /**
     * Returns the exit from the maze.
     */
    public Cell getExitCell() {
        return _exitCell;
    }

    /**
     * Returns the cell at location {row} and {col} in the grid.
     */
    public Cell getCell(int row, int col) {
        return _grid[row][col];
    }

    /**
     * Returns the neighbor of a {cell} on a given {side}.
     */
    public Cell getNeighbor(Cell cell, Side side) {
        Cell neighbor = null;
        if (side == Side.Top && cell.getRow() > 0) {
            neighbor = _grid[cell.getRow()-1][cell.getCol()];
        } else if (side == Side.Bottom && cell.getRow() < _mc.getRows()-1) {
            neighbor = _grid[cell.getRow()+1][cell.getCol()];
        } else if (side == Side.Left && cell.getCol() > 0) {
            neighbor = _grid[cell.getRow()][cell.getCol()-1];
        } else if (side == Side.Right && cell.getCol() < _mc.getCols()-1) {
            neighbor = _grid[cell.getRow()][cell.getCol()+1];
        }
        return neighbor;
    }
}
