package fayelab.ddd.gcdlcm.interpreter;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

import java.util.List;

import static fayelab.ddd.gcdlcm.interpreter.ListOp.*;

import java.util.Optional; 

public class ListOpTest extends TestCase
{
    public void test_keyfind()
    {
        List<List<Object>> pairs = asList(asList(1, "a"), asList(2, "b"));
        assertEquals(Optional.of(asList(1, "a")), keyFind(1, pairs));
        assertEquals(Optional.empty(), keyFind(3, pairs));
    }
    
    public void test_keyReplace()
    {
        List<List<Object>> pairs = asList(asList(1, "a"), asList(2, "b"));
        List<List<Object>> expectedPairs = asList(asList(1, "a"), asList(2, "bb"));
        keyReplace(2, pairs, asList(2, "bb"));
        assertEquals(expectedPairs, pairs);
    }
    
    public void test_unzip()
    {
        List<List<Object>> pairs = asList(asList(1, "a"), asList(2, "b"), asList(3, "c"));
        List<List<Object>> expected = asList(asList(1, 2, 3), asList("a", "b", "c"));
        assertEquals(expected, unzip(pairs));
        
        assertEquals(asList(asList(), asList()), unzip(asList()));
    }
}
