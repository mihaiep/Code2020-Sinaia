import graphics.MazeCanvas;

public class Program {
    private static int NROWS = 96;
    private static int NCOLS = 128;
    private static int CELLSIZE = 8;

    public static void main(String[] args) {
        // create and open the maze canvas
        MazeCanvas mc = new MazeCanvas(NROWS, NCOLS, CELLSIZE);
        mc.open();
        
        // create the maze instance
        Maze m = new Maze(mc);
        
        // checkpoint 1 practice with MazeCanvas
        m.genSnake();
        mc.pause();
        mc.clear();
        
        // initialize the maze with proper cells
        m.initialize();
        mc.pause();
        
        // generate a random maze
        Generator g = new Generator(mc, m);
        g.run();
        mc.pause();
        
        // solve the maze
        Solver s = new Solver(mc, m);
        s.run();
        mc.pause();
        
        // close the maze and terminate the program
        mc.close();
    }

}
