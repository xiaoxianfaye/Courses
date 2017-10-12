package fayelab.tdd.stringtransformer.reverse;

import java.util.List;

public interface BusinessLogic
{
    List<String> getAllTransIds();

    String transform(String sourceStr, List<String> chainTransIds);
}
