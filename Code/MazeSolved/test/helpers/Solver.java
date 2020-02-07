package helpers;

import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Solver extends WClass {
    private MazeCanvas _mc;
    private Maze _m;

    //Region: Constructor/instantiate/newInstance sequence
    public Solver(MazeCanvas mc, Maze m) {
        _mc = mc;
        _m = m;
    }
    
    protected void instantiate() throws Exception {
        _wrapObj = getCtor("public Solver(graphics.MazeCanvas,Maze)")
                .newInstance(_mc, _m.getInstance());
    }
    
    public static Solver newInstance(MazeCanvas mc, Maze m) {
        Solver s = new Solver(mc, m);
        return (Solver) s.newInstance();
    }
    //EndRegion: Constructor/instantiate/newInstance sequence

    public boolean run() {
        return (boolean)super.invoke("* boolean *.run()");
    }
    
    @SuppressWarnings("unchecked")
    public List<Side> shuffle(List<Side> sides) {
        return (List<Side>)super.invoke("* java.util.List *.shuffle(java.util.List)", sides);
    }
    
    public Side getOpposite(Side side) {
        return (Side)super.invoke("* graphics.MazeCanvas$Side *.getOpposite(graphics.MazeCanvas$Side)", side);
    }
}
