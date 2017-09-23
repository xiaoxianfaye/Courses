package fayelab.ddd.gcdlcm.functional;

import java.util.List;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.ddd.gcdlcm.functional.GcdLcm.*;

public class GcdLcmTest extends TestCase
{
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
        List<List<Integer>> inputListOfPfs = asList(asList(2, 3, 5), asList(2, 3, 5, 7), asList(2, 3, 5, 7));
        
        assertEquals(asList(2, 3, 5), extractPrimeFactors(listOfPfs -> extractCommonPrimeFactors(listOfPfs), inputListOfPfs));
        assertEquals(asList(2, 3, 5, 7), extractPrimeFactors(listOfPfs -> extractAllnpPrimeFactors(listOfPfs), inputListOfPfs));
    }
    
    public void test_extractPowers()
    {
        List<Integer> pfs = asList(2, 3, 5, 7);
        List<List<List<Integer>>> listOfPfPs =
                asList(asList(asList(2, 1), asList(3, 2), asList(5, 1)),
                       asList(asList(2, 2), asList(3, 1), asList(5, 1), asList(7, 1)),
                       asList(asList(2, 1), asList(3, 3), asList(5, 2), asList(7, 1)));
        
        List<List<Integer>> expectedPfps = asList(asList(2, 1), asList(3, 1), asList(5, 1), asList(7, 0));
        assertEquals(expectedPfps, extractPowers(powers -> extractMinPower(powers), pfs, listOfPfPs));
        
        List<List<Integer>> expectedPfps2 = asList(asList(2, 2), asList(3, 3), asList(5, 2), asList(7, 1));
        assertEquals(expectedPfps2, extractPowers(powers -> extractMaxPower(powers), pfs, listOfPfPs));
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
