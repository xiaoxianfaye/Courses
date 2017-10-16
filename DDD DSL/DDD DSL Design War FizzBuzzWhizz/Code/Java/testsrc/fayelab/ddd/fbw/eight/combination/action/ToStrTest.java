package fayelab.ddd.fbw.eight.combination.action;

import junit.framework.TestCase;

import static fayelab.ddd.fbw.eight.combination.SpecTool.toStr;

public class ToStrTest extends TestCase
{
    public void test_toStr()
    {
        Action action = new ToStr();
        assertEquals("4", action.act(4));
        
        Action action2 = toStr();
        assertEquals("4", action2.act(4));
    }
}
