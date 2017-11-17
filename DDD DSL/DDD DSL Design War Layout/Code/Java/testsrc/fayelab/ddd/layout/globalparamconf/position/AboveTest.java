package fayelab.ddd.layout.globalparamconf.position;

import static fayelab.ddd.layout.globalparamconf.position.TestUtil.*;

import java.awt.Container;

import fayelab.ddd.layout.globalparamconf.component.Component;
import fayelab.ddd.layout.globalparamconf.component.ComponentStub;
import fayelab.ddd.layout.globalparamconf.position.Above;
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
        above.at(20, 10, 300, 60);
        
        checkRect(new int[]{20, 10, 300, 30}, up.getRect());
        checkRect(new int[]{20, 40, 300, 30}, down.getRect());
    }
    
    public void test_in()
    {
        Container container = new Container();
        
        above.in(container);
        
        assertSame(container, up.getContainer());
        assertSame(container, down.getContainer());
    }
}
