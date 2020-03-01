import graphics.MazeCanvas;

public class Program {

    public static final int NROWS = 38;
    public static final int NCOLS = 54;
    public static final int CELL_SIZE = 20;
    
    public static void main(String[] args) {
        MazeCanvas mc = new MazeCanvas(NROWS, NCOLS, CELL_SIZE);
        Maze m = new Maze(mc);
        mc.open();
        m.initialize();
        mc.pause();
        
        Generator g = new Generator(mc, m);
        g.run();
        mc.pause();
        
        Solver s = new Solver(mc, m);
        s.run();
        mc.pause();
        
        mc.close();
    }

}
