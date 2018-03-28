package fayelab.tdd.arithmetic.doublestack.parser;

import junit.framework.TestCase;

public class ExpressionTest extends TestCase
{    
    public void test_multidigit_operand()
    {
        Expression expr = new Expression("12");
        assertEquals(12, expr.eval());
    }

    public void test_multichar_operator()
    {
        Expression expr = new Expression("10**2");
        assertEquals(100, expr.eval());
    }

    public void test_negative_operand()
    {
        Expression expr = new Expression("-12");
        assertEquals(-12, expr.eval());
    }

    public void test_parenthesis_without_negative()
    {
        Expression expr = new Expression("10*(48-(12+34))");
        assertEquals(20, expr.eval());
    }

    public void test_parenthesis_with_negative()
    {
        Expression expr = new Expression("((12+(-11))*2-11)**2");
        assertEquals(81, expr.eval());
    }
}
