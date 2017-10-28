package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

import junit.framework.TestCase;

public class OOToStrTest extends TestCase
{
    public void test_toStr()
    {
        OOAction action = new OOToStr();
        assertEquals("4", action.act(4));
    }
}
