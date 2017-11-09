package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

import junit.framework.TestCase;

public class OOAlwaysTrueTest extends TestCase
{
    public void test_alwaysTrue()
    {
        OOPredication alwaysTrue = new OOAlwaysTrue();
        assertTrue(alwaysTrue.predicate(1));
        assertTrue(alwaysTrue.predicate(3));
        assertTrue(alwaysTrue.predicate(5));
    }
}
