package fayelab.ddd.layout.original.position;

import static fayelab.ddd.layout.original.position.TestUtil.*;

import java.awt.Container;

import fayelab.ddd.layout.original.component.Component;
import fayelab.ddd.layout.original.component.ComponentStub;
import fayelab.ddd.layout.original.position.Above;
import junit.framework.TestCase;

public class AboveTest extends TestCase
{    
    private ComponentStub up;
    private ComponentStub down;
    private Component above;
    
    @Override
    protected void setUp()
    {
        this.up = new ComponentStub();
        this.down = new ComponentStub();
        this.above = new Above(up, down, 0.5f);
    }
    
    public void test_at()
    {
        above.at(0, 0, 300, 60);
        
        checkRect(new int[]{0, 0, 300, 30}, up.getRect());
        checkRect(new int[]{0, 30, 300, 30}, down.getRect());
    }
    
    public void test_in()
    {
        Container container = new Container();
        
        above.at(0, 0, 300, 60).in(container);
        
        assertSame(container, up.getContainer());
        assertSame(container, down.getContainer());
    }
}
