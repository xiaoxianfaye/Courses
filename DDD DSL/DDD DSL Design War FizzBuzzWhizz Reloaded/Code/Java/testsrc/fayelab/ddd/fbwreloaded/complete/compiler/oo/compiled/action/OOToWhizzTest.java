package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

import junit.framework.TestCase;

public class OOToWhizzTest extends TestCase
{
    public void test_toWhizz()
    {
        OOAction action = new OOToWhizz();
        assertEquals("Whizz", action.act(7));
    }
}
