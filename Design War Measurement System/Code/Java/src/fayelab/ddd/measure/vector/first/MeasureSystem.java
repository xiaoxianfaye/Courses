package fayelab.ddd.measure.vector.first;

import java.util.ArrayList;
import java.util.List;

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
        return VecOp.equal(normalize(quantityVec1), normalize(quantityVec2));
    }

    public List<Integer> normalize(List<Integer> quantityVec)
    {
        return VecOp.div(quantityVec, stepFactors);
    }

    public List<Integer> add(List<Integer> quantityVec1, List<Integer> quantityVec2)
    {
        return normalize(VecOp.add(quantityVec1, quantityVec2));
    }

    private List<Integer> makeBaseFactors(List<Integer> stepFactors)
    {
        List<Integer> result = new ArrayList<>();
        for(int i = 1; i < stepFactors.size(); i++)
        {
            List<Integer> tail = stepFactors.subList(i, stepFactors.size());
            result.add(tail.stream().reduce(1, (x, y) -> x * y));
        }
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