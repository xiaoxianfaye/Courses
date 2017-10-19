package fayelab.ddd.layout.globalparamconf.position;

import static junit.framework.TestCase.*;

public class TestUtil
{
    private static final int TOLERANCE = 1;
    
    static void checkRect(int[] expected, int[] actual)
    {
        assertEquals(expected.length, actual.length);
        
        for(int i = 0; i < expected.length; i++)
        {
            assertTrue(Math.abs(expected[i] - actual[i]) <= TOLERANCE);
        }
    }
}
