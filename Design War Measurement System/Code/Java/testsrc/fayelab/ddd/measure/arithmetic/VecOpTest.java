package fayelab.ddd.measure.arithmetic;

import static fayelab.ddd.measure.arithmetic.VecOp.*;
import static java.util.Arrays.asList;

import fayelab.ddd.measure.arithmetic.IllegalParametersException;
import junit.framework.TestCase;

public class VecOpTest extends TestCase
{
    public void test_equal()
    {
        assertTrue(equal(asList(1, 2, 3), asList(1, 2, 3)));
        assertFalse(equal(asList(1, 2), asList(1, 2, 3)));
        assertTrue(equal(asList(), asList()));
    }
    
    public void test_equal_when_validating_failed()
    {
        try
        {
            equal(null, asList(1, 2));
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The first vector is null.", e.getMessage());
        }
    }
    
    public void test_add()
    {
        assertEquals(asList(5, 7, 9), add(asList(1, 2, 3), asList(4, 5, 6)));
        assertEquals(asList(), add(asList(), asList()));
    }
    
    public void test_add_when_validating_failed()
    {
        try
        {
            add(asList(1, 2), asList(3, 4, 5));
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The sizes of two vectors are different.", e.getMessage());
        }
    }
    
    public void test_dotProduct()
    {
        assertEquals(102, dotProduct(asList(1, 0, 2), asList(100, 10, 1)));
        assertEquals(0, dotProduct(asList(), asList()));
    }
    
    public void test_dotProduct_when_validating_failed()
    {
        try
        {
            dotProduct(asList(1, 2), asList(3, 4, 5));
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The sizes of two vectors are different.", e.getMessage());
        }
    }
    
    public void test_div()
    {
        assertEquals(asList(0, 0, 2, 0), div(asList(0, 0, 0, 24), asList(Integer.MAX_VALUE, 1760, 3, 12)));
        assertEquals(asList(2, 6, 0, 4), div(asList(1, 1765, 0, 40), asList(Integer.MAX_VALUE, 1760, 3, 12)));
    }
    
    public void test_div_when_validating_failed()
    {
        try
        {
            div(asList(1, 2), asList(3, 4, 5));
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The sizes of two vectors are different.", e.getMessage());
        }
        
        try
        {
            div(asList(), asList());
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The divisor vector is empty.", e.getMessage());
        }
        
        try
        {
            div(asList(1, 2), asList(0, 4));
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The divisor vector which includes 0 components is illegal.", e.getMessage());
        }
    }
    
    public void test_dotDiv()
    {   
        assertEquals(asList(1, 0, 2), dotDiv(102, asList(100, 10, 1)));    
    }
    
    public void test_dotDiv_when_validating_failed()
    {
        try
        {
            dotDiv(0, null);
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The vector is null.", e.getMessage());
        }
        
        try
        {
            dotDiv(0, asList());
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The divisor vector is empty.", e.getMessage());
        }
        
        try
        {
            dotDiv(0, asList(0, 4));
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The divisor vector which includes 0 components is illegal.", e.getMessage());
        }
    }
    
    public void test_scale()
    {
        assertEquals(asList(2, 4, 6), scale(2, asList(1, 2, 3)));
        assertEquals(asList(0, 0), scale(0, asList(1, 2)));
        assertEquals(asList(), scale(2, asList()));
    }

    public void test_scale_when_validating_failed()
    {
        try
        {
            scale(2, null);
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The vector is null.", e.getMessage());
        }
    }
    
    public void test_validate()
    {
        try
        {
            validate(null, asList());
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The first vector is null.", e.getMessage());
        }
        
        try
        {
            validate(asList(), null);
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The second vector is null.", e.getMessage());
        }
        
        try
        {
            validate(asList(1, 2), asList(3, 4, 5));
            assertTrue(false);
        }
        catch(IllegalParametersException e)
        {
            assertEquals("The sizes of two vectors are different.", e.getMessage());
        }
    }
}
