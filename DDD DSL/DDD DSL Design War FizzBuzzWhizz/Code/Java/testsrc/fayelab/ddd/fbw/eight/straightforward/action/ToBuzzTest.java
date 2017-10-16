package fayelab.ddd.fbw.eight.straightforward.action;

import junit.framework.TestCase;

public class ToBuzzTest extends TestCase
{
    public void test_toBuzz()
    {
        Action action = new ToBuzz();
        assertEquals("Buzz", action.act(5));
    }
}
