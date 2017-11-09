package fayelab.ddd.fbwreloaded.oo.action;

import junit.framework.TestCase;

public class ToFizzTest extends TestCase
{
    public void test_toFizz()
    {
        Action toFizz = new ToFizz();
        assertEquals("Fizz", toFizz.act(3));
    }
}
