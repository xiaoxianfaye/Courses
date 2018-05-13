package fayelab.ddd.measure.vector.original;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MeasureSystem
{
    private List<Integer> stepFactors;
    private List<Integer> baseFactors;

    public MeasureSystem(List<Integer> stepFactors)
    {
        this.stepFactors = stepFactors;
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

    public List<Integer> normalize(List<Integer> quantityVec)
    {
        return VecOp.div(quantityVec, stepFactors);
    }

    public boolean equal(List<Integer> quantityVec1, List<Integer> quantityVec2)
    {
        return VecOp.equal(normalize(quantityVec1), normalize(quantityVec2));
    }

    public List<Integer> add(List<Integer> quantityVec1, List<Integer> quantityVec2)
    {
        return normalize(VecOp.add(quantityVec1, quantityVec2));
    }
}
