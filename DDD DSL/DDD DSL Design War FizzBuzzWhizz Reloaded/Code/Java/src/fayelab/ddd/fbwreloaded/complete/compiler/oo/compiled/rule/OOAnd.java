package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule;

import java.util.Optional;

public class OOAnd implements OORule
{
    private OORule rule1;
    private OORule rule2;

    public OOAnd(OORule rule1, OORule rule2)
    {
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    @Override
    public Optional<String> apply(int n)
    {
        Optional<String> result1 = rule1.apply(n);
        if(!result1.isPresent())
        {
            return Optional.empty();
        }
        
        Optional<String> result2 = rule2.apply(n);
        if(!result2.isPresent())
        {
            return Optional.empty();
        }
        
        return Optional.of(result1.get() + result2.get());
    }
}
