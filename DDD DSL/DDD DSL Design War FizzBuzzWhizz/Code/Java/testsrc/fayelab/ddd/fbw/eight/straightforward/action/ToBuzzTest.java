package fayelab.ddd.fbw.eight.straightforward.action;

import junit.framework.TestCase;

public class ToBuzzTest extends TestCase
{
    public void test_toBuzz()
    {
        Action toBuzz = new ToBuzz();
        assertEquals("Buzz", toBuzz.act(5));
    }
}
