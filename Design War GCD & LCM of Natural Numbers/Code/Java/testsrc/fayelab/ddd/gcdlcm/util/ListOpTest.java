package fayelab.ddd.gcdlcm.util;

import junit.framework.TestCase;

import java.util.List;
import java.util.Optional;

import static fayelab.ddd.gcdlcm.util.ListOp.*;
import static java.util.Arrays.asList;

public class ListOpTest extends TestCase
{
    public void test_keyFind()
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
        
        keyReplace(3, pairs, asList(3, "cc"));
        assertEquals(expectedPairs, pairs);
    }
    
    public void test_unzip()
    {
        List<List<Object>> pairs = asList(asList(1, "a"), asList(2, "b"), asList(3, "c"));
        List<List<Object>> expected = asList(asList(1, 2, 3), asList("a", "b", "c"));
        assertEquals(expected, unzip(pairs));
        
        assertEquals(asList(asList(), asList()), unzip(asList()));
        assertEquals(asList(asList(), asList()), unzip(asList(asList(), asList(), asList())));
        
        List<List<Object>> pairs2 = asList(asList(1, "a"), asList(), asList(3, "c"));
        List<List<Object>> expected2 = asList(asList(1, 3), asList("a", "c"));
        assertEquals(expected2, unzip(pairs2));
    }
    
    public void test_intersect()
    {
        assertEquals(asList(), intersect(asList(), asList()));
        assertEquals(asList(), intersect(asList(1), asList()));
        assertEquals(asList(), intersect(asList(), asList(1)));
        assertEquals(asList(2, 3), intersect(asList(1, 2, 3, 5), asList(2, 3, 4)));
        assertEquals(asList(), intersect(asList(1, 2, 3), asList(4, 5)));
        
        assertEquals(asList(), intersect(asList()));
        assertEquals(asList(), intersect(asList(asList(1))));
        assertEquals(asList(2, 3), intersect(asList(asList(1, 2, 3, 5), asList(2, 3, 4))));
        assertEquals(asList(), intersect(asList(asList(1, 2, 3), asList(4, 5))));
        assertEquals(asList(3, 4), intersect(asList(asList(1, 2, 3, 4), asList(3, 4, 5), asList(3, 4, 5, 6))));
    }
    
    public void test_union()
    {
        assertEquals(asList(), union(asList(), asList()));
        assertEquals(asList(1), union(asList(1), asList()));
        assertEquals(asList(1), union(asList(), asList(1)));
        assertEquals(asList(1, 2, 3, 4), union(asList(1, 3), asList(2, 4)));
        assertEquals(asList(1, 2, 3, 4), union(asList(1, 2, 3), asList(3, 4)));
        assertEquals(asList(1, 2, 3, 4, 5), union(asList(1, 2, 3), asList(2, 3, 4, 5)));
        
        assertEquals(asList(), union(asList()));
        assertEquals(asList(1), union(asList(asList(1))));
        assertEquals(asList(1, 2, 3, 4), union(asList(asList(1, 3), asList(2, 4))));
        assertEquals(asList(1, 2, 3, 4), union(asList(asList(1, 2, 3), asList(3, 4))));
        assertEquals(asList(1, 2, 3, 4, 5), union(asList(asList(1, 2, 3), asList(2, 3, 4, 5))));
        assertEquals(asList(1, 2, 3, 4, 5, 6), union(asList(asList(1, 2, 3), asList(3, 4), asList(4, 5, 6))));        
    }
    
    public void test_of()
    {
        assertEquals(asList(1, 2, 3), of(1, 2, 3));
        assertEquals(asList(), of());
    }
}
