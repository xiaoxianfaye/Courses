package fayelab.ddd.fbw.original.action;

import junit.framework.TestCase;

public class ToFizzTest extends TestCase
{
    public void test_toFizz()
    {
        Action action = new ToFizz();
        assertEquals("Fizz", action.act(3));
    }
}