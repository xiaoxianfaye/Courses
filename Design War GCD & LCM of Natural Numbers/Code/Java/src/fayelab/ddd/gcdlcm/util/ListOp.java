package fayelab.ddd.gcdlcm.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class ListOp
{
    public static <T> Optional<List<T>> keyFind(T key, List<List<T>> pairs)
    {
         return pairs.stream().filter(pair -> key.equals(pair.get(0))).findFirst();
    }
    
    public static <T> void keyReplace(T key, List<List<T>> pairs, List<T> newPair)
    {
        keyFind(key, pairs).ifPresent(pair -> {
            pair.set(0, newPair.get(0));
            pair.set(1, newPair.get(1));
        });
    }
    
    public static <T> List<List<T>> unzip(List<List<T>> pairs)
    {
        @SuppressWarnings("serial")
        List<List<T>> result = new ArrayList<List<T>>(){
            {
                add(new ArrayList<T>());
                add(new ArrayList<T>());
            }
        };
        
        pairs.stream().collect(() -> result, 
                (acc, pair) -> {
                    if(!pair.isEmpty())
                    {
                        acc.get(0).add(pair.get(0));
                        acc.get(1).add(pair.get(1));
                    }
                }, 
                (acc1, acc2) -> {
                    acc1.get(0).addAll(acc2.get(0));
                    acc2.get(1).addAll(acc2.get(1));
                });
                
        return result;
    }
    
    public static <T> List<T> intersect(List<List<T>> lists)
    {
        return lists.stream().reduce((list1, list2) -> intersect(list1, list2)).orElse(asList());
    }
    
    public static <T> List<T> union(List<List<T>> lists)
    {
        return lists.stream().reduce((list1, list2) -> union(list1, list2)).orElse(asList());
    }
    
    public static <T> List<T> intersect(List<T> list1, List<T> list2)
    {
        Set<T> resultSet = new HashSet<>();
        
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);
        resultSet.addAll(set1);
        resultSet.retainAll(set2);
        
        return new ArrayList<>(resultSet);
    }
    
    public static <T> List<T> union(List<T> list1, List<T> list2)
    {
        Set<T> resultSet = new HashSet<>();
        
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);
        resultSet.addAll(set1);
        resultSet.addAll(set2);
        
        return new ArrayList<>(resultSet);
    }
    
    public static List<Integer> of(int... numbers)
    {
        return IntStream.of(numbers).boxed().collect(Collectors.toList());
    }
}
