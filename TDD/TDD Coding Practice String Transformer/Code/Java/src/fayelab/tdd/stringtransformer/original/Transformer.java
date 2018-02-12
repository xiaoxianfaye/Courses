package fayelab.tdd.stringtransformer.original;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Transformer
{
    UPPER("Upper"), LOWER("Lower"), TRIM_PREFIX_SPACES("TrimPrefixSpaces");
    
    private String name;
    
    private Transformer(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    
    public static List<String> getNames(List<Transformer> transformers)
    {
        return transformers.stream().map(trans -> trans.getName()).collect(Collectors.toList());
    }
    
    public static Transformer getTransformer(String name)
    {
        return Stream.of(values())
                     .filter(trans -> name.equals(trans.getName()))
                     .findFirst()
                     .orElse(null);
    }
}