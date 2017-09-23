package fayelab.ddd.measure.vector;

import static fayelab.ddd.measure.vector.ListOp.*;
import static java.util.Arrays.asList;

import junit.framework.TestCase;

public class ListOpTest extends TestCase
{
    public void test_zip()
    {
        assertEquals(asList(asList(1, 4), asList(2, 5), asList(3, 6)), zip(asList(1, 2, 3), asList(4, 5, 6)));
        assertEquals(asList(asList(1, 4), asList(2, 5)), zip(asList(1, 2), asList(4, 5, 6)));
        assertEquals(asList(asList(1, 4), asList(2, 5)), zip(asList(1, 2, 3), asList(4, 5)));
        assertEquals(asList(), zip(asList(), asList()));
    }
    
    public void test_reverse()
    {
        assertEquals(asList(3, 2, 1), reverse(asList(1, 2, 3)));
        assertEquals(asList(), reverse(asList()));
    }
}
