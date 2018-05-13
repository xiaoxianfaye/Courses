package fayelab.ddd.measure.vecarith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VecOp
{
    public static boolean equal(List<Integer> vec1, List<Integer> vec2)
    {
        return vec1.equals(vec2);
    }

    public static List<Integer> add(List<Integer> vec1, List<Integer> vec2)
    {
        return IntStream.range(0, vec1.size())
                        .mapToObj(i -> vec1.get(i) + vec2.get(i))
                        .collect(Collectors.toList());
    }

    public static int dotProduct(List<Integer> rowVec, List<Integer> colVec)
    {
        return IntStream.range(0, rowVec.size())
                        .mapToObj(i -> rowVec.get(i) * colVec.get(i))
                        .reduce(0, (x, y) -> x + y);
    }

    public static List<Integer> div(List<Integer> dividendVec, List<Integer> divisorVec)
    {
        List<Integer> rDividendVec = reverse(dividendVec);
        List<Integer> rDivisorVec = reverse(divisorVec);
        int[] quotient = new int[]{0};

        List<Integer> rResultVec = IntStream.range(0, rDividendVec.size())
                                            .collect(() -> new ArrayList<>(),
                                                     (acc, i) -> {
                                                         int dividend = rDividendVec.get(i) + quotient[0];
                                                         int divisor = rDivisorVec.get(i);
                                                         quotient[0] = dividend / divisor;
                                                         acc.add(dividend % divisor);
                                                     },
                                                     (acc1, acc2) -> acc1.addAll(acc2));
        return reverse(rResultVec);
    }

    public static List<Integer> dotDiv(int baseValue, List<Integer> vec)
    {
        int[] remainder = new int[]{baseValue};

        return vec.stream()
                  .collect(() -> new ArrayList<>(),
                           (acc, c) -> {
                               int quotient = remainder[0] / c;
                               remainder[0] = remainder[0] % c;
                               acc.add(quotient);
                           },
                           (acc1, acc2) -> acc1.addAll(acc2));
    }

    public static List<Integer> scale(int c, List<Integer> vec)
    {
        return vec.stream().map(x -> c * x).collect(Collectors.toList());
    }

    private static List<Integer> reverse(List<Integer> vec)
    {
        List<Integer> rVec = new ArrayList<>(vec);
        Collections.reverse(rVec);
        return rVec;
    }
}
