package fayelab.ddd.trianglecounter.original;

import java.util.List;

public class SetOp
{
    public static <T> boolean subset(List<T> s1, List<T> s2)
    {
        return s1.stream().allMatch(element1 -> s2.contains(element1));
    }

    public static <T> boolean belong(List<T> s, List<List<T>> sos)
    {
        return sos.stream().anyMatch(elementOfSos -> subset(s, elementOfSos));
    }
}
