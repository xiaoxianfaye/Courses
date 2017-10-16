package fayelab.ddd.fbw.original.action;

import fayelab.ddd.fbw.original.action.Action;
import fayelab.ddd.fbw.original.action.ToStr;
import junit.framework.TestCase;

import static fayelab.ddd.fbw.original.SpecTool.toStr;

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
