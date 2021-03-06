package fayelab.ddd.measure.vector.scale;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

public class MeasureSystemTest extends TestCase
{
    private MeasureSystem ms;

    @Override
    protected void setUp()
    {
        ms = new MeasureSystem(asList(Integer.MAX_VALUE, 1760, 3, 12));
    }

    public void test_toBaseFactors()
    {
        assertEquals(asList(1760 * 3 * 12, 3 * 12, 12, 1), 
                     MeasureSystem.toBaseFactors(asList(Integer.MAX_VALUE, 1760, 3, 12)));
    }

    public void test_base()
    {
        assertEquals(63472, ms.base(asList(1, 2, 3, 4)));
        assertEquals(63396, ms.base(asList(1, 0, 3, 0)));
    }

    public void test_normalize()
    {
        assertEquals(asList(1, 4, 0, 1), ms.normalize(asList(0, 1762, 5, 13)));
    }

    public void test_equal()
    {
        assertTrue(ms.equal(asList(1, 2, 3, 4), asList(0, 0, 0, 63472)));
        assertTrue(ms.equal(asList(0, 1765, 0, 40), asList(1, 6, 0, 4)));
        assertFalse(ms.equal(asList(0, 1765, 0, 41), asList(1, 6, 0, 4)));
    }

    public void test_add()
    {
        assertEquals(asList(0, 0, 2, 0), ms.add(asList(0, 0, 0, 13), asList(0, 0, 0, 11)));
        assertEquals(asList(0, 3, 0, 0), ms.add(asList(0, 0, 3, 0), asList(0, 2, 0, 0)));
    }

    public void test_scale()
    {
        assertEquals(asList(0, 6, 0, 0), ms.scale(2, asList(0, 2, 3, 0)));
        assertEquals(asList(0, 6, 1, 2), ms.scale(2, asList(0, 2, 3, 7)));
    }
}
