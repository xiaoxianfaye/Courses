package fayelab.ddd.gcdlcm.interpreter;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GcdLcm
{
    static final String SOLUTION_TYPE_GCD = "gcd";
    static final String SOLUTION_TYPE_LCM = "lcm";
    
    private static final String EXTRACTION_TYPE_PF = "prime_factor";
    private static final String EXTRACTION_TYPE_POWER = "power";
    
    static final String PF_EXTRACTION_TYPE_COMMON = "common";
    static final String PF_EXTRACTION_TYPE_ALLNP = "all_np";
    
    static final String POWER_EXTRACTION_TYPE_MIN = "min";
    static final String POWER_EXTRACTION_TYPE_MAX = "max";
    
    @SuppressWarnings("serial")
    private static final Map<String, Map<String, String>> spec = 
            new HashMap<String, Map<String, String>>(){
        {
            put(SOLUTION_TYPE_GCD, 
                new HashMap<String, String>(){
                    {
                        put(EXTRACTION_TYPE_PF, PF_EXTRACTION_TYPE_COMMON);
                        put(EXTRACTION_TYPE_POWER, POWER_EXTRACTION_TYPE_MIN);
                    }       
                }
            );
            
            put(SOLUTION_TYPE_LCM, 
                new HashMap<String, String>(){
                    {
                        put(EXTRACTION_TYPE_PF, PF_EXTRACTION_TYPE_ALLNP);
                        put(EXTRACTION_TYPE_POWER, POWER_EXTRACTION_TYPE_MAX);
                    }       
                }
            );
        }
    };
    
    private static int unifiedProc(String solutionType, List<Integer> numbers)
    {
        List<Object> pfResult = primeFactorizeList(numbers);
        List<Integer> pfs = extractPrimeFactors(getExtractionTypePf(solutionType), pfResult.get(0));
        List<List<Integer>> pfps = extractPowers(getExtractionTypePower(solutionType), pfs, pfResult.get(1));
        return productPowers(pfps);
    }

    static String getExtractionTypePf(String solutionType)
    {
        return extractSpecItem(solutionType, EXTRACTION_TYPE_PF);
    }
    
    static String getExtractionTypePower(String solutionType)
    {
        return extractSpecItem(solutionType, EXTRACTION_TYPE_POWER);
    }

    private static String extractSpecItem(String solutionType, String extractionType)
    {
        return spec.get(solutionType).get(extractionType);
    }
    
    static List<Object> primeFactorizeList(List<Integer> numbers)
    {
        List<List<List<Integer>>> listOfPfps = numbers.stream().map(n -> primeFactorize(n)).collect(toList());
        List<List<List<Integer>>> listOfPfsAndPs = listOfPfps.stream().map(pfps -> ListOp.unzip(pfps)).collect(toList());
        List<List<Integer>> listOfPfs = ListOp.unzip(listOfPfsAndPs).get(0);
        
        return asList(listOfPfs, listOfPfps);
    }

    static List<List<Integer>> primeFactorize(int n)
    {
        List<List<Integer>> pfps = new ArrayList<>();
        primeFactorize(n, pfps);
        return pfps;
    }
    
    private static void primeFactorize(int n, List<List<Integer>> pfps)
    {
        for(int i = 2; i <= n; i++)
        {
            if(isPrime(i) && n % i == 0)
            {
                accPrimeFactors(i, pfps);
                primeFactorize(n / i, pfps);
                break;
            }
        }
    }
    
    private static void accPrimeFactors(int pf, List<List<Integer>> pfps)
    {   
        Optional<List<Integer>> opfp = ListOp.keyFind(pf, pfps);
        if(opfp.isPresent())
        {
            ListOp.keyReplace(pf, pfps, asList(pf, opfp.get().get(1) + 1));
        }
        else
        {
            pfps.add(asList(pf, 1));
        }
    }

    static boolean isPrime(int n)
    {
        for(int i = 2; i < n; i++)
        {
            if(n % i == 0)
            {
                return false;
            }
        }
        
        return true;
    }
    
    static List<Integer> extractPrimeFactors(String extractionTypePf, Object object)
    {
        // TODO Auto-generated method stub
        return null;
    }

    static List<List<Integer>> extractPowers(String extractionTypePower, List<Integer> pfs, Object object)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    static int productPowers(List<List<Integer>> pfps)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
