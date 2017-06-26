package fayelab.ddd.measure.arithmetic.second;

import java.util.ArrayList;
import java.util.List;

public class VecOp
{
    public static int dotProduct(List<Integer> rowVec, List<Integer> colVec)
    {
        validate(rowVec, colVec);
        
        int result = 0;
        for(int i = 0; i < rowVec.size(); i++)
        {
            result += rowVec.get(i) * colVec.get(i);
        }
        return result;
    }
    
    public static List<Integer> dotDiv(int baseValue, List<Integer> divisorVec)
    {
        validateNotNull(divisorVec);
        validateDivisorVec(divisorVec);
        
        List<Integer> result = new ArrayList<>();
        int remainder = baseValue;
        for(int component : divisorVec)
        {
            int quotient = remainder / component;
            remainder = remainder % component;
            result.add(quotient);
        }
        return result;
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
}
