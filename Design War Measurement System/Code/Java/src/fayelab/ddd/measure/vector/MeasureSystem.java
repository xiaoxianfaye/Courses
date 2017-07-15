package fayelab.ddd.measure.vector;

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

    public List<Integer> scale(int c, List<Integer> quantityVec)
    {
        return normalize(VecOp.scale(c, quantityVec));
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
