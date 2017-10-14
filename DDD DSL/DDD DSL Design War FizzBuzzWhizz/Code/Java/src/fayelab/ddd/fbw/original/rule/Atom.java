package fayelab.ddd.fbw.original.rule;

import fayelab.ddd.fbw.original.action.Action;
import fayelab.ddd.fbw.original.predication.Predication;

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
