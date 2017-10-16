package fayelab.ddd.fbw.original.predication;

public class AlwaysTrue implements Predication
{
    @Override
    public boolean predicate(int n)
    {
        return true;
    }
}
