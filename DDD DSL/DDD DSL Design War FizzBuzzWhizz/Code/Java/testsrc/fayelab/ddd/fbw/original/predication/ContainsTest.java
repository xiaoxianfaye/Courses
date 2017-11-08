package fayelab.ddd.fbw.original.predication;

import junit.framework.TestCase;

import static fayelab.ddd.fbw.original.SpecTool.contains;

public class ContainsTest extends TestCase
{
    public void test_contains()
    {
        Predication contains3 = new Contains(3);
        assertTrue(contains3.predicate(13));
        assertTrue(contains3.predicate(35));
        assertTrue(contains3.predicate(300));
        assertFalse(contains3.predicate(24));
        
        Predication contains3_t = contains(3);
        assertTrue(contains3_t.predicate(13));
    }
}
