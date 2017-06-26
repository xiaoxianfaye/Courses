package fayelab.ddd.measure.arithmetic.first;

import static java.util.Arrays.asList;

import fayelab.ddd.measure.arithmetic.first.IllegalParametersException;
import fayelab.ddd.measure.arithmetic.first.MeasureSystemUI;
import junit.framework.TestCase;

public class MeasureSystemUITest extends TestCase
{
    private MeasureSystemUI ui;

    protected void setUp()
    {
        ui = new MeasureSystemUI("Imperial Length", "Mile 1760 Yard 3 Feet 12 Inch");
    }
    
    public void test_parseUnitConversionDesc()
    {
        MeasureSystemUI actual = new MeasureSystemUI("", " Mile  1760 Yard 3 Feet 12 Inch  ");
        
        assertEquals(asList(Integer.MAX_VALUE, 1760, 3, 12), actual.getStepFactors());
        assertEquals(asList("Mile", "Yard", "Feet", "Inch"), actual.getSysUnits());
    }
    
    public void test_parseUnitConversionDesc_when_validating_failed()
    {
        try
        {
            new MeasureSystemUI("Imperial Length", " ");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The unit conversion description is illegal.", e.getMessage());
        }
    }
    
    public void test_validateConversionDesc()
    {
        try
        {
            new MeasureSystemUI("", " Mile 1760  Yard 3 Feet 12 Inch  ");
            assertTrue(true);
        }
        catch(IllegalParametersException e)
        {
            assertTrue(false);
        }
        
        try
        {
            new MeasureSystemUI("", " Mile 1760  Yard  ");
            assertTrue(true);
        }
        catch(IllegalParametersException e)
        {
            assertTrue(false);
        }
        
        try
        {
            new MeasureSystemUI("", null);
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The unit conversion description is null.", e.getMessage());
        }
        
        try
        {
            new MeasureSystemUI("", "");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The unit conversion description is illegal.", e.getMessage());
        }
        
        try
        {
            new MeasureSystemUI("", "  ");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The unit conversion description is illegal.", e.getMessage());
        }
        
        try
        {
            new MeasureSystemUI("", "Mile Yard 3 Feet 12 Inch");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The unit conversion description is illegal.", e.getMessage());
        }
        
        try
        {
            new MeasureSystemUI("", "Mile 1760Yard 3 Feet 12 Inch");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The unit conversion description is illegal.", e.getMessage());
        }
    }
    
    public void test_parseQuantity()
    {
        assertEquals(asList(1, 2, 3, 4), ui.parseQuantity(" 1 Mile  2 Yard 3 Feet 4 Inch  "));
        assertEquals(asList(1, 2, 0, 4), ui.parseQuantity("1 Mile 4 Inch 2 Yard"));
    }
    
    public void test_parseQuantity_when_illegal_unit()
    {
        try
        {
            ui.parseQuantity("1 Meter 2 Decimeter");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The unit of the quantity is illegal.", e.getMessage());
        }
    }
    
    public void test_validateQuantity()
    {
        try
        {
            ui.validateQuantity(" 1 Mile 1760  Yard 3 Feet 12 Inch  ");
            assertTrue(true);
        }
        catch(IllegalParametersException e)
        {
            assertTrue(false);
        }
        
        try
        {
            ui.validateQuantity(" 16 Inch  ");
            assertTrue(true);
        }
        catch(IllegalParametersException e)
        {
            assertTrue(false);
        }
        
        try
        {
            ui.validateQuantity(null);
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The quantity is null.", e.getMessage());
        }
        
        try
        {
            ui.validateQuantity("");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The quantity is illegal.", e.getMessage());
        }
        
        try
        {
            ui.validateQuantity("  ");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The quantity is illegal.", e.getMessage());
        }
        
        try
        {
            ui.validateQuantity("Mile 2 Yard 3 Feet 12 Inch");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The quantity is illegal.", e.getMessage());
        }
        
        try
        {
            ui.validateQuantity("1 Mile 2Yard 3 Feet 12 Inch");
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The quantity is illegal.", e.getMessage());
        }
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
        assertEquals("2 Feet 0 Inch", ui.add("13 Inch", "11 Inch"));
    }
}
