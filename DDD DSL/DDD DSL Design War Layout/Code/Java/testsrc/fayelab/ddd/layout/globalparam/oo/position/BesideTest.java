package fayelab.ddd.layout.globalparam.oo.position;

import static fayelab.ddd.layout.globalparam.oo.position.TestUtil.*;

import java.awt.Container;

import fayelab.ddd.layout.globalparam.oo.component.Component;
import fayelab.ddd.layout.globalparam.oo.component.ComponentStub;
import fayelab.ddd.layout.globalparam.oo.position.Beside;
import junit.framework.TestCase;

public class BesideTest extends TestCase
{
    private ComponentStub leftCmp;
    private ComponentStub rightCmp;
    private Component beside;

    @Override
    protected void setUp()
    {
        this.leftCmp = new ComponentStub();
        this.rightCmp = new ComponentStub();
        this.beside = new Beside(leftCmp, rightCmp, 0.8f);
    }

    public void test_at()
    {
        beside.at(20, 10, 300, 60);
        
        checkRectangle(new int[]{20, 10, 240, 60}, leftCmp.getRectangle());
        checkRectangle(new int[]{260, 10, 60, 60}, rightCmp.getRectangle());
    }

    public void test_in()
    {
        Container container = new Container();
        
        beside.in(container);
        
        assertSame(container, leftCmp.getContainer());
        assertSame(container, rightCmp.getContainer());
    }
}
