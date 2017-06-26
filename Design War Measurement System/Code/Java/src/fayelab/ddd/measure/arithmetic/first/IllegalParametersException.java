package fayelab.ddd.measure.arithmetic.first;

public class IllegalParametersException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public IllegalParametersException()
    {
        super();
    }

    public IllegalParametersException(String msg)
    {
        super(msg);
    }

    public IllegalParametersException(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }

    public IllegalParametersException(Throwable throwable)
    {
        super(throwable);
    }
}
