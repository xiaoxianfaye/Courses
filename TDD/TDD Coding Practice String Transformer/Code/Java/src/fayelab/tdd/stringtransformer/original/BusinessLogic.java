package fayelab.tdd.stringtransformer.original;

import java.util.List;

public interface BusinessLogic
{
    List<Transformer> getAllTransformers();

    String transform(String sourceStr, List<Transformer> transformers);
}