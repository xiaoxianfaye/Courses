package fayelab.tdd.arithmetic.doublestack;

import junit.framework.TestCase;

public class ExpressionTest extends TestCase
{
    public void test_num()
    {
        Expression expr = new Expression("3");
        assertEquals(3, expr.eval());
    }
    
    public void test_expr_has_one_operator()
    {
        Expression expr = new Expression("1+2");
        assertEquals(3, expr.eval());
    }
    
    public void test_expr_has_multiple_operators()
    {
        Expression expr = new Expression("1+2*3");
        assertEquals(7, expr.eval());
    }
    
    public void test_expr_context()
    {
        Expression expr = new Expression(new Context("3", 0));
        assertEquals(3, expr.eval());
    }
    
    public void test_expr_has_parentheses()
    {
        Expression expr = new Expression("2*(1+2)");
        assertEquals(6, expr.eval());
    }
    
    public void test_expr_final()
    {
        Expression expr = new Expression("((1+2)+1)*2");
        assertEquals(8, expr.eval());
        
        expr = new Expression("(1+(1+2))*2");
        assertEquals(8, expr.eval());
                
        expr = new Expression("(2-(1-2))*3+(2-(2+1))*3");
        assertEquals(6, expr.eval());
    }
}
