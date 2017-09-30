package fayelab.tdd.arithmetic.doublestack.negint;

import junit.framework.TestCase;

public class ExpressionTest extends TestCase
{    
    public void test_num()
    {
        Expression expr = new Expression("12");
        assertEquals(12, expr.eval());
    }
    
    public void test_expr_has_one_operator()
    {
        Expression expr = new Expression("12+34");
        assertEquals(46, expr.eval());
    }
    
    public void test_expr_has_multiple_operators()
    {
        Expression expr = new Expression("12+34*10");
        assertEquals(352, expr.eval());
    }
    
    public void test_expr_with_right_parenthesis_followed_by_an_operator()
    {
        Expression expr = new Expression("(12+34)*10");
        assertEquals(460, expr.eval());
    }
    
    public void test_expr_ends_with_right_parenthesis()
    {
        Expression expr = new Expression("10*(12+34)");
        assertEquals(460, expr.eval());
    }
    
    public void test_expr_with_right_parenthesis_followed_by_right_parenthesis()
    {
        Expression expr = new Expression("(48-(12+34))*10");
        assertEquals(20, expr.eval());
        
        Expression expr2 = new Expression("10*(48-(12+34))");
        assertEquals(20, expr2.eval());
    }
    
    public void test_expr_with_nested_parentheses()
    {
        Expression expr = new Expression("((10+21)+34)*20");
        assertEquals(1300, expr.eval());
        
        Expression expr2 = new Expression("(50-(12+34))*20");
        assertEquals(80, expr2.eval());
        
        Expression expr3 = new Expression("((11+23)%11)*20");
        assertEquals(20, expr3.eval());
                
        Expression expr4 = new Expression("(12-(15-12))*10/(24-(12+10))/3");
        assertEquals(15, expr4.eval());
        
        Expression expr5 = new Expression("(100-(1920-1900))*2/(24-(12+10))/2");
        assertEquals(40, expr5.eval());
    }
    
    public void test_expr_with_pow()
    {
        Expression expr = new Expression("2**3");
        assertEquals(8, expr.eval());
    }
    
    public void test_expr_with_operator_followed_by_left_parenthesis()
    {
        Expression expr = new Expression("2**(1+2)");
        assertEquals(8, expr.eval());
    }
    
    public void test_expr_with_minus_when_expr_starts_with_minus()
    {
        Expression expr = new Expression("-12");
        assertEquals(-12, expr.eval());
    }
    
    public void test_expr_with_minus_when_negative_num_wrapped_with_parentheses()
    {
        Expression expr = new Expression("(-12)");
        assertEquals(-12, expr.eval());
    }
    
    public void test_expr_with_minus()
    {
        Expression expr = new Expression("((12))");
        assertEquals(12, expr.eval());
        
        Expression expr2 = new Expression("-10+20");
        assertEquals(10, expr2.eval());
        
        Expression expr3 = new Expression("(-10)+20");
        assertEquals(10, expr3.eval());
        
        Expression expr4 = new Expression("20+(-10)");
        assertEquals(10, expr4.eval());
        
        Expression expr5 = new Expression("(-11+12)*1-15+12/2");
        assertEquals(-8, expr5.eval());
        
        Expression expr6 = new Expression("(11+12)*(-1)-(15+12/2)");
        assertEquals(-44, expr6.eval());
        
        Expression expr7 = new Expression("((12+(-11))*2-11)*3");
        assertEquals(-27, expr7.eval());
    }
}
