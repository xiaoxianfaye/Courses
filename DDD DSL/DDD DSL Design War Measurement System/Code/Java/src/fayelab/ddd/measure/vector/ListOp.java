package fayelab.ddd.measure.vector;

import java.util.List;

import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;

public class ListOp
{
    public static <T> List<List<T>> zip(List<T> list1, List<T> list2)
    {
        int minSize = Integer.min(list1.size(), list2.size());
        return IntStream.range(0, minSize)
                        .mapToObj(i -> asList(list1.get(i), list2.get(i)))
                        .collect(toList());
    }
    
    public static <T> List<T> reverse(List<T> list)
    {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<T> result = new ArrayList(asList(new Object[list.size()]));
        Collections.copy(result, list);
        Collections.reverse(result);
        return result;
    }
}
