package fayelab.ddd.measure.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MeasureSystem
{
    private List<Integer> stepFactors;
    private List<Integer> baseFactors;

    public MeasureSystem(List<Integer> stepFactors)
    {
        this.stepFactors = stepFactors;
        this.baseFactors = makeBaseFactors(stepFactors);
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

    private List<Integer> makeBaseFactors(List<Integer> stepFactors)
    {
        List<Integer> result = new ArrayList<>();
        IntStream.range(1, stepFactors.size())
                 .collect(() -> result,
                          (acc, idx) -> {
                              List<Integer> tail = stepFactors.subList(idx, stepFactors.size());
                              acc.add(tail.stream().reduce(1, (x, y) -> x * y));
                          },
                          (acc1, acc2) -> {
                              acc1.addAll(acc2);
                          });
        result.add(1);
        return result;
    }
    
    List<Integer> getStepFactors()
    {
        return stepFactors;
    }

    List<Integer> getBaseFactors()
    {
        return baseFactors;
    }
}
