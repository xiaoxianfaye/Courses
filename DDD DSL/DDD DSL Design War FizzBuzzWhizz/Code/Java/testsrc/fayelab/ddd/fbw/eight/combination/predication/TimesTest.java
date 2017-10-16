package fayelab.ddd.fbw.eight.combination.predication;

import junit.framework.TestCase;

public class TimesTest extends TestCase
{
    public void test_times_3()
    {
        Predication pred = new Times(3);
        assertTrue(pred.predicate(6));
        assertFalse(pred.predicate(5));
    }
    
    public void test_times_5()
    {
        Predication pred = new Times(5);
        assertTrue(pred.predicate(10));
        assertFalse(pred.predicate(11));
    }
    
    public void test_times_7()
    {
        Predication pred = new Times(7);
        assertTrue(pred.predicate(14));
        assertFalse(pred.predicate(15));
    }    
}
