package fayelab.ddd.measure.vector.second;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class VecOp
{
    public static boolean equal(List<Integer> firstVec, List<Integer> secondVec)
    {
        validateNotNull(firstVec, secondVec);
        
        if(firstVec.size() != secondVec.size())
        {
            return false;
        }
        
        return operate(firstVec, secondVec, (x, y) -> x == y, Collectors.reducing(true, (b1, b2) -> b1 && b2));
    }
    
    public static List<Integer> add(List<Integer> augendVec, List<Integer> addendVec)
    {
        validate(augendVec, addendVec);
        
        return operate(augendVec, addendVec, (x, y) -> x + y,  Collectors.toList());
    }
    
    public static int dotProduct(List<Integer> rowVec, List<Integer> colVec)
    {
        validate(rowVec, colVec);
        
        return operate(rowVec, colVec, (x, y) -> x * y, Collectors.reducing(0, Integer::sum));
    }
    
    public static List<Integer> div(List<Integer> dividendVec, List<Integer> divisorVec)
    {
        validate(dividendVec, divisorVec);
        validateDivisorVec(divisorVec);
        
        List<Integer> result = zeroList(dividendVec.size());
        int carry = 0;
        
        for(int i = dividendVec.size() - 1; i > 0; i--)
        {
            int dividend = dividendVec.get(i) + carry;
            int divisor = divisorVec.get(i);
            int remainder = dividend % divisor;
            result.set(i, remainder);
            carry = dividend / divisor;
        }
        result.set(0, carry + dividendVec.get(0));

        return result;
    }
        
    public static List<Integer> scale(int c, List<Integer> vec)
    {
        validateNotNull(vec);
        
        return vec.stream().map(x -> c * x).collect(Collectors.toList());
    }

    static void validate(List<Integer> firstVec, List<Integer> secondVec)
    {
        validateNotNull(firstVec, secondVec);
        validateSameSize(firstVec, secondVec);
    }

    private static void validateNotNull(List<Integer> firstVec, List<Integer> secondVec)
    {
        if(null == firstVec)
        {
            throw new IllegalParametersException("The first vector is null.");
        }
        
        if(null == secondVec)
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

    private static void validateSameSize(List<Integer> firstVec, List<Integer> secondVec)
    {
        if(firstVec.size() != secondVec.size())
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
    
    private static <R1, A, R2> R2 operate(List<Integer> firstVec, List<Integer> secondVec, 
            BiFunction<Integer, Integer, R1> biFunction, Collector<? super R1, A, R2> collector)
    {
        List<R1> list = new ArrayList<>();
        
        for(int i = 0; i < firstVec.size(); i++)
        {
            list.add(biFunction.apply(firstVec.get(i), secondVec.get(i)));
        }
        
        return list.stream().collect(collector);
    }
    
    private static List<Integer> zeroList(int size)
    {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < size; i++)
        {
            result.add(0);
        }
        return result;
    }
}
