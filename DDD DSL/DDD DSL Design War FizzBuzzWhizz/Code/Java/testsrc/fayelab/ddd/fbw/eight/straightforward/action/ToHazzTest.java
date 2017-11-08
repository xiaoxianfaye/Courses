package fayelab.ddd.fbw.eight.straightforward.action;

import static fayelab.ddd.fbw.eight.straightforward.SpecTool.toHazz;

import junit.framework.TestCase;

public class ToHazzTest extends TestCase
{
    public void test_toHazz()
    {
        Action toHazz = new ToHazz();
        assertEquals("Hazz", toHazz.act(8));
        
        Action toHazz_t = toHazz();
        assertEquals("Hazz", toHazz_t.act(8));
    }
}
