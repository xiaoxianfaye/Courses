package fayelab.ddd.poker.first.util;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Lists
{
    public static Optional<Integer> findByOccurrences(int occurrences, List<Integer> list)
    {
        return list.stream()
                   .map(ele -> asList(ele, Collections.frequency(list, ele)))
                   .filter(eleAndFreq -> eleAndFreq.get(1) == occurrences)
                   .findFirst()
                   .map(eleAndFreq -> eleAndFreq.get(0));
    }

    public static List<Integer> filterOut(int filteredOutEle, List<Integer> list)
    {
        return list.stream().filter(ele -> ele != filteredOutEle).collect(Collectors.toList());
    }

    public static List<Integer> reverse(List<Integer> list)
    {
        List<Integer> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }

    public static int compare(List<Integer> list1, List<Integer> list2)
    {
        if(list1.size() != list2.size())
        {
            throw new IllegalArgumentException("The length of list1 is not equal to the length of list2.");
        }

        Iterator<Integer> it1 = list1.iterator();
        Iterator<Integer> it2 = list2.iterator();
        while(it1.hasNext())
        {
            int ele1 = it1.next();
            int ele2 = it2.next();
            if(ele1 == ele2)
            {
                continue;
            }

            return ele1 - ele2;
        }

        return 0;
    }
}