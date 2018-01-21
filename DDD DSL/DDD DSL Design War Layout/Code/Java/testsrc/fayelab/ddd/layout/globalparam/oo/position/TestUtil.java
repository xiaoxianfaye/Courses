package fayelab.ddd.layout.globalparam.oo.position;
import java.util.stream.IntStream;

import static junit.framework.TestCase.*;

public class TestUtil
{
    private static final int TOLERANCE = 1;
    
    static void checkRectangle(int[] expected, int[] actual)
    {
        assertEquals(expected.length, actual.length);
        
        assertTrue(IntStream.range(0, expected.length)
                            .allMatch(idx -> Math.abs(expected[idx] - actual[idx]) <= TOLERANCE));
    }
}
