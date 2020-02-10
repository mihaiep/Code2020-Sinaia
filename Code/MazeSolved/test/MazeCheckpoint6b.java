import org.junit.Test;
import org.junit.runner.RunWith;

import helpers.Order;
import helpers.OrderedRunner;

import helpers.WClass;

@RunWith(OrderedRunner.class)
@Order(order = 620)
public class MazeCheckpoint6b {
    @Test
    @Order(order = 621)
    public void testExplorer_Class() {
        WClass.checkClass("Explorer",
                "public Explorer(graphics.MazeCanvas,Maze,java.awt.Color,java.awt.Color)",
                "protected boolean Explorer.onEnterCell(Cell,graphics.MazeCanvas$Side)",
                "protected java.util.List Explorer.onGetNextSteps(Cell)",
                "protected void Explorer.onStepForward(Cell,graphics.MazeCanvas$Side)",
                "protected void Explorer.onStepBack(boolean,Cell,graphics.MazeCanvas$Side)",
                "protected void Explorer.onExitCell(boolean,Cell,graphics.MazeCanvas$Side)",
                "* boolean Explorer.run(Cell,graphics.MazeCanvas$Side)",
                "public boolean Explorer.run()");
    }
    
    @Test
    @Order(order = 622)
    public void testGenerator_Class() {
        WClass.checkClass("Generator extends Explorer",
                "protected boolean Generator.onEnterCell(Cell,graphics.MazeCanvas$Side)",
                "protected java.util.List Generator.onGetNextSteps(Cell)",
                "protected void Generator.onStepForward(Cell,graphics.MazeCanvas$Side)",
                "protected void Explorer.onStepBack(boolean,Cell,graphics.MazeCanvas$Side)",
                "protected void Explorer.onExitCell(boolean,Cell,graphics.MazeCanvas$Side)");
    }

    @Test
    @Order(order = 623)
    public void testSolver_Class() {
        WClass.checkClass("Solver extends Explorer",
                "protected boolean Solver.onEnterCell(Cell,graphics.MazeCanvas$Side)",
                "protected java.util.List Solver.onGetNextSteps(Cell)",
                "protected void Explorer.onStepForward(Cell,graphics.MazeCanvas$Side)",
                "protected void Explorer.onStepBack(boolean,Cell,graphics.MazeCanvas$Side)",
                "protected void Explorer.onExitCell(boolean,Cell,graphics.MazeCanvas$Side)");
    }
}
