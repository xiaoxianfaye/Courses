package fayelab.ddd.gcdlcm.interpreter;

import static fayelab.ddd.gcdlcm.interpreter.GcdLcm.*;
import static java.util.Arrays.asList;

import java.util.List;

import junit.framework.TestCase;

public class GcdLcmTest extends TestCase
{
    public void test_extractSpecItems()
    {
        assertEquals(PF_EXTRACTION_TYPE_COMMON, getExtractionTypePf(SOLUTION_TYPE_GCD));
        assertEquals(POWER_EXTRACTION_TYPE_MIN, getExtractionTypePower(SOLUTION_TYPE_GCD));
        
        assertEquals(PF_EXTRACTION_TYPE_ALLNP, getExtractionTypePf(SOLUTION_TYPE_LCM));
        assertEquals(POWER_EXTRACTION_TYPE_MAX, getExtractionTypePower(SOLUTION_TYPE_LCM));
    }
    
    public void test_isPrime()
    {
        assertTrue(isPrime(2));
        assertTrue(isPrime(3));
        assertFalse(isPrime(4));
        assertTrue(isPrime(5));
    }
    
    public void test_primeFactorize()
    {
        assertEquals(asList(asList(2, 1)), primeFactorize(2));
        assertEquals(asList(asList(2, 1), asList(3, 1)), primeFactorize(6));
        assertEquals(asList(asList(2, 1), asList(3, 2), asList(5, 1)), primeFactorize(90));
        assertEquals(asList(asList(2, 2), asList(3, 1), asList(5, 1), asList(7, 1)), primeFactorize(420));
        assertEquals(asList(asList(2, 1), asList(3, 3), asList(5, 2), asList(7, 1)), primeFactorize(9450));
    }
    
    public void test_primeFactorizeList()
    {
        List<List<Integer>> expectedListOfPfs = 
                asList(asList(2, 3, 5), asList(2, 3, 5, 7), asList(2, 3, 5, 7));
        List<List<List<Integer>>> expectedListOfPfPs =
                asList(asList(asList(2, 1), asList(3, 2), asList(5, 1)),
                       asList(asList(2, 2), asList(3, 1), asList(5, 1), asList(7, 1)),
                       asList(asList(2, 1), asList(3, 3), asList(5, 2), asList(7, 1)));
        
        assertEquals(asList(expectedListOfPfs, expectedListOfPfPs), 
                primeFactorizeList(asList(90, 420, 9450)));
    }
}
