package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

public class OOToStr implements OOAction
{
    @Override
    public String act(int n)
    {
        return String.valueOf(n);
    }
}
