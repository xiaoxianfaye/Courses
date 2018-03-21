package fayelab.tdd.stringtransformer.dummy.original;

import java.util.List;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.dummy.original.Trans.*;

public class BusinessLogicImpl implements BusinessLogic
{
    @Override
    public List<String> getAllTranses()
    {
        return asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS);
    }
}
