package fayelab.ddd.fbw.eight.straightforward.action;

import static fayelab.ddd.fbw.eight.straightforward.SpecTool.toHazz;

import junit.framework.TestCase;

public class ToHazzTest extends TestCase
{
    public void test_toHazz()
    {
        Action action = new ToHazz();
        assertEquals("Hazz", action.act(8));
        
        Action action2 = toHazz();
        assertEquals("Hazz", action2.act(8));
    }
}
