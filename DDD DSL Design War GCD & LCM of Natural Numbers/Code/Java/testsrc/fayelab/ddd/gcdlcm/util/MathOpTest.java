package fayelab.ddd.gcdlcm.util;

import static fayelab.ddd.gcdlcm.util.MathOp.*;

import junit.framework.TestCase;

public class MathOpTest extends TestCase
{
    public void test_isPrime()
    {
        assertTrue(isPrime(2));
        assertTrue(isPrime(3));
        assertFalse(isPrime(4));
        assertTrue(isPrime(5));
    }
    
    public void test_pow()
    {
        assertEquals(1, pow(1, 0));
        assertEquals(8, pow(2, 3));
    }
    
    public void test_isFactor()
    {
        assertTrue(isFactor(1, 3));
        assertFalse(isFactor(2, 3));
        assertTrue(isFactor(3, 3));
        assertTrue(isFactor(2, 6));
    }
}
