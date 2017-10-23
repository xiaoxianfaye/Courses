package fayelab.ddd.fbwreloaded.oo.predication;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.oo.SpecTool.alwaysTrue;

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
