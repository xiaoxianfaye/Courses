package fayelab.tdd.arithmetic.doublestack.rem;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

public class DoubleStackProcessorTest extends TestCase
{
    private DoubleStackProcessor dsp;
    
    @Override
    protected void setUp()
    {
        dsp = new DoubleStackProcessor();
    }

    public void test_num()
    {        
        dsp.process('3');
        
        assertEquals(asList(3), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus()
    {        
        dsp.process('3');
        dsp.process('+');
        
        assertEquals(asList(3), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_plus()
    {        
        dsp.process('3');
        dsp.process('+');
        dsp.process('2');
        dsp.process('+');
        
        assertEquals(asList(5), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_multiply()
    {        
        dsp.process('3');
        dsp.process('+');
        dsp.process('2');
        dsp.process('*');
        
        assertEquals(asList(3, 2), dsp.dumpOperandStack());
        assertEquals(asList('+', '*'), dsp.dumpOperatorStack());
    }
    
    public void test_num_multiply_num_multiply()
    {        
        dsp.process('3');
        dsp.process('*');
        dsp.process('2');
        dsp.process('*');
        
        assertEquals(asList(6), dsp.dumpOperandStack());
        assertEquals(asList('*'), dsp.dumpOperatorStack());
    }
    
    public void test_num_multiply_num_plus()
    {        
        dsp.process('3');
        dsp.process('*');
        dsp.process('2');
        dsp.process('+');
        
        assertEquals(asList(6), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_multiply_num_plus()
    {        
        dsp.process('3');
        dsp.process('+');
        dsp.process('2');
        dsp.process('*');
        dsp.process('3');
        dsp.process('+');
        
        assertEquals(asList(9), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_subtract()
    {        
        dsp.process('3');
        dsp.process('+');
        dsp.process('2');
        dsp.process('-');
        
        assertEquals(asList(5), dsp.dumpOperandStack());
        assertEquals(asList('-'), dsp.dumpOperatorStack());
    }
    
    public void test_num_subtract_num_plus()
    {        
        dsp.process('3');
        dsp.process('-');
        dsp.process('2');
        dsp.process('+');
        
        assertEquals(asList(1), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack());
    }
    
    public void test_num_subtract_num_divide()
    {        
        dsp.process('4');
        dsp.process('-');
        dsp.process('2');
        dsp.process('/');
        
        assertEquals(asList(4, 2), dsp.dumpOperandStack());
        assertEquals(asList('-', '/'), dsp.dumpOperatorStack());
    }
    
    public void test_num_divide_num_plus()
    {        
        dsp.process('4');
        dsp.process('/');
        dsp.process('2');
        dsp.process('+');
        
        assertEquals(asList(2), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack());
    }
    
    public void test_result_num()
    {
        dsp.process('3');
        
        assertEquals(3, dsp.result());
        assertEquals(asList(), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_result_calc_once()
    {
        dsp.process('1');
        dsp.process('+');
        dsp.process('2');
        
        assertEquals(3, dsp.result());
        assertEquals(asList(), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_result_calc_twice()
    {
        dsp.process('1');
        dsp.process('+');
        dsp.process('2');
        dsp.process('*');
        dsp.process('3');
        
        assertEquals(7, dsp.result());
        assertEquals(asList(), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_rem()
    {
        dsp.process('3');
        dsp.process('+');
        dsp.process('2');
        dsp.process('%');
                
        assertEquals(asList(3, 2), dsp.dumpOperandStack());
        assertEquals(asList('+', '%'), dsp.dumpOperatorStack());
    }
    
    public void test_num_rem_num_plus()
    {
        dsp.process('3');
        dsp.process('%');
        dsp.process('2');
        dsp.process('+');
                
        assertEquals(asList(1), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack());
    }
}
