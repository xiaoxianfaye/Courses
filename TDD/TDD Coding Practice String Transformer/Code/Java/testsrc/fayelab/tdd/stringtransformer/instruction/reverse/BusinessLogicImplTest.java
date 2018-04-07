package fayelab.tdd.stringtransformer.instruction.reverse;

import junit.framework.TestCase;

import java.util.List;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.instruction.reverse.Trans.*;

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
        assertEquals(asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS, REVERSE_TRANS), impl.getAllTranses());
    }

    public void test_transform_upper()
    {
        assertEquals("HELLO, WORLD.", impl.transform("Hello, world.", asList(UPPER_TRANS)));
    }

    public void test_transform_lower()
    {
        assertEquals("hello, world.", impl.transform("Hello, world.", asList(LOWER_TRANS)));
    }

    public void test_transform_trimPrefixSpaces()
    {
        List<String> transes = asList(TRIM_PREFIX_SPACES_TRANS);

        assertEquals("Hello, world.  ", impl.transform("  Hello, world.  ", transes));
        assertEquals("", impl.transform("  ", transes));
        assertEquals("Hello, world.  ", impl.transform("Hello, world.  ", transes));
    }

    public void test_transform_reverse()
    {
        assertEquals("  .dlrow ,olleH  ", impl.transform("  Hello, world.  ", asList(REVERSE_TRANS)));
    }

    public void test_transform()
    {
        assertEquals("hello, world.  ", 
                     impl.transform("  Hello, world.  ", asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS)));
    }
}
