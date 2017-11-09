package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

import junit.framework.TestCase;

public class OOToBuzzTest extends TestCase
{
    public void test_toBuzz()
    {
        OOAction toBuzz = new OOToBuzz();
        assertEquals("Buzz", toBuzz.act(5));
    }
}
