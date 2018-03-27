package fayelab.tdd.stringtransformer.dummy.reverse;

import java.util.List;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.dummy.reverse.Trans.*;

public class BusinessLogicStub implements BusinessLogic
{
    @Override
    public List<String> getAllTranses()
    {
        return asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS);
    }

    @Override
    public String transform(String sourceStr, List<String> transes)
    {
        return "HELLO, WORLD.";
    }
}
