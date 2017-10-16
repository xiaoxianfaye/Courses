package fayelab.ddd.fbw.eight.combination;

import junit.framework.TestCase;

import static java.util.Arrays.asList;

import java.util.List;

import static fayelab.ddd.fbw.eight.combination.ListTool.*;

public class ListToolTest extends TestCase
{
    public void test_combinate()
    {
        List<Integer> list = asList(1, 2, 3, 4);
        
        assertEquals(asList(asList(1), asList(2), asList(3), asList(4)), 
                     combinate(list, 1));
        
        assertEquals(asList(asList(1, 2), asList(1, 3), asList(1, 4), 
                            asList(2, 3), asList(2, 4),
                            asList(3, 4)), 
                     combinate(list, 2));
        
        assertEquals(asList(asList(1, 2, 3), asList(1, 2, 4), asList(1, 3, 4), 
                            asList(2, 3, 4)), 
                     combinate(list, 3));
        
        assertEquals(asList(asList(1, 2, 3, 4)), 
                     combinate(list, 4));
    }
}
