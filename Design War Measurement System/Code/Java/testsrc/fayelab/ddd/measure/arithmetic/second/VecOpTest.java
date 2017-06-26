package fayelab.ddd.measure.arithmetic.second;

import static fayelab.ddd.measure.arithmetic.second.VecOp.*;
import static java.util.Arrays.asList;

import fayelab.ddd.measure.arithmetic.second.IllegalParametersException;
import junit.framework.TestCase;

public class VecOpTest extends TestCase
{
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
