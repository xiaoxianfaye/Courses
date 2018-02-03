package fayelab.tdd.arithmetic.doublestack.negint;

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
        dsp.process("12");
        
        assertEquals(asList(12), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus()
    {        
        dsp.process("12");
        dsp.process("+"); //not operator char but string
        
        assertEquals(asList(12), dsp.dumpOperandStack());
        assertEquals(asList("+"), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_plus()
    {        
        dsp.process("12");
        dsp.process("+");
        dsp.process("34");
        dsp.process("+");
        
        assertEquals(asList(46), dsp.dumpOperandStack());
        assertEquals(asList("+"), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_multiply()
    {        
        dsp.process("12");
        dsp.process("+");
        dsp.process("34");
        dsp.process("*");
        
        assertEquals(asList(12, 34), dsp.dumpOperandStack());
        assertEquals(asList("+", "*"), dsp.dumpOperatorStack());
    }
    
    public void test_num_multiply_num_multiply()
    {
        dsp.process("12");
        dsp.process("*");
        dsp.process("10");
        dsp.process("*");
        
        assertEquals(asList(120), dsp.dumpOperandStack());
        assertEquals(asList("*"), dsp.dumpOperatorStack());
    }
    
    public void test_num_multiply_num_plus()
    {
        dsp.process("12");
        dsp.process("*");
        dsp.process("10");
        dsp.process("+");
        
        assertEquals(asList(120), dsp.dumpOperandStack());
        assertEquals(asList("+"), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_subtract()
    {
        dsp.process("12");
        dsp.process("+");
        dsp.process("34");
        dsp.process("-");
        
        assertEquals(asList(46), dsp.dumpOperandStack());
        assertEquals(asList("-"), dsp.dumpOperatorStack());
    }
    
    public void test_num_subtract_num_plus()
    {
        dsp.process("34");
        dsp.process("-");
        dsp.process("12");
        dsp.process("+");
        
        assertEquals(asList(22), dsp.dumpOperandStack());
        assertEquals(asList("+"), dsp.dumpOperatorStack());
    }
    
    public void test_num_subtract_num_divide()
    {
        dsp.process("34");
        dsp.process("-");
        dsp.process("12");
        dsp.process("/");
        
        assertEquals(asList(34, 12), dsp.dumpOperandStack());
        assertEquals(asList("-", "/"), dsp.dumpOperatorStack());
    }
    
    public void test_num_divide_num_plus()
    {
        dsp.process("24");
        dsp.process("/");
        dsp.process("12");
        dsp.process("+");
        
        assertEquals(asList(2), dsp.dumpOperandStack());
        assertEquals(asList("+"), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_rem()
    {
        dsp.process("12");
        dsp.process("+");
        dsp.process("34");
        dsp.process("%");
        
        assertEquals(asList(12, 34), dsp.dumpOperandStack());
        assertEquals(asList("+", "%"), dsp.dumpOperatorStack());
    }
    
    public void test_num_rem_num_plus()
    {
        dsp.process("34");
        dsp.process("%");
        dsp.process("12");
        dsp.process("+");
                
        assertEquals(asList(10), dsp.dumpOperandStack());
        assertEquals(asList("+"), dsp.dumpOperatorStack());
    }
    
    public void test_num_plus_num_multiply_num_plus()
    {
        dsp.process("12");
        dsp.process("+");
        dsp.process("10");
        dsp.process("*");
        dsp.process("34");
        dsp.process("+");
        
        assertEquals(asList(352), dsp.dumpOperandStack());
        assertEquals(asList("+"), dsp.dumpOperatorStack());
    }
    
    public void test_result_num()
    {
        dsp.process("12");
        
        assertEquals(12, dsp.result());
        assertEquals(asList(), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_result_calc_once()
    {
        dsp.process("12");
        dsp.process("+");
        dsp.process("34");
        
        assertEquals(46, dsp.result());
        assertEquals(asList(), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_result_calc_twice()
    {
        dsp.process("12");
        dsp.process("+");
        dsp.process("34");
        dsp.process("*");
        dsp.process("10");
        
        assertEquals(352, dsp.result());
        assertEquals(asList(), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
    
    public void test_num_multiply_num_pow()
    {
        dsp.process("12");
        dsp.process("*");
        dsp.process("2");
        dsp.process("**");
        
        assertEquals(asList(12, 2), dsp.dumpOperandStack());
        assertEquals(asList("*", "**"), dsp.dumpOperatorStack());
    }
    
    public void test_num_pow_num_multiply()
    {
        dsp.process("10");
        dsp.process("**");
        dsp.process("3");
        dsp.process("*");
        
        assertEquals(asList(1000), dsp.dumpOperandStack());
        assertEquals(asList("*"), dsp.dumpOperatorStack());
    }
    
    public void test_negative_num()
    {
        dsp.process("-10");
        
        assertEquals(asList(-10), dsp.dumpOperandStack());
        assertEquals(asList(), dsp.dumpOperatorStack());
    }
}
