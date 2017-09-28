package fayelab.tdd.arithmetic.doublestack.pow;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

import java.util.Arrays;

public class DoubleStackProcessorTest extends TestCase
{
    private DoubleStackProcessor dsp;
    
    @Override
    protected void setUp()
    {
        dsp = new DoubleStackProcessor();
    }
    
    public void test_num_2()
    {        
        dsp.process2("12");
        
        assertEquals(asList(12), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack2());
    }
    
    public void test_num()
    {        
        dsp.process("12");
        
        assertEquals(asList(12), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_2()
    {        
        dsp.process2("12");
        dsp.process2("+"); //not operator char but string
        
        assertEquals(asList(12), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_plus()
    {        
        dsp.process("12");
        dsp.process("+"); //not operator char but string
        
        assertEquals(asList(12), dsp.dumpOperandStack());
        assertEquals(asList("+"), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_plus_2()
    {        
        dsp.process2("12");
        dsp.process2("+");
        dsp.process2("34");
        dsp.process2("+");
        
        assertEquals(asList(46), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_plus_num_multiple_2()
    {        
        dsp.process2("12");
        dsp.process2("+");
        dsp.process2("34");
        dsp.process2("*");
        
        assertEquals(asList(12, 34), dsp.dumpOperandStack());
        assertEquals(asList('+', '*'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_multiply_num_multiply_2()
    {
        dsp.process2("12");
        dsp.process2("*");
        dsp.process2("10");
        dsp.process2("*");
        
        assertEquals(Arrays.asList(120), dsp.dumpOperandStack());
        assertEquals(Arrays.asList('*'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_multiply_num_plus_2()
    {
        dsp.process2("12");
        dsp.process2("*");
        dsp.process2("10");
        dsp.process2("+");
        
        assertEquals(Arrays.asList(120), dsp.dumpOperandStack());
        assertEquals(Arrays.asList('+'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_plus_num_subtract_2()
    {
        dsp.process2("12");
        dsp.process2("+");
        dsp.process2("34");
        dsp.process2("-");
        
        assertEquals(Arrays.asList(46), dsp.dumpOperandStack());
        assertEquals(Arrays.asList('-'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_subtract_num_plus_2()
    {
        dsp.process2("34");
        dsp.process2("-");
        dsp.process2("12");
        dsp.process2("+");
        
        assertEquals(Arrays.asList(22), dsp.dumpOperandStack());
        assertEquals(Arrays.asList('+'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_subtract_num_divide_2()
    {
        dsp.process2("34");
        dsp.process2("-");
        dsp.process2("12");
        dsp.process2("/");
        
        assertEquals(Arrays.asList(34, 12), dsp.dumpOperandStack());
        assertEquals(Arrays.asList('-', '/'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_divide_num_plus_2()
    {
        dsp.process2("24");
        dsp.process2("/");
        dsp.process2("12");
        dsp.process2("+");
        
        assertEquals(Arrays.asList(2), dsp.dumpOperandStack());
        assertEquals(Arrays.asList('+'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_plus_num_rem_2()
    {
        dsp.process2("12");
        dsp.process2("+");
        dsp.process2("34");
        dsp.process2("%");
        
        assertEquals(Arrays.asList(12, 34), dsp.dumpOperandStack());
        assertEquals(Arrays.asList('+', '%'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_rem_num_plus_2()
    {
        dsp.process2("34");
        dsp.process2("%");
        dsp.process2("12");
        dsp.process2("+");
                
        assertEquals(asList(10), dsp.dumpOperandStack());
        assertEquals(asList('+'), dsp.dumpOperatorStack2());
    }
    
    public void test_num_plus_num_multiply_num_plus_2()
    {
        dsp.process2("12");
        dsp.process2("+");
        dsp.process2("10");
        dsp.process2("*");
        dsp.process2("34");
        dsp.process2("+");
        
        assertEquals(Arrays.asList(352), dsp.dumpOperandStack());
        assertEquals(Arrays.asList('+'), dsp.dumpOperatorStack2());
    }
}
