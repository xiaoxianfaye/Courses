package fayelab.ddd.fbwreloaded.oo.predication;

public class Contains implements Predication
{
    private int digit;

    public Contains(int digit)
    {
        this.digit = digit;
    }

    @Override
    public boolean predicate(int n)
    {
        int p1 = n % 10;
        int p2 = (n / 10) % 10;
        int p3 = (n / 100) % 10;
        
        return p1 == digit || p2 == digit || p3 == digit;
    }
}
