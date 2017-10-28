package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

import junit.framework.TestCase;

public class OOAlwaysTrueTest extends TestCase
{
    public void test_alwaysTrue()
    {
        OOPredication pred = new OOAlwaysTrue();
        assertTrue(pred.predicate(4));
    }
}
