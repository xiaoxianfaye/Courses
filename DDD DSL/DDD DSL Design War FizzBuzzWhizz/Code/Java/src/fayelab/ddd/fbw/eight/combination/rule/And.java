package fayelab.ddd.fbw.eight.combination.rule;

public class And implements Rule
{
    private Rule rule1;
    private Rule rule2;

    public And(Rule rule1, Rule rule2)
    {
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    @Override
    public Result apply(int n)
    {
        Result result1 = rule1.apply(n);
        if(!result1.isSucceeded())
        {
            return Result.failedResult();
        }
        
        Result result2 = rule2.apply(n);
        if(!result2.isSucceeded())
        {
            return Result.failedResult();
        }
        
        return Result.succeededResult(result1.getStr() + result2.getStr());
    }
}
