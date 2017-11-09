package fayelab.ddd.fbwreloaded.oo.predication;

import junit.framework.TestCase;

public class TimesTest extends TestCase
{
    public void test_times_3()
    {
        Predication times3 = new Times(3);
        assertTrue(times3.predicate(6));
        assertFalse(times3.predicate(5));
    }
    
    public void test_times_5()
    {
        Predication times5 = new Times(5);
        assertTrue(times5.predicate(10));
        assertFalse(times5.predicate(11));
    }
    
    public void test_times_7()
    {
        Predication times7 = new Times(7);
        assertTrue(times7.predicate(14));
        assertFalse(times7.predicate(15));
    }    
}
