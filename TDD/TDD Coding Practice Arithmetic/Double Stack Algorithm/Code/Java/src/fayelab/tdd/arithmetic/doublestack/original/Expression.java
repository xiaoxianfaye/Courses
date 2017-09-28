package fayelab.tdd.arithmetic.doublestack.original;

public class Expression
{
    private Context ctx;
    private DoubleStackProcessor dsp;

    public Expression(String expr)
    {
        this(new Context(expr, 0));
    }

    Expression(Context ctx)
    {
        this.ctx = ctx;
        this.dsp = new DoubleStackProcessor();
    }

    public int eval()
    {
        while(ctx.exprNotEnd())
        {
            char c = ctx.nextChar();
            
            if('(' == c)
            {
                dsp.pushOperand(new Expression(ctx).eval());
            }
            else if(')' == c)
            {
                return dsp.result();
            }
            else
            {
                dsp.process(c);
            }
        }
        
        return dsp.result();
    }
}

class Context
{
    private String expr;
    private int curIdx;
    
    Context(String expr, int curIdx)
    {
        this.expr = expr;
        this.curIdx = curIdx;
    }
    
    boolean exprNotEnd()
    {
        return curIdx < expr.length();
    }

    char nextChar()
    {
        return expr.charAt(curIdx++);
    }
}
