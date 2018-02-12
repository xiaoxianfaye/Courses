package fayelab.tdd.stringtransformer.original;

import java.util.List;

import fayelab.tdd.stringtransformer.original.BusinessLogic;
import fayelab.tdd.stringtransformer.original.Transformer;

import static java.util.Arrays.asList;

public class BusinessLogicStub implements BusinessLogic
{
    @Override
    public List<Transformer> getAllTransformers()
    {
        return asList(Transformer.values());
    }

    @Override
    public String transform(String sourceStr, List<Transformer> transformers)
    {
        return "HELLO, WORLD.";
    }
}