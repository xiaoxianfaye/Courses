package fayelab.tdd.arithmetic.doublestack.rem;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

public class DoubleStackProcessor
{
    private static final int OPERAND_STACK_MAX_SIZE = 3;
    private static final int OPERATOR_STACK_MAX_SIZE = 2;
    
    private static Map<Character, BiFunction<Integer, Integer, Integer>> operatorAndFuncMap= null;
    
    static
    {
        operatorAndFuncMap = new HashMap<>();
        operatorAndFuncMap.put('+', (x, y) -> x + y);
        operatorAndFuncMap.put('-', (x, y) -> x - y);
        operatorAndFuncMap.put('*', (x, y) -> x * y);
        operatorAndFuncMap.put('/', (x, y) -> x / y);
        operatorAndFuncMap.put('%', (x, y) -> x % y);
    }
    
    private static Map<Character, Integer> operatorAndPriorityMap = null;
    
    static
    {
        operatorAndPriorityMap = new HashMap<>();
        operatorAndPriorityMap.put('+', 1);
        operatorAndPriorityMap.put('-', 1);
        operatorAndPriorityMap.put('*', 2);
        operatorAndPriorityMap.put('/', 2);
        operatorAndPriorityMap.put('%', 2);
    }
        
    private int[] operands = new int[OPERAND_STACK_MAX_SIZE];
    private int idxOfOperands = 0;
    private char[] operators = new char[OPERATOR_STACK_MAX_SIZE];
    private int idxOfOperators = 0;
    
    public void process(char c)
    {
        if(isDigit(c))
        {
            processOperand(c);
        }
        else
        {
            processOperator(c);
        }
    }

    public int result()
    {
        calc();
        return popOperand();
    }

    private void calc()
    {
        while(notEmptyOperatorStack())
        {
            calcOnce();
        }
    }

    private void processOperand(char c)
    {
        pushOperand(toInt(c));
    }

    private void processOperator(char c)
    {
        while(notEmptyOperatorStack() && notPriorTo(c, topOperator()))
        {
            calcOnce();
        }
        
        pushOperator(c);
    }

    private boolean notPriorTo(char operator1, char operator2)
    {
        return priority(operator1) <= priority(operator2);
    }

    private int priority(char operator)
    {
        return operatorAndPriorityMap.get(operator);
    }

    private void calcOnce()
    {
        int rOperand = popOperand();
        int lOperand = popOperand();
        char operator = popOperator();
        pushOperand(apply(operator, lOperand, rOperand));
    }

    private int apply(char operator, int lOperand, int rOperand)
    {
        return operatorAndFuncMap.get(operator).apply(lOperand, rOperand);
    }

    void pushOperand(int operand)
    {
        operands[idxOfOperands++] = operand;
    }

    private int popOperand()
    {
        return operands[--idxOfOperands];
    }

    private void pushOperator(char c)
    {
        operators[idxOfOperators++] = c;
    }

    private char popOperator()
    {
        return operators[--idxOfOperators];
    }

    private char topOperator()
    {
        return operators[idxOfOperators - 1];
    }

    private boolean isDigit(char c)
    {
        return '0' <= c && c <= '9';
    }

    private boolean notEmptyOperatorStack()
    {
        return idxOfOperators != 0;
    }

    private int toInt(char c)
    {
        return c - '0';
    }

    int getOperandStackSize()
    {
        return idxOfOperands;
    }

    int getOperandFromStack(int idx)
    {
        return operands[idx];
    }

    int getOperatorStackSize()
    {
        return idxOfOperators;
    }

    char getOperatorFromStack(int idx)
    {
        return operators[idx];
    }

    List<Integer> dumpOperandStack()
    {
        return IntStream.range(0, idxOfOperands).mapToObj(i -> operands[i]).collect(toList());
    }

    List<Character> dumpOperatorStack()
    {
        return IntStream.range(0, idxOfOperators).mapToObj(i -> operators[i]).collect(toList());
    }
}
