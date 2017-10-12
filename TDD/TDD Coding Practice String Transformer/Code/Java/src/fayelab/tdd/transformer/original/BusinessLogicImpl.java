package fayelab.tdd.transformer.original;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BusinessLogicImpl
{
    private static Map<String, Function<String, String>> transIdAndFuncMap = null;
    
    static
    {
        transIdAndFuncMap = new HashMap<>();
        transIdAndFuncMap.put("Upper", str -> upper(str));
        transIdAndFuncMap.put("Lower", str -> lower(str));
        transIdAndFuncMap.put("TrimPrefixSpaces", str -> trimPrefixSpaces(str));
    }
    
    public String transform(String sourceStr, List<String> transIds)
    {
        return transIds.stream().reduce(sourceStr, 
                (resultStr, transId) -> transIdAndFuncMap.get(transId).apply(resultStr));
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
        return firstNonSpaceCharIdx == -1 ? str : str.substring(firstNonSpaceCharIdx);
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
}
