package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

import junit.framework.TestCase;

public class OOContainsTest extends TestCase
{
    public void test_contains()
    {
        OOPredication pred = new OOContains(3);
        assertTrue(pred.predicate(13));
        assertTrue(pred.predicate(35));
        assertTrue(pred.predicate(300));
        assertFalse(pred.predicate(24));
    }
}
