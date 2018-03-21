package fayelab.tdd.stringtransformer.dummy.original;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.dummy.original.Trans.*;

public class BusinessLogicImplTest extends TestCase
{
    public void test_get_all_transes()
    {
        BusinessLogicImpl impl = new BusinessLogicImpl();
        assertEquals(asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS), impl.getAllTranses());
    }
}
