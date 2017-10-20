package fayelab.ddd.layout.globalparamconf.position;

import static fayelab.ddd.layout.globalparamconf.position.TestUtil.*;

import java.awt.Container;

import fayelab.ddd.layout.globalparamconf.component.Component;
import fayelab.ddd.layout.globalparamconf.component.ComponentStub;
import fayelab.ddd.layout.globalparamconf.position.Beside;
import junit.framework.TestCase;

public class BesideTest extends TestCase
{
    private ComponentStub left;
    private ComponentStub right;
    private Component beside;
    
    @Override
    protected void setUp()
    {
        this.left = new ComponentStub();
        this.right = new ComponentStub();
        this.beside = new Beside(left, right, 0.8f);
    }
    
    public void test_at()
    {
        beside.at(0, 0, 300, 60);
        
        checkRect(new int[]{0, 0, 240, 60}, left.getRect());
        checkRect(new int[]{240, 0, 60, 60}, right.getRect());
    }
    
    public void test_in()
    {
        Container container = new Container();
        
        beside.at(0, 0, 300, 60).in(container);
        
        assertSame(container, left.getContainer());
        assertSame(container, right.getContainer());
    }
}