package fayelab.tdd.stringtransformer.original;

import static java.util.Arrays.asList;

import java.util.List;

public class BusinessLogicImpl implements BusinessLogic
{
    @Override
    public List<Transformer> getAllTransformers()
    {
        //TODO
        return asList(Transformer.values());
    }

    @Override
    public String transform(String sourceStr, List<Transformer> transformers)
    {
        //TODO
        return "HELLO, WORLD.";
    }
}