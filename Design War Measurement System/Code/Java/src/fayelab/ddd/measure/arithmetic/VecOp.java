package fayelab.ddd.measure.arithmetic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class VecOp
{
    public static boolean equal(List<Integer> vec1, List<Integer> vec2)
    {
        validateNotNull(vec1, vec2);
        
        return vec1.equals(vec2);
    }
    
    public static List<Integer> add(List<Integer> vec1, List<Integer> vec2)
    {
        validate(vec1, vec2);
        
        return ListOp.zip(vec1, vec2).stream()
                                     .map(pair -> pair.get(0) + pair.get(1))
                                     .collect(Collectors.toList());
    }
    
    public static int dotProduct(List<Integer> rowVec, List<Integer> colVec)
    {
        validate(rowVec, colVec);
        
        return ListOp.zip(rowVec, colVec).stream()
                                         .map(pair -> pair.get(0) * pair.get(1))
                                         .reduce((x, y) -> x + y)
                                         .orElse(0);
    }
    
    public static List<Integer> div(List<Integer> vec1, List<Integer> vec2)
    {
        validate(vec1, vec2);
        validateDivisorVec(vec2);
        
        List<Integer> result = new ArrayList<>();
        List<Integer> rvec1 = ListOp.reverse(vec1);
        List<Integer> rvec2 = ListOp.reverse(vec2);
        List<List<Integer>> pairs = ListOp.zip(rvec1, rvec2);
        final int[] carry = new int[]{0};
        
        pairs.stream()
             .collect(() -> result,
                      (acc, pair) -> {
                          int dividend = pair.get(0) + carry[0];
                          int divisor = pair.get(1);
                          int remainder = dividend % divisor;
                          acc.add(remainder);
                          carry[0] = dividend / divisor;
                      },
                      (acc1, acc2) -> acc1.addAll(acc2)
                     );
        
        return ListOp.reverse(result);
    }
    
    public static List<Integer> dotDiv(int baseValue, List<Integer> vec)
    {
        validateNotNull(vec);
        validateDivisorVec(vec);
        
        List<Integer> result = new ArrayList<>();
        final int[] remainder = new int[]{baseValue};
        
        vec.stream()
           .collect(() -> result,
                    (acc, c) -> {
                        int quotient = remainder[0] / c;
                        remainder[0] = remainder[0] % c;
                        result.add(quotient);
                    },
                    (acc1, acc2) -> {
                        acc1.addAll(acc2);
                    });
        
        return result;
    }
    
    public static List<Integer> scale(int c, List<Integer> vec)
    {
        validateNotNull(vec);
        
        return vec.stream().map(x -> c * x).collect(Collectors.toList());
    }
    
    static void validate(List<Integer> vec1, List<Integer> vec2)
    {
        validateNotNull(vec1, vec2);
        validateSameSize(vec1, vec2);
    }
    
    private static void validateNotNull(List<Integer> vec1, List<Integer> vec2)
    {
        if(null == vec1)
        {
            throw new IllegalParametersException("The first vector is null.");
        }
        
        if(null == vec2)
        {
            throw new IllegalParametersException("The second vector is null.");
        }
    }
    
    private static void validateNotNull(List<Integer> vec)
    {
        if(null == vec)
        {
            throw new IllegalParametersException("The vector is null.");
        }
    }
    
    private static void validateSameSize(List<Integer> vec1, List<Integer> vec2)
    {
        if(vec1.size() != vec2.size())
        {
            throw new IllegalParametersException("The sizes of two vectors are different.");
        }
    }
    
    private static void validateDivisorVec(List<Integer> divisorVec)
    {
        if(divisorVec.isEmpty())
        {
            throw new IllegalParametersException("The divisor vector is empty.");
        }
        
        if(divisorVec.contains(0))
        {
            throw new IllegalParametersException("The divisor vector which includes 0 components is illegal.");
        }
    }
}
