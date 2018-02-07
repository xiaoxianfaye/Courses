package fayelab.tdd.stringtransformer.addall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class BusinessLogicImpl implements BusinessLogic
{
    private static Map<String, Function<String, String>> transIdAndFuncMap = null;
    
    static
    {
        transIdAndFuncMap = new HashMap<>();
        transIdAndFuncMap.put("Upper", str -> upper(str));
        transIdAndFuncMap.put("Lower", str -> lower(str));
        transIdAndFuncMap.put("TrimPrefixSpaces", str -> trimPrefixSpaces(str));
        transIdAndFuncMap.put("Reverse", str -> reverse(str));
    }
    
    @Override
    public String transform(String sourceStr, List<String> transIds)
    {
        return transIds.stream().reduce(sourceStr, 
                (resultStr, transId) -> transIdAndFuncMap.get(transId).apply(resultStr));
    }

    @Override
    public List<String> getAllTransIds()
    {
        return transIdAndFuncMap.keySet().stream().collect(toList());
    }

    private static String lower(String str)
    {
        return str.toLowerCase();
    }

    private static String upper(String str)
    {
        return str.toUpperCase();
    }

    private static String trimPrefixSpaces(String str)
    {
        int firstNonSpaceCharIdx = findFirstNonSpaceCharIdx(str);
        return firstNonSpaceCharIdx == -1 ? "" : str.substring(firstNonSpaceCharIdx);
    }

    private static int findFirstNonSpaceCharIdx(String str)
    {
        int firstNonSpaceCharIdx = -1;
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) != ' ')
            {
                firstNonSpaceCharIdx = i;
                break;
            }
        }
        return firstNonSpaceCharIdx;
    }
    
    private static String reverse(String str)
    {
        return new StringBuffer(str).reverse().toString();
    }
}
