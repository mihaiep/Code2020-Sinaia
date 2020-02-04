package helpers;

import java.util.List;

import graphics.MazeCanvas;
import graphics.MazeCanvas.Side;

public class Generator extends WClass {
    private MazeCanvas _mc;
    private Maze _m;

    //Region: Constructor/instantiate/newInstance sequence
    public Generator(MazeCanvas mc, Maze m) {
        _mc = mc;
        _m = m;
    }
    
    protected void instantiate() throws Exception {
        _wrapObj = getCtor("public Generator(graphics.MazeCanvas,Maze)")
                .newInstance(_mc, _m.getInstance());
    }
    
    public static Generator newInstance(MazeCanvas mc, Maze m) {
        Generator g = new Generator(mc, m);
        return (Generator) g.newInstance();
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
