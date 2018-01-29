package fayelab.ddd.trianglecounter.original;

import java.util.List;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.ddd.trianglecounter.original.SetOp.*;

public class SetOpTest extends TestCase
{
    public void test_subset()
    {
        List<Integer> s1 = asList(1, 2);
        assertTrue(subset(asList(), s1));
        assertTrue(subset(asList(1), s1));
        assertTrue(subset(asList(2), s1));
        assertTrue(subset(asList(1, 2), s1));
        
        List<Character> s2 = asList('a', 'b', 'c');
        assertTrue(subset(asList(), s2));
        assertTrue(subset(asList('a'), s2));
        assertTrue(subset(asList('b'), s2));
        assertTrue(subset(asList('c'), s2));
        assertTrue(subset(asList('a', 'b'), s2));
        assertTrue(subset(asList('a', 'c'), s2));
        assertTrue(subset(asList('b', 'c'), s2));
        assertTrue(subset(asList('a', 'b', 'c'), s2));
    }
    
    public void test_belong()
    {
        List<List<Integer>> sos1 = asList(asList(1, 2), asList(2, 3), asList(3, 4));
        assertTrue(belong(asList(2, 3), sos1));
        assertFalse(belong(asList(1, 3), sos1));
        
        List<List<Character>> sos2 = asList(asList('a', 'c', 'b'), asList('c', 'd'));
        assertTrue(belong(asList('a', 'c', 'b'), sos2));
        assertTrue(belong(asList('a', 'c'), sos2));
        assertTrue(belong(asList('c', 'b'), sos2));
        assertFalse(belong(asList('a', 'd'), sos2));
        assertFalse(belong(asList('b', 'd'), sos2));
    }
}
