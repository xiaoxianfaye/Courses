package fayelab.ddd.fbw.eight.straightforward.predication;

import junit.framework.TestCase;

import static fayelab.ddd.fbw.eight.straightforward.SpecTool.alwaysTrue;

public class AlwaysTrueTest extends TestCase
{
    public void test_alwaysTrue()
    {
        Predication alwaysTrue = new AlwaysTrue();
        assertTrue(alwaysTrue.predicate(1));
        assertTrue(alwaysTrue.predicate(3));
        assertTrue(alwaysTrue.predicate(5));
        
        Predication alwaysTrue_t = alwaysTrue();
        assertTrue(alwaysTrue_t.predicate(1));
    }
}
