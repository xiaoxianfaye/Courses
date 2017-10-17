package fayelab.ddd.fbw.eight.combination;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ListTool
{
    public static <T> List<List<T>> combinate(List<T> lst, int n)
    {
        if(n < 1 || n > lst.size())
        {
            return asList();
        }
        
        if(n == 1)
        {
            return lst.stream().map(ele -> asList(ele)).collect(toList());
        }
        
        if(n == lst.size())
        {
            return asList(lst);
        }
        
        T head = lst.get(0);
        List<T> rest = lst.subList(1, lst.size());
        List<List<T>> comb1 = combinate(rest, n - 1).stream()
                                                    .map(ele -> addHead(head, ele))
                                                    .collect(toList());
        List<List<T>> comb2 = combinate(rest, n);
        return concat(comb1, comb2);
    }
    
    public static <T> List<T> flatten(List<List<T>> lstOfLst)
    {
        return lstOfLst.stream().collect(ArrayList::new, 
                                         (result, lst) -> result.addAll(lst), 
                                         ArrayList::addAll);
    }
    
    private static <T> List<T> addHead(T head, List<T> lst)
    {
        List<T> result = new ArrayList<>();
        result.add(head);
        result.addAll(lst);
        return result;
    }

    private static <T> List<List<T>> concat(List<List<T>> lstOfLst1, List<List<T>> lstOfLst2)
    {
        return Stream.concat(lstOfLst1.stream(), lstOfLst2.stream()).collect(toList());
    }
}
