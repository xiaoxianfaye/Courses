package fayelab.tdd.arithmetic.doublestack.parser;

import java.util.function.Predicate;

public class Expression
{
    private Context ctx;
    private DoubleStackProcessor dsp;
    private Parser parser;

    public Expression(String expr)
    {
        this(new Context(expr, 0));
    }

    Expression(Context ctx)
    {
        this.ctx = ctx;
        this.dsp = new DoubleStackProcessor();
        this.parser = new Parser(ctx);
    }

    public int eval()
    {
        while(parser.exprNotEnd())
        {
            String item = parser.nextItem();

            if("(".equals(item))
            {
                dsp.pushOperand(new Expression(ctx).eval());
            }
            else if(")".equals(item))
            {
                return dsp.result();
            }
            else
            {
                dsp.process(item);
            }
        }

        return dsp.result();
    }
}

class Parser
{
    private Context ctx;

    Parser(Context ctx)
    {
        this.ctx = ctx;
    }

    boolean exprNotEnd()
    {
        return ctx.curIdx < ctx.expr.length();
    }

    String nextItem()
    {
        char c = nextChar();
        return c + subsequence(c);
    }

    private char nextChar()
    {
        char c = ctx.expr.charAt(ctx.curIdx);
        ctx.curIdx += 1;
        return c;
    }

    private String subsequence(char c)
    {
        if(isDigit(c) || isNegative(c))
        {
            return nextOperand();
        }

        if(isOpChar(c))
        {
            return nextOperator();
        }

        return "";
    }

    private String nextOperand()
    {
        return nextItem(this::isDigit);
    }

    private String nextOperator()
    {
        return nextItem(this::isOpChar);
    }

    private String nextItem(Predicate<Character> pred)
    {
        String result = "";
        while(pred.test(peekChar()))
        {
            result += nextChar();
        }
        return result;
    }

    private char peekChar()
    {
        return exprNotEnd() ? ctx.expr.charAt(ctx.curIdx) : Character.MIN_VALUE;
    }

    private char prevChar()
    {
        return ctx.expr.charAt(ctx.curIdx - 2);
    }

    private boolean isDigit(char c)
    {
        return '0' <= c && c <= '9';
    }

    private boolean isOpChar(char c)
    {
        return "+-*/%".indexOf(c) > -1;
    }

    private boolean isNegative(char c)
    {
        return c == '-' && (ctx.curIdx == 1 || prevChar() == '(');
    }
}

class Context
{
    String expr;
    int curIdx;

    Context(String expr, int curIdx)
    {
        this.expr = expr;
        this.curIdx = curIdx;
    }
}
