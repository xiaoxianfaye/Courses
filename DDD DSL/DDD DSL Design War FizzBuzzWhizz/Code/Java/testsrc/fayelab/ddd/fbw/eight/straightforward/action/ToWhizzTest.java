package fayelab.ddd.fbw.eight.straightforward.action;

import junit.framework.TestCase;

public class ToWhizzTest extends TestCase
{
    public void test_toWhizz()
    {
        Action toWhizz = new ToWhizz();
        assertEquals("Whizz", toWhizz.act(7));
    }
}
