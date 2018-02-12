package fayelab.tdd.stringtransformer.original;

import junit.framework.TestCase;

import static fayelab.tdd.stringtransformer.original.Transformer.*;
import static java.util.Arrays.asList;

public class BusinessLogicImplTest extends TestCase
{
    private BusinessLogicImpl impl;
    
    @Override
    protected void setUp()
    {
        impl = new BusinessLogicImpl();
    }
    
    public void test_get_all_transIds()
    {
        assertEquals(asList(UPPER, LOWER, TRIM_PREFIX_SPACES), impl.getAllTransformers());
    }
}
