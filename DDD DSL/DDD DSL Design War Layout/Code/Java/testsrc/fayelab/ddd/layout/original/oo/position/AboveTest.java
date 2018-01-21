package fayelab.ddd.layout.original.oo.position;

import static fayelab.ddd.layout.original.oo.position.TestUtil.*;

import java.awt.Container;

import fayelab.ddd.layout.original.oo.component.Component;
import fayelab.ddd.layout.original.oo.component.ComponentStub;
import fayelab.ddd.layout.original.oo.position.Above;
import junit.framework.TestCase;

public class AboveTest extends TestCase
{    
    private ComponentStub upCmp;
    private ComponentStub downCmp;
    private Component above;
    
    @Override
    protected void setUp()
    {
        this.upCmp = new ComponentStub();
        this.downCmp = new ComponentStub();
        this.above = new Above(upCmp, downCmp, 0.5f);
    }
    
    public void test_at()
    {
        above.at(20, 10, 300, 60);
        
        checkRectangle(new int[]{20, 10, 300, 30}, upCmp.getRectangle());
        checkRectangle(new int[]{20, 40, 300, 30}, downCmp.getRectangle());
    }
    
    public void test_in()
    {
        Container container = new Container();
        
        above.in(container);
        
        assertSame(container, upCmp.getContainer());
        assertSame(container, downCmp.getContainer());
    }
}
