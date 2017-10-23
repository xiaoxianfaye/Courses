package fayelab.ddd.fbwreloaded.oo.action;

public class ToStr implements Action
{
    @Override
    public String act(int n)
    {
        return String.valueOf(n);
    }
}
