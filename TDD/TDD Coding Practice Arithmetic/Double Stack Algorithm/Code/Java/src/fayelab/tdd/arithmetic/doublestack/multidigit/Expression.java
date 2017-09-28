package fayelab.tdd.arithmetic.doublestack.multidigit;

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
        StringBuffer digitBuffer = new StringBuffer();
        
        while(ctx.exprNotEnd())
        {
            char c = ctx.nextChar();
            if('(' == c)
            {
                dsp.pushOperand(new Expression(ctx).eval());
            }
            else if(')' == c)
            {
                if(digitBuffer.length() != 0)
                {
                    dsp.process(digitBuffer.toString());
                } //else digitBuffer.length() == 0 when right parenthesis followed by right parenthesis
                
                return dsp.result();
            }
            else
            {
                if(isDigit(c))
                {
                    digitBuffer.append(c);
                }
                else
                {
                    if(digitBuffer.length() != 0)
                    {
                        dsp.process(digitBuffer.toString());
                        digitBuffer = new StringBuffer();
                    } //else digitBuffer.length() == 0 when right parenthesis followed by an operator
                    
                    dsp.process(String.valueOf(c));
                }
            }
        }
        
        if(digitBuffer.length() != 0)
        {
            dsp.process(digitBuffer.toString());
        } //else digitBuffer.length() == 0 when expr ends with right parenthesis
        
        return dsp.result();
    }

    private boolean isDigit(char c)
    {
        return '0' <= c && c <= '9';
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
