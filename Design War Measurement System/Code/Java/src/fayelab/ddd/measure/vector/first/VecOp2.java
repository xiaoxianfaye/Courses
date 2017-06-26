package fayelab.ddd.measure.vector.first;

import java.util.ArrayList;
import java.util.List;

public class VecOp2
{
    public static boolean equal(List<Integer> firstVec, List<Integer> secondVec)
    {
        validateNotNull(firstVec, secondVec);
        
        if(firstVec.size() != secondVec.size())
        {
            return false;
        }
        
        for(int i = 0; i < firstVec.size(); i++)
        {
            if(firstVec.get(i) != secondVec.get(i))
            {
                return false;
            }
        }
        
        return true;
    }
    
    public static List<Integer> add(List<Integer> augendVec, List<Integer> addendVec)
    {
        validate(augendVec, addendVec);
        
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < augendVec.size(); i++)
        {
            result.add(augendVec.get(i) + addendVec.get(i));
        }
        
        return result;
    }
    
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
