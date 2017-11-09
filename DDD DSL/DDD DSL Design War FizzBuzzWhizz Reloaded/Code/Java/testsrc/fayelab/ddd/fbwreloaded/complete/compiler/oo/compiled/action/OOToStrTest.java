package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

import junit.framework.TestCase;

public class OOToStrTest extends TestCase
{
    public void test_toStr()
    {
        OOAction toStr = new OOToStr();
        assertEquals("1", toStr.act(1));
        assertEquals("10", toStr.act(10));
    }
}
