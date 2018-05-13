package fayelab.ddd.measure.vector.original;

import java.util.List;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

public class MeasureSystemUITest extends TestCase
{
    private MeasureSystemUI ui;

    @Override
    protected void setUp()
    {
        ui = new MeasureSystemUI("Imperial Length", "Mile 1760 Yard 3 Feet 12 Inch");
    }

    public void test_parseUnitConversionDesc()
    {
        List<List<?>> actual = MeasureSystemUI.parseUnitConversionDesc(" Mile  1760 Yard 3 Feet 12 Inch  ");
        assertEquals(2, actual.size());
        assertEquals(asList(Integer.MAX_VALUE, 1760, 3, 12), actual.get(0));
        assertEquals(asList("Mile", "Yard", "Feet", "Inch"), actual.get(1));

        actual = MeasureSystemUI.parseUnitConversionDesc2(" Mile  1760 Yard 3 Feet 12 Inch  ");
        assertEquals(2, actual.size());
        assertEquals(asList(Integer.MAX_VALUE, 1760, 3, 12), actual.get(0));
        assertEquals(asList("Mile", "Yard", "Feet", "Inch"), actual.get(1));
    }

    public void test_parseQuantityDesc()
    {
        assertEquals(asList(1, 2, 3, 4), ui.parseQuantityDesc(" 1 Mile  2 Yard 3 Feet 4 Inch  "));
        assertEquals(asList(1, 2, 0, 4), ui.parseQuantityDesc("1 Mile 4 Inch 2 Yard"));
        assertEquals(asList(1, 2, 0, 4), ui.parseQuantityDesc("1 Mile 2 Yard 4 Inch"));
        assertEquals(asList(0, 0, 3, 0), ui.parseQuantityDesc("3 Feet"));
    }

    public void test_equal()
    {
        assertTrue(ui.equal("1 Mile 2 Yard 3 Feet 4 Inch", "63472 Inch"));
        assertTrue(ui.equal("1765 Yard 40 Inch", "1 Mile 6 Yard 4 Inch"));
        assertFalse(ui.equal("1765 Yard 41 Inch", "1 Mile 6 Yard 4 Inch"));
    }

    public void test_format()
    {
        assertEquals("1 Mile 3 Yard 4 Inch", ui.format(asList(1, 3, 0, 4)));
        assertEquals("2 Feet 0 Inch", ui.format(asList(0, 0, 2, 0)));
    }

    public void test_add()
    {
        assertEquals("2 Feet 0 Inch", ui.add("13 Inch", "11 Inch"));
        assertEquals("3 Yard 0 Inch", ui.add("3 Feet", "2 Yard"));
    }

    public void test_baseFormat()
    {
        assertEquals("63472 Inch", ui.baseFormat(asList(1, 3, 0, 4)));
    }
}
