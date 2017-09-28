package fayelab.tdd.arithmetic.doublestack.multidigit;

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
}
