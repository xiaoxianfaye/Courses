package fayelab.tdd.stringtransformer.instruction.original;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.instruction.original.Trans.*;

public class BusinessLogicImplTest extends TestCase
{
    private BusinessLogicImpl impl;

    @Override
    protected void setUp()
    {
        impl = new BusinessLogicImpl();
    }

    public void test_get_all_transes()
    {
        assertEquals(asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS), impl.getAllTranses());
    }
}
