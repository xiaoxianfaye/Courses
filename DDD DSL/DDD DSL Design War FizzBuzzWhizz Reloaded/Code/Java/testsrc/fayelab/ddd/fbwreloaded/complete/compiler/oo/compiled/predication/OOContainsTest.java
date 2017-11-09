package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

import junit.framework.TestCase;

public class OOContainsTest extends TestCase
{
    public void test_contains()
    {
        OOPredication contains3 = new OOContains(3);
        assertTrue(contains3.predicate(13));
        assertTrue(contains3.predicate(35));
        assertTrue(contains3.predicate(300));
        assertFalse(contains3.predicate(24));
    }
}
