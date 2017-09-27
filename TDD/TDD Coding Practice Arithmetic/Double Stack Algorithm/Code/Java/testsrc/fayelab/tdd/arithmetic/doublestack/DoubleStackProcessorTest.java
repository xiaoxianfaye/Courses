package fayelab.tdd.arithmetic.doublestack;

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
}
