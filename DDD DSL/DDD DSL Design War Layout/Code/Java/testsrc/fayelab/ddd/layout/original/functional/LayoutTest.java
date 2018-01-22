package fayelab.ddd.layout.original.functional;

import java.awt.Container;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

import junit.framework.TestCase;

import static fayelab.ddd.layout.original.functional.Layout.*;

public class LayoutTest extends TestCase
{
    private static final int TOLERANCE = 1;
    
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Container> containers;

    protected void setUp()
    {
        this.rectangles = new ArrayList<>();
        this.containers = new ArrayList<>();
    }
    
    public void test_beside()
    {
        Function<Rectangle, Consumer<Container>> leftCmp = componentStub();
        Function<Rectangle, Consumer<Container>> rightCmp = componentStub();
        Container container = new Container();
        
        beside(leftCmp, rightCmp, 0.8f).apply(rectangle(20, 10, 300, 60)).accept(container);
        
        checkRectangle(rectangle(20, 10, 240, 60), rectangles.get(0));
        checkRectangle(rectangle(260, 10, 60, 60), rectangles.get(1));
        
        assertSame(container, containers.get(0));
        assertSame(container, containers.get(1));
    }
    
    public void test_above()
    {
        Function<Rectangle, Consumer<Container>> upCmp = componentStub();
        Function<Rectangle, Consumer<Container>> downCmp = componentStub();
        Container container = new Container();
        
        above(upCmp, downCmp, 0.5f).apply(rectangle(20, 10, 300, 60)).accept(container);
        
        checkRectangle(rectangle(20, 10, 300, 30), rectangles.get(0));
        checkRectangle(rectangle(20, 40, 300, 30), rectangles.get(1));
        
        assertSame(container, containers.get(0));
        assertSame(container, containers.get(1));
    }

    private Function<Rectangle, Consumer<Container>> componentStub()
    {
        return rectangle -> {
            rectangles.add(rectangle);
            return container -> containers.add(container);
        };
    }
    
    private void checkRectangle(Rectangle expected, Rectangle actual)
    {
        assertEquals((int)expected.getX(), (int)actual.getX(), TOLERANCE);
        assertEquals((int)expected.getY(), (int)actual.getY(), TOLERANCE);
        assertEquals((int)expected.getWidth(), (int)actual.getWidth(), TOLERANCE);
        assertEquals((int)expected.getHeight(), (int)actual.getHeight(), TOLERANCE);
    }
}
