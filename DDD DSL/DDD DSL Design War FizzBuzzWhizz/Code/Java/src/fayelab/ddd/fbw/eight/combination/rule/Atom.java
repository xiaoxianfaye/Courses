package fayelab.ddd.fbw.eight.combination.rule;

import fayelab.ddd.fbw.eight.combination.action.Action;
import fayelab.ddd.fbw.eight.combination.predication.Predication;

public class Atom implements Rule
{
    private Predication predication;
    private Action action;

    public Atom(Predication predication, Action action)
    {
        this.predication = predication;
        this.action = action;
    }

    @Override
    public Result apply(int n)
    {
        if(predication.predicate(n))
        {
            return Result.succeededResult(action.act(n));
        }
        
        return Result.failedResult();
    }
}
