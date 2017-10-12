package fayelab.tdd.transformer.original;

import java.util.List;

import static java.util.Arrays.asList;

public class BusinessLogicStub implements BusinessLogic
{
    @Override
    public List<String> getAllTransIds()
    {
        return asList("Upper", "Lower", "TrimPrefixSpaces");
    }

    @Override
    public String transform(String sourceStr, List<String> chainTransIds)
    {
        return "HELLO, WORLD!";
    }
}
