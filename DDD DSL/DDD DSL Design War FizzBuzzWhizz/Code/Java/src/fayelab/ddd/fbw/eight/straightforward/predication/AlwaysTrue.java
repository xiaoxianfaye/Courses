package fayelab.ddd.fbw.eight.straightforward.predication;

public class AlwaysTrue implements Predication
{
    @Override
    public boolean predicate(int n)
    {
        return true;
    }
}
