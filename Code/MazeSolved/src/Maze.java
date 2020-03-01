import java.awt.Color;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Maze {
    private final int BLOCKCELLS_PCT = 5;
    private MazeCanvas _mc;
    private Cell[][] _grid;
    
    private EntryCell _entryCell;
    private ExitCell _exitCell;
    
    public void genSnake() {
        for (int r = 0; r < _mc.getRows(); r++) {
            for (int c = 0; c < _mc.getCols(); c++) {
                _mc.drawCell(r, c);
                if (r == 0) {
                    // first row
                    _mc.drawCenter(r, c, Color.RED.darker());
                    _mc.eraseWall(r, c, Side.Bottom);
                    if (c % 2 == 0) {
                        _mc.eraseWall(r, c, Side.Left);
                        _mc.drawPath(r, c, Side.Left, Color.RED);
                    } else {
                        _mc.eraseWall(r, c, Side.Right);
                        _mc.drawPath(r, c, Side.Right, Color.RED);
                    }
                    _mc.drawPath(r, c, Side.Bottom, Color.RED);
                } else if (r == _mc.getRows()-1) {
                    // last row
                    _mc.drawCenter(r, c, Color.RED.darker());
                    _mc.eraseWall(r, c, Side.Top);
                    if (c % 2 == 0) {
                        _mc.eraseWall(r, c, Side.Right);
                        _mc.drawPath(r, c, Side.Right, Color.RED);
                    } else {
                        _mc.eraseWall(r, c, Side.Left);
                        _mc.drawPath(r, c, Side.Left, Color.RED);
                    }
                    _mc.drawPath(r, c, Side.Top, Color.RED);
                } else {
                    // inner row
                    _mc.eraseWall(r, c,  Side.Top);
                    _mc.eraseWall(r, c, Side.Bottom);
                    _mc.drawCenter(r, c, Color.RED);
                    _mc.drawPath(r, c, Side.Top, Color.RED);
                    _mc.drawPath(r, c, Side.Bottom, Color.RED);
                }
            }
        }
    }


    public Maze(MazeCanvas mc) {
        _mc = mc;
        _grid = new Cell[mc.getRows()][mc.getCols()];
    }
    
    public void initialize() {
        int count = (int)((_mc.getRows() - 1) * (_mc.getCols()-1) * (BLOCKCELLS_PCT / 100.0));
        int perimeter = _mc.getRows() * 2 + _mc.getCols() * 2 - 4;
        int nEntry = 0;
        int nExit = 0;
        while (nEntry == nExit) {
            nEntry = (int)(Math.random() * perimeter);
            nExit = (int)(Math.random() * perimeter);
        }
        perimeter = 0;
        for (int r = 0; r < _grid.length; r++) {
            for (int c = 0; c < _grid[r].length; c++) {
                if (r == 0 || c == 0 || r == _grid.length - 1 || c == _grid[r].length-1) {
                    if (perimeter == nEntry) {
                        _entryCell = new EntryCell(_mc, r, c);
                        _grid[r][c] = _entryCell;
                    } else if (perimeter == nExit) {
                        _exitCell = new ExitCell(_mc, r, c);
                        _grid[r][c] = _exitCell;
                    } else {
                        _grid[r][c] = new EdgeCell(_mc, r, c);
                    }
                    perimeter++;
                } else {
                    if (count > 0 && Math.random() <= (BLOCKCELLS_PCT / 100.0)) {
                        _grid[r][c] = new BlockCell(_mc, r, c);
                        count--;
                    } else {
                        _grid[r][c] = new Cell(_mc, r, c);
                    }
                }
            }
        }
    }
    
    public Cell getCell(int row, int col) {
        return _grid[row][col];
    }
    
    public Cell getEntryCell() {
        return _entryCell;
    }
    
    public Cell getExitCell() {
        return _exitCell;
    }
    
    public Cell getNeighbor(Cell cell, Side side) {
        if (side == Side.Top && cell.getRow() > 0) {
            return _grid[cell.getRow()-1][cell.getCol()];
        } else if (side == Side.Bottom && cell.getRow() < _mc.getRows()-1) {
            return _grid[cell.getRow()+1][cell.getCol()];
        } else if (side == Side.Left && cell.getCol() > 0) {
            return _grid[cell.getRow()][cell.getCol()-1];
        } else if (side == Side.Right && cell.getCol() < _mc.getCols()-1) {
            return _grid[cell.getRow()][cell.getCol()+1];
        } else {
            return null;
        }
    }
}
