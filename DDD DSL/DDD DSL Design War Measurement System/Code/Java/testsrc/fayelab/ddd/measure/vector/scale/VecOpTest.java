package fayelab.ddd.measure.vector.scale;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.ddd.measure.vector.scale.VecOp.*;

public class VecOpTest extends TestCase
{
    public void test_equal()
    {
        assertTrue(equal(asList(1, 2, 3), asList(1, 2, 3)));
        assertTrue(equal(asList(), asList()));
        assertFalse(equal(asList(1, 2), asList(1, 2, 3)));
        assertFalse(equal(asList(1, 3, 4), asList(1, 2, 3)));
    }

    public void test_add()
    {
        assertEquals(asList(5, 7, 9), add(asList(1, 2, 3), asList(4, 5, 6)));
        assertEquals(asList(), add(asList(), asList()));
    }

    public void test_dotProduct()
    {
        assertEquals(102, dotProduct(asList(1, 0, 2), asList(100, 10, 1)));
        assertEquals(0, dotProduct(asList(), asList()));
    }

    public void test_div()
    {
        assertEquals(asList(0, 0, 2, 0), div(asList(0, 0, 0, 24), asList(Integer.MAX_VALUE, 1760, 3, 12)));
        assertEquals(asList(2, 6, 0, 4), div(asList(1, 1765, 0, 40), asList(Integer.MAX_VALUE, 1760, 3, 12)));
    }

    public void test_dotDiv()
    {
        assertEquals(asList(1, 0, 2), dotDiv(102, asList(100, 10, 1)));
    }

    public void test_scale()
    {
        assertEquals(asList(2, 4, 6), scale(2, asList(1, 2, 3)));
        assertEquals(asList(0, 0), scale(0, asList(1, 2)));
        assertEquals(asList(), scale(2, asList()));
    }
}
