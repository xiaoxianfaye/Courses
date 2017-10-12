package fayelab.tdd.stringtransformer.original;

import java.util.List;

public interface BusinessLogic
{
    List<String> getAllTransIds();

    String transform(String sourceStr, List<String> chainTransIds);
}
