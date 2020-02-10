package helpers;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;
import helpers.Cell;

public class Maze extends WClass {
    protected MazeCanvas _mc;
    
    //Region: Constructor/instantiate/newInstance sequence
    public Maze(MazeCanvas mc) {
        _mc = mc;
    }
    
    protected void instantiate() throws Exception {
        _wrapObj = getCtor("public Maze(graphics.MazeCanvas)")
                .newInstance(_mc);
    }
    
    public static Maze newInstance(MazeCanvas mc) {
        Maze m = new Maze(mc);
        return (Maze) m.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence
    
    public void genSnake() {
        super.invoke("public void Maze.genSnake()");
    }
    
    public void initialize() {
        super.invoke("public void Maze.initialize()");
    }
    
    public Cell getCell(int row, int col) {
        Object cell = super.invoke("public Cell Maze.getCell(int,int)", row, col);
        if (cell != null) {
            String cellType = cell.getClass().getName();
            if (cellType.equals("Cell")) {
                cell = new Cell(_mc, cell);
            } else if (cellType.equals("EdgeCell")) {
                cell = new EdgeCell(_mc, cell);
            } else if (cellType.equals("EntryCell")) {
                cell = new EntryCell(_mc, cell);
            } else if (cellType.equals("ExitCell")) {
                cell = new ExitCell(_mc, cell);
            } else if (cellType.equals("BlockCell")) {
                cell = new BlockCell(_mc, cell);
            } else {
                cell = null;
            }
        }
        return (Cell)cell;
    }
    
    public Cell getEntryCell() {
        Object cell = super.invoke("public Cell Maze.getEntryCell()");
        if (cell != null) {
            String cellType = cell.getClass().getName();
            if (cellType.equals("EntryCell")) {
                cell = new EntryCell(_mc, cell);
            } else {
                cell = null;
            }
        }
        return (Cell)cell;
    }
    
    public Cell getExitCell() {
        Object cell = super.invoke("public Cell Maze.getExitCell()");
        if (cell != null) {
            String cellType = cell.getClass().getName();
            if (cellType.equals("ExitCell")) {
                cell = new ExitCell(_mc, cell);
            } else {
                cell = null;
            }
        }
        return (Cell)cell;
    }
    
    public Cell getNeighbor(Cell cell, Side side) {
        Object neighbor = super.invoke("public Cell Maze.getNeighbor(Cell,graphics.MazeCanvas$Side)", 
                cell.getInstance(), side);
        if (neighbor != null) {
            String cellType = neighbor.getClass().getName();
            if (cellType.equals("Cell")) {
                neighbor = new Cell(_mc, neighbor);
            } else if (cellType.equals("EdgeCell")) {
                neighbor = new EdgeCell(_mc, neighbor);
            } else if (cellType.equals("EntryCell")) {
                neighbor = new EntryCell(_mc, neighbor);
            } else if (cellType.equals("ExitCell")) {
                neighbor = new ExitCell(_mc, neighbor);
            } else if (cellType.equals("BlockCell")) {
                neighbor = new BlockCell(_mc, neighbor);
            } else {
                neighbor = null;
            }
        }
        return (Cell)neighbor;
    }
}
