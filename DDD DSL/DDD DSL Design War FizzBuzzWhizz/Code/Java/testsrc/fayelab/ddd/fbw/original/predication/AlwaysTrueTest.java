package fayelab.ddd.fbw.original.predication;

import junit.framework.TestCase;

import static fayelab.ddd.fbw.original.SpecTool.alwaysTrue;

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
