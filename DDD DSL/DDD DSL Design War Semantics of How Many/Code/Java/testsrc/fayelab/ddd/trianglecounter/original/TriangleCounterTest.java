package fayelab.ddd.trianglecounter.original;

import java.util.List;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.ddd.trianglecounter.original.TriangleCounter.*;

public class TriangleCounterTest extends TestCase
{
    public void test_connected()
    {
        List<List<Character>> lines = asList(asList('a', 'c', 'b'), asList('c', 'd'));
        assertTrue(connected('a', 'c', lines));
        assertTrue(connected('c', 'b', lines));
        assertTrue(connected('c', 'd', lines));
        assertFalse(connected('a', 'd', lines));
        assertFalse(connected('b', 'd', lines));
    }
    
    public void test_onALine()
    {
        List<List<Character>> lines = asList(asList('a', 'c', 'b'), asList('c', 'd'));
        assertTrue(onALine('a', 'c', 'b', lines));
        assertFalse(onALine('a', 'c', 'd', lines));
        assertFalse(onALine('b', 'c', 'd', lines));
    }
    
    public void test_triangle()
    {
        List<List<Character>> lines = asList(asList('a', 'c', 'b'), 
                                             asList('c', 'd'), 
                                             asList('a', 'd'), 
                                             asList('b', 'd'));
        
        assertTrue(triangle('a', 'c', 'd', lines));
        assertTrue(triangle('b', 'c', 'd', lines));
        assertTrue(triangle('a', 'b', 'd', lines));
        assertFalse(triangle('a', 'c', 'b', lines));
    }
    
    public void test_count()
    {
        assertEquals(3, count(asList('a', 'b', 'c', 'd'), 
                              asList(asList('a', 'c', 'b'), 
                                     asList('a', 'd'), asList('c', 'd'), asList('b', 'd'))));
    }
    
    public void test_parsePoints()
    {
        assertEquals(asList('a', 'b', 'c', 'd'), parsePoints("abcd"));
    }
    
    public void test_parseLines()
    {
        assertEquals(asList(asList('a', 'c', 'b'), asList('a', 'd'), asList('c', 'd'), asList('b', 'd')), 
                     parseLines("acb", "ad", "cd", "bd"));
    }
    
    public void test_count_ui()
    {
        assertEquals(24, count("abcdefghijk", "abc", "adef", "aghi", "ajk", "bdgj", "cehj", "cfik"));
    }
}
