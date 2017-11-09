package fayelab.ddd.fbwreloaded.oo.action;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.oo.SpecTool.toStr;

public class ToStrTest extends TestCase
{
    public void test_toStr()
    {
        Action toStr = new ToStr();
        assertEquals("1", toStr.act(1));
        assertEquals("10", toStr.act(10));
        
        Action toStr_t = toStr();
        assertEquals("1", toStr_t.act(1));
    }
}
