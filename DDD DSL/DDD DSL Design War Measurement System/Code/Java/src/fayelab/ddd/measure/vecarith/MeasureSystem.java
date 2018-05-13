package fayelab.ddd.measure.vecarith;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MeasureSystem
{
    private List<Integer> baseFactors;

    public MeasureSystem(List<Integer> stepFactors)
    {
        this.baseFactors = toBaseFactors(stepFactors);
    }

    static List<Integer> toBaseFactors(List<Integer> stepFactors)
    {
        int size = stepFactors.size();
        return IntStream.range(0, size)
                        .mapToObj(i -> stepFactors.subList(i + 1, size)
                                                  .stream()
                                                  .reduce(1, (acc, factor) -> acc * factor))
                        .collect(Collectors.toList());
    }

    public int base(List<Integer> quantityVec)
    {
        return VecOp.dotProduct(quantityVec, baseFactors);
    }

    public boolean equal(List<Integer> quantityVec1, List<Integer> quantityVec2)
    {
        return base(quantityVec1) == base(quantityVec2);
    }

    public List<Integer> normalize(int quantityValue)
    {
        return VecOp.dotDiv(quantityValue, baseFactors);
    }

    public List<Integer> add(List<Integer> quantityVec1, List<Integer> quantityVec2)
    {
        return normalize(base(quantityVec1) + base(quantityVec2));
    }

    public List<Integer> scale(int c, List<Integer> quantityVec)
    {
        return normalize(c * base(quantityVec));
    }
}
