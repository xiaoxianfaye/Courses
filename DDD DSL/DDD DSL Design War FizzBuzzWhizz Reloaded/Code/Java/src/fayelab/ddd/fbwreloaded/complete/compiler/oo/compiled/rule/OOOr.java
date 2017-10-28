package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule;

import java.util.Optional;

public class OOOr implements OORule
{
    private OORule rule1;
    private OORule rule2;

    public OOOr(OORule rule1, OORule rule2)
    {
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    @Override
    public Optional<String> apply(int n)
    {
        Optional<String> result1 = rule1.apply(n);
        if(result1.isPresent())
        {
            return result1;
        }
        
        return rule2.apply(n);
    }
}
