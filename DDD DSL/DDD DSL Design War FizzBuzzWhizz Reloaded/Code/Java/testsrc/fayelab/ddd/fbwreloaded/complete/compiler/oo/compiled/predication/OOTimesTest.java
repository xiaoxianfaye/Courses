package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

import junit.framework.TestCase;

public class OOTimesTest extends TestCase
{
    public void test_times_3()
    {
        OOPredication times3 = new OOTimes(3);
        assertTrue(times3.predicate(6));
        assertFalse(times3.predicate(5));
    }
    
    public void test_times_5()
    {
        OOPredication times5 = new OOTimes(5);
        assertTrue(times5.predicate(10));
        assertFalse(times5.predicate(11));
    }
    
    public void test_times_7()
    {
        OOPredication times7 = new OOTimes(7);
        assertTrue(times7.predicate(14));
        assertFalse(times7.predicate(15));
    }    
}
