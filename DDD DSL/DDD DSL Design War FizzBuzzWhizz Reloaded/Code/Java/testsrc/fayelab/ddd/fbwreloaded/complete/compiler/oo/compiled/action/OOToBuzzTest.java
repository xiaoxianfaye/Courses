package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

import junit.framework.TestCase;

public class OOToBuzzTest extends TestCase
{
    public void test_toBuzz()
    {
        OOAction action = new OOToBuzz();
        assertEquals("Buzz", action.act(5));
    }
}
