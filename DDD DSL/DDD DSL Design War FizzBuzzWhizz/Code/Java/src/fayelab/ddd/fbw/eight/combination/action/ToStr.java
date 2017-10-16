package fayelab.ddd.fbw.eight.combination.action;

public class ToStr implements Action
{
    @Override
    public String act(int n)
    {
        return String.valueOf(n);
    }
}
