package fayelab.ddd.fbwreloaded.oo.predication;

public class AlwaysTrue implements Predication
{
    @Override
    public boolean predicate(int n)
    {
        return true;
    }
}
