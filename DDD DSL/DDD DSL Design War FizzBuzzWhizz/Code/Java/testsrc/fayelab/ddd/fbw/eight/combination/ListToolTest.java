package fayelab.ddd.fbw.eight.combination;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

import java.util.List;

import static fayelab.ddd.fbw.eight.combination.ListTool.*;

public class ListToolTest extends TestCase
{
    public void test_combinate()
    {
        List<Integer> lst = asList(1, 2, 3, 4);
        
        assertEquals(asList(), combinate(lst, 0));
        assertEquals(asList(), combinate(lst, 5));
        
        assertEquals(asList(asList(1), asList(2), asList(3), asList(4)), 
                     combinate(lst, 1));
        
        assertEquals(asList(asList(1, 2), asList(1, 3), asList(1, 4), 
                            asList(2, 3), asList(2, 4),
                            asList(3, 4)), 
                     combinate(lst, 2));
        
        assertEquals(asList(asList(1, 2, 3), asList(1, 2, 4), asList(1, 3, 4), 
                            asList(2, 3, 4)), 
                     combinate(lst, 3));
        
        assertEquals(asList(asList(1, 2, 3, 4)), 
                     combinate(lst, 4));
    }
    
    public void test_flatten()
    {
        List<List<Integer>> lstOfLst = asList(asList(1, 2), asList(3, 4), asList(5));
        assertEquals(asList(1, 2, 3, 4, 5), flatten(lstOfLst));
        
        List<List<List<Integer>>> lstOfLst2 = asList(asList(asList(1, 2), asList(3, 4)), 
                                                     asList(asList(5), asList(6)));
        assertEquals(asList(asList(1, 2), asList(3, 4), asList(5), asList(6)), 
                     flatten(lstOfLst2));
    }
}
