package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule;

import java.util.Optional;

import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action.OOAction;
import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication.OOPredication;

public class OOAtom implements OORule
{
    private OOPredication predication;
    private OOAction action;

    public OOAtom(OOPredication predication, OOAction action)
    {
        this.predication = predication;
        this.action = action;
    }

    @Override
    public Optional<String> apply(int n)
    {
        if(predication.predicate(n))
        {
            return Optional.of(action.act(n));
        }
        
        return Optional.empty();
    }
}
