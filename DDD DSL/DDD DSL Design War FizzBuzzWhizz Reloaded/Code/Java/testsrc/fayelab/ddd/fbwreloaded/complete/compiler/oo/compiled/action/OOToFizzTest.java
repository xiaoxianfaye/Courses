package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

import junit.framework.TestCase;

public class OOToFizzTest extends TestCase
{
    public void test_toFizz()
    {
        OOAction action = new OOToFizz();
        assertEquals("Fizz", action.act(3));
    }
}
