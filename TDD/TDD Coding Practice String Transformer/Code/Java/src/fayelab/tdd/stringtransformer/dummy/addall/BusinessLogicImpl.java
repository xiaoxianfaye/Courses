package fayelab.tdd.stringtransformer.dummy.addall;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.dummy.addall.Trans.*;

public class BusinessLogicImpl implements BusinessLogic
{
    private static Map<String, Function<String, String>> TRANS_FUNC_MAP = null;

    static
    {
        TRANS_FUNC_MAP = new LinkedHashMap<>();
        TRANS_FUNC_MAP.put(UPPER_TRANS, BusinessLogicImpl::upper);
        TRANS_FUNC_MAP.put(LOWER_TRANS, BusinessLogicImpl::lower);
        TRANS_FUNC_MAP.put(TRIM_PREFIX_SPACES_TRANS, BusinessLogicImpl::trimPrefixSpaces);
    }

    @Override
    public List<String> getAllTranses()
    {
        return asList(TRANS_FUNC_MAP.keySet().toArray(new String[] {}));
    }

    @Override
    public String transform(String sourceStr, List<String> transes)
    {
        return transes.stream()
                      .reduce(sourceStr, (resultStr, trans) -> TRANS_FUNC_MAP.get(trans).apply(resultStr));

//        String resultStr = sourceStr;
//
//        for(String trans : transes)
//        {
//            resultStr = TRANS_FUNC_MAP.get(trans).apply(resultStr);
//        }
//
//        return resultStr;
    }

    private static String upper(String str)
    {
        return str.toUpperCase();
    }

    private static String lower(String str)
    {
        return str.toLowerCase();
    }

    private static String trimPrefixSpaces(String str)
    {
        int firstNonSpaceCharIndex = findFirstNonSpaceCharIndex(str);
        return firstNonSpaceCharIndex == -1 ? "" : str.substring(firstNonSpaceCharIndex);
    }

    private static int findFirstNonSpaceCharIndex(String str)
    {
        return str.chars()
                  .mapToObj(c -> asList(str.indexOf(c), c))
                  .filter(indexAndChar -> indexAndChar.get(1) != ' ')
                  .mapToInt(indexAndChar -> indexAndChar.get(0))
                  .findFirst()
                  .orElse(-1);

//        int firstNonSpaceCharIndex = -1;
//        for(int i = 0; i < str.length(); i++)
//        {
//            if(str.charAt(i) != ' ')
//            {
//                firstNonSpaceCharIndex = i;
//                break;
//            }
//        }
//        return firstNonSpaceCharIndex;
    }
}
