package fayelab.ddd.trianglecounter.original;

import java.util.List;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.ddd.trianglecounter.original.SetOp.*;

public class SetOpTest extends TestCase
{
    public void test_subset()
    {
        List<Integer> list1 = asList(1, 2);
        assertTrue(subset(asList(), list1));
        assertTrue(subset(asList(1), list1));
        assertTrue(subset(asList(2), list1));
        assertTrue(subset(asList(1, 2), list1));
        
        List<Character> list2 = asList('a', 'b', 'c');
        assertTrue(subset(asList(), list2));
        assertTrue(subset(asList('a'), list2));
        assertTrue(subset(asList('b'), list2));
        assertTrue(subset(asList('c'), list2));
        assertTrue(subset(asList('a', 'b'), list2));
        assertTrue(subset(asList('a', 'c'), list2));
        assertTrue(subset(asList('b', 'c'), list2));
        assertTrue(subset(asList('a', 'b', 'c'), list2));
    }
}
