package fayelab.ddd.fbw.eight.straightforward.rule;

public class Or implements Rule
{
    private Rule rule1;
    private Rule rule2;

    public Or(Rule rule1, Rule rule2)
    {
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    @Override
    public Result apply(int n)
    {
        Result result1 = rule1.apply(n);
        if(result1.isSucceeded())
        {
            return result1;
        }
        
        return rule2.apply(n);
    }
}
