package helpers;

import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import graphics.MazeCanvas;

@SuppressWarnings("rawtypes")
public class WMaze {

    MazeCanvas _mc;
    Object _mazeObj;
    Map<String, Method> _mazeMs;

    @SuppressWarnings("unchecked")
    public WMaze(MazeCanvas mc) {
        try {
            _mc = mc;
            Class mazeC = Class.forName("Maze");
            Constructor mazeCtr = mazeC.getConstructor(new Class[]{MazeCanvas.class});
            _mazeObj = mazeCtr.newInstance(_mc);
            _mazeMs = new HashMap<String, Method>();
            for (Method m : mazeC.getDeclaredMethods()) {
                _mazeMs.put(m.toString(), m);
            }
        } catch (Exception e) {
            fail("### Missing or invalid 'Maze' class definition.");
        }
    }

    public void genSnake() {
        Method mGenSnake = MazeTest.getMethod("Maze", "public void Maze.genSnake()", _mazeMs);
        Object noParams[] = {};
        try {
            mGenSnake.invoke(_mazeObj, noParams);
        } catch (Exception e) {
            fail("### Runtime error invoking genSnake().");
        }
    }
}
