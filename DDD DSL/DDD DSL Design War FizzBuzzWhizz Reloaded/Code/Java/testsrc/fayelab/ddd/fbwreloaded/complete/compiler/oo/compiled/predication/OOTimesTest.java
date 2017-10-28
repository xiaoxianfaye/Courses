package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

import junit.framework.TestCase;

public class OOTimesTest extends TestCase
{
    public void test_times_3()
    {
        OOPredication pred = new OOTimes(3);
        assertTrue(pred.predicate(6));
        assertFalse(pred.predicate(5));
    }
    
    public void test_times_5()
    {
        OOPredication pred = new OOTimes(5);
        assertTrue(pred.predicate(10));
        assertFalse(pred.predicate(11));
    }
    
    public void test_times_7()
    {
        OOPredication pred = new OOTimes(7);
        assertTrue(pred.predicate(14));
        assertFalse(pred.predicate(15));
    }    
}
