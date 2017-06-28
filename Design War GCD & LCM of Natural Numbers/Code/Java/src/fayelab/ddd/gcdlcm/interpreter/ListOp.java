package fayelab.ddd.gcdlcm.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    acc.get(0).add(pair.get(0));
                    acc.get(1).add(pair.get(1));
                }, 
                (acc, pair) -> {
                    acc.get(0).addAll(pair.get(0));
                    acc.get(1).addAll(pair.get(1));
                });
        
        return result;
    }
}
