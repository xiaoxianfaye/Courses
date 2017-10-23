package fayelab.ddd.fbwreloaded.functional;

import java.util.function.Predicate;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.functional.FizzBuzzWhizz.*;

public class FizzBuzzWhizzTest extends TestCase
{
    public void test_times()
    {
        Predicate<Integer> times3 = times(3);
        assertTrue(times3.test(3));
        assertTrue(times3.test(6));
        assertFalse(times3.test(5));
        
        Predicate<Integer> times5 = times(5);
        assertTrue(times5.test(5));
        assertTrue(times5.test(10));
        assertFalse(times5.test(7));
        
        Predicate<Integer> times7 = times(7);
        assertTrue(times7.test(7));
        assertTrue(times7.test(14));
        assertFalse(times7.test(3));
    }
}
