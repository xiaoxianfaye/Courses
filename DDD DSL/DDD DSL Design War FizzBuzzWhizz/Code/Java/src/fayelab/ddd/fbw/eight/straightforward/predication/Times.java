package fayelab.ddd.fbw.eight.straightforward.predication;

public class Times implements Predication
{
    private int base;

    public Times(int base)
    {
        this.base = base;
    }

    @Override
    public boolean predicate(int n)
    {
        return n % base == 0;
    }
}
