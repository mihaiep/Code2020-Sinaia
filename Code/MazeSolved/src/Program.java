import graphics.MazeCanvas;

public class Program {
    private static int NROWS = 32;
    private static int NCOLS = 48;
    private static int CELLSIZE = 20;

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
        Generator g = new Generator(m, mc);
        g.run();
        mc.pause();
        
        // solve the maze
        Solver s = new Solver(m, mc);
        s.run();
        mc.pause();
        
        // close the maze and terminate the program
        mc.close();
    }

}
