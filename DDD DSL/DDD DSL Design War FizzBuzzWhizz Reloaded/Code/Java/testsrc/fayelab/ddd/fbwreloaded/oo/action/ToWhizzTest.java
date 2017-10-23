package fayelab.ddd.fbwreloaded.oo.action;

import junit.framework.TestCase;

public class ToWhizzTest extends TestCase
{
    public void test_toWhizz()
    {
        Action action = new ToWhizz();
        assertEquals("Whizz", action.act(7));
    }
}
