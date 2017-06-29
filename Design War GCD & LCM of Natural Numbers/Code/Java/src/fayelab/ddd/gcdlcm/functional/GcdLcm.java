package fayelab.ddd.gcdlcm.functional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import fayelab.ddd.gcdlcm.util.ListOp;
import fayelab.ddd.gcdlcm.util.MathOp;

public class GcdLcm
{
    static final String SOLUTION_TYPE_GCD = "gcd";
    static final String SOLUTION_TYPE_LCM = "lcm";
    
    private static final String EXTRACTION_TYPE_PF = "prime_factor";
    private static final String EXTRACTION_TYPE_POWER = "power";
    
    @SuppressWarnings("serial")
    private static final Map<String, Map<String, Object>> spec = 
            new HashMap<String, Map<String, Object>>(){
        {
            put(SOLUTION_TYPE_GCD, 
                new HashMap<String, Object>(){
                    {
                        put(EXTRACTION_TYPE_PF, (Function<List<List<Integer>>, List<Integer>>)(listOfPfs -> extractCommonPrimeFactors(listOfPfs)));
                        put(EXTRACTION_TYPE_POWER, (Function<List<Integer>, Integer>)(powers -> extractMinPower(powers)));
                    }       
                }
            );
            
            put(SOLUTION_TYPE_LCM, 
                new HashMap<String, Object>(){
                    {
                        put(EXTRACTION_TYPE_PF, (Function<List<List<Integer>>, List<Integer>>)(listOfPfs -> extractAllnpPrimeFactors(listOfPfs)));
                        put(EXTRACTION_TYPE_POWER, (Function<List<Integer>, Integer>)(powers -> extractMaxPower(powers)));
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
        List<Integer> pfs = extractPrimeFactors(getExtractionTypePfFunc(solutionType), (List<List<Integer>>)pfResult.get(0));
        List<List<Integer>> pfps = extractPowers(getExtractionTypePowerFunc(solutionType), pfs, (List<List<List<Integer>>>)pfResult.get(1));
        return productPowers(pfps);
    }

    static Function<List<List<Integer>>, List<Integer>> getExtractionTypePfFunc(String solutionType)
    {
        return extractSpecItem(solutionType, EXTRACTION_TYPE_PF);
    }
    
    static Function<List<Integer>, Integer> getExtractionTypePowerFunc(String solutionType)
    {
        return extractSpecItem(solutionType, EXTRACTION_TYPE_POWER);
    }

    @SuppressWarnings("unchecked")
    private static <T, R> Function<T, R> extractSpecItem(String solutionType, String extractionType)
    {
        return (Function<T, R>)spec.get(solutionType).get(extractionType);
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
        IntStream.range(2, n + 1).filter(i -> MathOp.isPrime(i) && n % i == 0).findFirst()
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
    
    static List<Integer> extractPrimeFactors(Function<List<List<Integer>>, List<Integer>> extractionTypePfFunc, List<List<Integer>> pfps)
    {
        return extractionTypePfFunc.apply(pfps);
    }
    
    static List<Integer> extractCommonPrimeFactors(List<List<Integer>> pfps)
    {
        return ListOp.intersect(pfps);
    }
    
    static List<Integer> extractAllnpPrimeFactors(List<List<Integer>> pfps)
    {
        return ListOp.union(pfps);
    }

    static List<List<Integer>> extractPowers(Function<List<Integer>, Integer> extractPowerFunc, List<Integer> pfs, List<List<List<Integer>>> listOfPfps)
    {
        return pfs.stream()
                  .map(pf -> {
                      List<Integer> powers = new ArrayList<>();
                      listOfPfps.stream()
                                .collect(() -> powers, 
                                         (acc, pfps) -> {
                                             Optional<List<Integer>> opfp = ListOp.keyFind(pf, pfps);
                                             if(opfp.isPresent())
                                             {
                                                 acc.add(opfp.get().get(1));
                                             }
                                             else
                                             {
                                                 acc.add(0);
                                             }
                                         },
                                         (acc1, acc2) -> {
                                             acc1.addAll(acc2);
                                         });
                      return asList(pf, extractPowerFunc.apply(powers));
                  })
                  .collect(toList());
    }
    
    static int extractMinPower(List<Integer> powers)
    {
        return powers.stream().min(Integer::compare).orElse(0);
    }
    
    static int extractMaxPower(List<Integer> powers)
    {
        return powers.stream().max(Integer::compare).orElse(0);
    }
    
    static int productPowers(List<List<Integer>> pfps)
    {
        return pfps.stream().map(pfp -> MathOp.pow(pfp.get(0), pfp.get(1)))
                            .reduce((x, y) -> x * y).orElse(Integer.MIN_VALUE);
    }
}

