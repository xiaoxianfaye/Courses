package fayelab.ddd.fbw.eight.combination.predication;

import static fayelab.ddd.fbw.eight.combination.SpecTool.alwaysTrue;

import junit.framework.TestCase;

public class AlwaysTrueTest extends TestCase
{
    public void test_alwaysTrue()
    {
        Predication pred = new AlwaysTrue();
        assertTrue(pred.predicate(4));
        
        Predication pred2 = alwaysTrue();
        assertTrue(pred2.predicate(4));
    }
}
