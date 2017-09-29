package fayelab.tdd.arithmetic.doublestack.pow;

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
        StringBuffer operatorBuffer = new StringBuffer();
        
        while(ctx.exprNotEnd())
        {
            char c = ctx.nextChar();
            if('(' == c)
            {
                //else operatorBuffer.length() == 0 when operator followed by left parenthesis
                processOperator(operatorBuffer);
                dsp.pushOperand(new Expression(ctx).eval());
            }
            else if(')' == c)
            {
                //else digitBuffer.length() == 0 when right parenthesis followed by right parenthesis
                processOperand(digitBuffer);
                return dsp.result();
            }
            else
            {
                if(isDigit(c))
                {
                    //else operatorBuffer.length() == 0 when operator followed by digit
                    processOperator(operatorBuffer);
                    digitBuffer.append(c);
                }
                else
                {
                    //else digitBuffer.length() == 0 when right parenthesis followed by an operator
                    processOperand(digitBuffer);
                    operatorBuffer.append(c);
                }
            }
        }
        
        //else digitBuffer.length() == 0 when expr ends with right parenthesis
        processOperand(digitBuffer);
        return dsp.result();
    }

    private void processOperand(StringBuffer digitBuffer)
    {
        if(digitBuffer.length() != 0)
        {
            dsp.process(digitBuffer.toString());
            digitBuffer.delete(0, digitBuffer.length());
        } 
    }

    private void processOperator(StringBuffer operatorBuffer)
    {
        if(operatorBuffer.length() != 0)
        {
            dsp.process(operatorBuffer.toString());
            operatorBuffer.delete(0, operatorBuffer.length());
        } 
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
