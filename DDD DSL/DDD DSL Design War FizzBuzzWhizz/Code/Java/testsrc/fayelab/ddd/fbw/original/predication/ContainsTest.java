package fayelab.ddd.fbw.original.predication;

import junit.framework.TestCase;

import static fayelab.ddd.fbw.original.SpecTool.contains;

public class ContainsTest extends TestCase
{
    public void test_contains()
    {
        Predication pred = new Contains(3);
        assertTrue(pred.predicate(13));
        assertTrue(pred.predicate(35));
        assertTrue(pred.predicate(300));
        assertFalse(pred.predicate(24));
        
        Predication pred2 = contains(3);
        assertTrue(pred2.predicate(13));
    }
}
