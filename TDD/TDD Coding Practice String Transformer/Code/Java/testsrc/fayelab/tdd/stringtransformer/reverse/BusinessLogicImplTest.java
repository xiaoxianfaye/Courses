package fayelab.tdd.stringtransformer.reverse;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

public class BusinessLogicImplTest extends TestCase
{
    private BusinessLogicImpl impl;

    @Override
    protected void setUp()
    {
        impl = new BusinessLogicImpl();
    }
    
    public void test_transform_upper()
    {
        assertEquals("HELLO, WORLD!", impl.transform("Hello, world!", asList("Upper")));
    }
    
    public void test_transform_lower()
    {
        assertEquals("hello, world!", impl.transform("Hello, world!", asList("Lower")));
    }
    
    public void test_transform_trimPrefixSpaces()
    {
        assertEquals("Hello, world!  ", impl.transform("  Hello, world!  ", asList("TrimPrefixSpaces")));
        assertEquals("", impl.transform("  ", asList("TrimPrefixSpaces")));
    }
    
    public void test_transform_reverse()
    {
        assertEquals("  !dlrow ,olleH  ", impl.transform("  Hello, world!  ", asList("Reverse")));
    }
    
    public void test_transform()
    {
        assertEquals("hello, world!  ", 
                     impl.transform("  Hello, world!  ", asList("Upper", "Lower", "TrimPrefixSpaces")));
    }
    
    public void test_get_all_transIds()
    {
        assertEquals(asList("Upper", "Lower", "Reverse", "TrimPrefixSpaces"), impl.getAllTransIds());
    }
}
