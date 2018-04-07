package fayelab.tdd.stringtransformer.instruction.reverse;

import java.util.List;

public interface BusinessLogic
{
    List<String> getAllTranses();

    String transform(String sourceStr, List<String> transes);
}
