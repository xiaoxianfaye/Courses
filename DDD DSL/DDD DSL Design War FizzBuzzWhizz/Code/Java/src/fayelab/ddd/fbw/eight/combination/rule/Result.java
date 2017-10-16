package fayelab.ddd.fbw.eight.combination.rule;

public class Result
{
    private boolean isSucceeded;
    private String str;

    private Result(boolean isSucceeded, String str)
    {
        this.isSucceeded = isSucceeded;
        this.str = str;
    }

    public boolean isSucceeded()
    {
        return isSucceeded;
    }

    public String getStr()
    {
        return str;
    }
    
    public static Result succeededResult(String str)
    {
        return new Result(true, str);
    }

    public static Result failedResult()
    {
        return new Result(false, "");
    }
}
