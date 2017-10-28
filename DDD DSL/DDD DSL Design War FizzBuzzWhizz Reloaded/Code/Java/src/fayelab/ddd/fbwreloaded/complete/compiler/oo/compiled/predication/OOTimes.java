package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

public class OOTimes implements OOPredication
{
    private int base;

    public OOTimes(int base)
    {
        this.base = base;
    }

    @Override
    public boolean predicate(int n)
    {
        return n % base == 0;
    }
}
