package fayelab.ddd.poker.tie.util;

import static java.util.Arrays.asList;
import static fayelab.ddd.poker.tie.util.Lists.*;

import java.util.Optional;

import junit.framework.TestCase;

public class ListsTest extends TestCase
{
    public void test_findByOccurrences()
    {
        assertEquals(Optional.of(4), findByOccurrences(4, asList(4, 4, 4, 4, 5)));
        assertEquals(Optional.of(4), findByOccurrences(3, asList(3, 4, 5, 4, 4)));
        assertEquals(Optional.of(4), findByOccurrences(2, asList(2, 4, 5, 3, 4)));
        assertEquals(Optional.of(4), findByOccurrences(2, asList(4, 4, 2, 5, 5)));
        assertEquals(Optional.of(1), findByOccurrences(1, asList(1, 2, 3, 4, 5)));
        assertEquals(Optional.empty(), findByOccurrences(6, asList(5, 4, 3, 2, 1)));
    }

    public void test_filterOut()
    {
        assertEquals(asList(), filterOut(2, asList()));
        assertEquals(asList(3, 5), filterOut(4, asList(3, 4, 5, 4, 4)));
        assertEquals(asList(3, 4, 5, 4, 4), filterOut(2, asList(3, 4, 5, 4, 4)));
        assertEquals(asList(3, 4, 4, 4), filterOut(5, asList(3, 4, 5, 4, 4)));
    }

    public void test_reverse()
    {
        assertEquals(asList(), reverse(asList()));
        assertEquals(asList(1, 2, 3, 4, 5), reverse(asList(5, 4, 3, 2, 1)));
        assertEquals(asList(2, 2, 3, 5, 5), reverse(asList(5, 5, 3, 2, 2)));
    }

    public void test_compare()
    {
        assertTrue(compare(asList(), asList()) == 0);
        assertTrue(compare(asList(6, 5, 4, 3, 2), asList(7, 6, 5, 4, 3)) < 0);
        assertTrue(compare(asList(6, 5, 4, 3, 2), asList(6, 5, 4, 3, 2)) == 0);
        assertTrue(compare(asList(7, 5, 4, 3, 2), asList(6, 6, 4, 3, 2)) > 0);
    }

    public void test_calcDistribution()
    {
        assertEquals(asList(asList(1, 1, 1, 1, 1), asList(6, 5, 4, 3, 2)), calcDistribution(asList(2, 3, 4, 5, 6)));
        assertEquals(asList(asList(4, 1), asList(2, 9)), calcDistribution(asList(2, 2, 9, 2, 2)));
        assertEquals(asList(asList(3, 2), asList(10, 3)), calcDistribution(asList(10, 3, 10, 3, 10)));
        assertEquals(asList(asList(1, 1, 1, 1, 1), asList(12, 11, 6, 5, 3)), calcDistribution(asList(6, 3, 5, 11, 12)));
        assertEquals(asList(asList(1, 1, 1, 1, 1), asList(6, 5, 4, 3, 2)), calcDistribution(asList(2, 3, 4, 5, 6)));
        assertEquals(asList(asList(3, 1, 1), asList(2, 12, 10)), calcDistribution(asList(2, 2, 2, 10, 12)));
        assertEquals(asList(asList(2, 2, 1), asList(4, 3, 6)), calcDistribution(asList(3, 3, 4, 4, 6)));
        assertEquals(asList(asList(2, 1, 1, 1), asList(3, 11, 9, 2)), calcDistribution(asList(3, 3, 2, 9, 11)));
        assertEquals(asList(asList(1, 1, 1, 1, 1), asList(9, 7, 4, 3, 2)), calcDistribution(asList(7, 2, 3, 4, 9)));
    }
}