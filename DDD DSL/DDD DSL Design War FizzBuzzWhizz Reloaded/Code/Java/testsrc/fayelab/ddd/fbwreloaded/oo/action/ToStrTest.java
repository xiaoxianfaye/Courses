package fayelab.ddd.fbwreloaded.oo.action;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.oo.SpecTool.toStr;

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
