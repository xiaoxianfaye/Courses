package fayelab.ddd.gcdlcm.interpreter;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import fayelab.ddd.gcdlcm.util.ListOp;
import fayelab.ddd.gcdlcm.util.MathOp;

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
    
    public static int gcd(int...numbers)
    {
        return unifiedProc(SOLUTION_TYPE_GCD, ListOp.of(numbers));
    }
    
    public static int lcm(int...numbers)
    {
        return unifiedProc(SOLUTION_TYPE_LCM, ListOp.of(numbers));
    }

    @SuppressWarnings("unchecked")
    private static int unifiedProc(String solutionType, List<Integer> numbers)
    {
        List<Object> pfResult = primeFactorizeList(numbers);
        List<Integer> pfs = extractPrimeFactors(getExtractionTypePf(solutionType), (List<List<Integer>>)pfResult.get(0));
        List<List<Integer>> pfps = extractPowers(getExtractionTypePower(solutionType), pfs, (List<List<List<Integer>>>)pfResult.get(1));
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
        IntStream.range(2, n + 1).filter(i -> MathOp.isPrime(i) && MathOp.isFactor(i, n)).findFirst()
                 .ifPresent(pf -> {
                     accPrimeFactors(pf, pfps);
                     primeFactorize(n / pf, pfps);
                 });
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
    
    static List<Integer> extractPrimeFactors(String extractionTypePf, List<List<Integer>> pfps)
    {
        if(PF_EXTRACTION_TYPE_COMMON.equals(extractionTypePf))
        {
            return ListOp.intersect(pfps);
        }
        
        return ListOp.union(pfps);
    }

    static List<List<Integer>> extractPowers(String extractionTypePower, List<Integer> pfs, List<List<List<Integer>>> listOfPfps)
    {
        return pfs.stream()
                  .map(pf -> asList(pf, extractPower(extractionTypePower, extractPfPowers(pf, listOfPfps))))
                  .collect(toList());
    }
    
    private static List<Integer> extractPfPowers(int pf, List<List<List<Integer>>> listOfPfps)
    {
        return listOfPfps.stream()
                         .map(pfps -> extractPfPower(pf, pfps))
                         .collect(toList());
    }
    
    private static int extractPfPower(int pf, List<List<Integer>> pfps)
    {
        Optional<List<Integer>> opfp = ListOp.keyFind(pf, pfps);
        return opfp.isPresent() ? opfp.get().get(1) : 0;
    }
    
    private static int extractPower(String extractionTypePower, List<Integer> powers)
    {
        if(POWER_EXTRACTION_TYPE_MIN.equals(extractionTypePower))
        {
            return powers.stream().min(Integer::compare).orElse(0);
        }
        
        return powers.stream().max(Integer::compare).orElse(0);
    }
    
    static int productPowers(List<List<Integer>> pfps)
    {
        return pfps.stream().map(pfp -> MathOp.pow(pfp.get(0), pfp.get(1)))
                            .reduce((x, y) -> x * y).orElse(Integer.MIN_VALUE);
    }
}
