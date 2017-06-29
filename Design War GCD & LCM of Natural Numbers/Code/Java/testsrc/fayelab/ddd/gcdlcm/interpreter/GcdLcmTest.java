package fayelab.ddd.gcdlcm.interpreter;

import static fayelab.ddd.gcdlcm.interpreter.GcdLcm.*;
import static java.util.Arrays.asList;

import java.util.List;

import junit.framework.TestCase;

public class GcdLcmTest extends TestCase
{
    public void test_getExtractionTypePf()
    {
        assertEquals(PF_EXTRACTION_TYPE_COMMON, getExtractionTypePf(SOLUTION_TYPE_GCD));
        assertEquals(POWER_EXTRACTION_TYPE_MIN, getExtractionTypePower(SOLUTION_TYPE_GCD));
    }
    
    public void test_getExtractionTypePower()
    {
        assertEquals(PF_EXTRACTION_TYPE_ALLNP, getExtractionTypePf(SOLUTION_TYPE_LCM));
        assertEquals(POWER_EXTRACTION_TYPE_MAX, getExtractionTypePower(SOLUTION_TYPE_LCM));
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
    
    public void test_extractPrimeFactors()
    {
        List<List<Integer>> listOfPfs = asList(asList(2, 3, 5), asList(2, 3, 5, 7), asList(2, 3, 5, 7));
        
        assertEquals(asList(2, 3, 5), extractPrimeFactors(PF_EXTRACTION_TYPE_COMMON, listOfPfs));
        assertEquals(asList(2, 3, 5, 7), extractPrimeFactors(PF_EXTRACTION_TYPE_ALLNP, listOfPfs));
    }
    
    public void test_extractPowers()
    {
        List<Integer> pfs = asList(2, 3, 5);
        List<List<List<Integer>>> listOfPfPs =
                asList(asList(asList(2, 1), asList(3, 2), asList(5, 1)),
                       asList(asList(2, 2), asList(3, 1), asList(5, 1), asList(7, 1)),
                       asList(asList(2, 1), asList(3, 3), asList(5, 2), asList(7, 1)));
        
        List<List<Integer>> expectedPfps = asList(asList(2, 1), asList(3, 1), asList(5, 1));
        assertEquals(expectedPfps, extractPowers(POWER_EXTRACTION_TYPE_MIN, pfs, listOfPfPs));
        
        List<List<Integer>> expectedPfps2 = asList(asList(2, 2), asList(3, 3), asList(5, 2));
        assertEquals(expectedPfps2, extractPowers(POWER_EXTRACTION_TYPE_MAX, pfs, listOfPfPs));
    } 
    
    public void test_productPowers()
    {
        assertEquals(30, productPowers(asList(asList(2, 1), asList(3, 1), asList(5, 1))));
        assertEquals(18900, productPowers(asList(asList(2, 2), asList(3, 3), asList(5, 2), asList(7, 1))));
        
        assertEquals(Integer.MIN_VALUE, productPowers(asList()));
    }
    
    public void test_gcd()
    {
        assertEquals(30, gcd(90, 420, 9450));
    }

    public void test_lcm()
    {
        assertEquals(18900, lcm(90, 420, 9450));
    }
}
