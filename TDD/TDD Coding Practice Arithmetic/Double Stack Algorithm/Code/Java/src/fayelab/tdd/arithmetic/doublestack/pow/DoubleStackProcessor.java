package fayelab.tdd.arithmetic.doublestack.pow;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

public class DoubleStackProcessor
{
    private static final int OPERAND_STACK_MAX_SIZE = 3;
    private static final int OPERATOR_STACK_MAX_SIZE = 2;
    
    private static Map<Character, BiFunction<Integer, Integer, Integer>> operatorAndFuncMap2= null;
        
    static
    {
        operatorAndFuncMap2 = new HashMap<>();
        operatorAndFuncMap2.put('+', (x, y) -> x + y);
        operatorAndFuncMap2.put('-', (x, y) -> x - y);
        operatorAndFuncMap2.put('*', (x, y) -> x * y);
        operatorAndFuncMap2.put('/', (x, y) -> x / y);
        operatorAndFuncMap2.put('%', (x, y) -> x % y);
    }
    
    private static Map<String, BiFunction<Integer, Integer, Integer>> operatorAndFuncMap= null;
    
    static
    {
        operatorAndFuncMap = new HashMap<>();
        operatorAndFuncMap.put("+", (x, y) -> x + y);
        operatorAndFuncMap.put("-", (x, y) -> x - y);
        operatorAndFuncMap.put("*", (x, y) -> x * y);
        operatorAndFuncMap.put("/", (x, y) -> x / y);
        operatorAndFuncMap.put("%", (x, y) -> x % y);
    }
    
    private static Map<Character, Integer> operatorAndPriorityMap2 = null;
    
    static
    {
        operatorAndPriorityMap2 = new HashMap<>();
        operatorAndPriorityMap2.put('+', 1);
        operatorAndPriorityMap2.put('-', 1);
        operatorAndPriorityMap2.put('*', 2);
        operatorAndPriorityMap2.put('/', 2);
        operatorAndPriorityMap2.put('%', 2);
    }
    
    private static Map<String, Integer> operatorAndPriorityMap = null;
    
    static
    {
        operatorAndPriorityMap = new HashMap<>();
        operatorAndPriorityMap.put("+", 1);
        operatorAndPriorityMap.put("-", 1);
        operatorAndPriorityMap.put("*", 2);
        operatorAndPriorityMap.put("/", 2);
        operatorAndPriorityMap.put("%", 2);
    }
        
    private int[] operands = new int[OPERAND_STACK_MAX_SIZE];
    private int idxOfOperands = 0;
    private char[] operators2 = new char[OPERATOR_STACK_MAX_SIZE];
    private String[] operators = new String[OPERATOR_STACK_MAX_SIZE];
    private int idxOfOperators = 0;

    public void process(String item)
    {
        if(allDigits(item))
        {
            processOperand(item);
        }
        else
        {
            processOperator(item);
        }
    }

    public void process2(String item)
    {
        if(allDigits(item))
        {
            processOperand(item);
        }
        else
        {
            processOperator2(item);
        }
    }

    private void processOperand(String item)
    {
        pushOperand(toInt(item));
    }

    private void processOperator2(String item)
    {
        char curChar = toChar(item);
        
        while(notEmptyOperatorStack() && notPriorTo2(curChar, topOperator2()))
        {
            calcOnce2();
        }
        
        pushOperator2(curChar);
    }

    private void processOperator(String item)
    {
        while(notEmptyOperatorStack() && notPriorTo(item, topOperator()))
        {
            calcOnce();
        }
        
        pushOperator(item);
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
            calcOnce2();
        }
    }
    
    private boolean notPriorTo(String operator1, String operator2)
    {
        return priority(operator1) <= priority(operator2);
    }

    private boolean notPriorTo2(char operator1, char operator2)
    {
        return priority2(operator1) <= priority2(operator2);
    }
    
    private int priority(String operator)
    {
        return operatorAndPriorityMap.get(operator);
    }

    private int priority2(char operator)
    {
        return operatorAndPriorityMap2.get(operator);
    }
    
    private void calcOnce()
    {
        int rOperand = popOperand();
        int lOperand = popOperand();
        String operator = popOperator();
        pushOperand(apply(operator, lOperand, rOperand));
    }

    private void calcOnce2()
    {
        int rOperand = popOperand();
        int lOperand = popOperand();
        char operator = popOperator2();
        pushOperand(apply2(operator, lOperand, rOperand));
    }
    
    private int apply(String operator, int lOperand, int rOperand)
    {
        return operatorAndFuncMap.get(operator).apply(lOperand, rOperand);
    }

    private int apply2(char operator, int lOperand, int rOperand)
    {
        return operatorAndFuncMap2.get(operator).apply(lOperand, rOperand);
    }

    void pushOperand(int operand)
    {
        operands[idxOfOperands++] = operand;
    }

    private int popOperand()
    {
        return operands[--idxOfOperands];
    }
    
    private void pushOperator(String operator)
    {
        operators[idxOfOperators++] = operator;
    }

    private void pushOperator2(char c)
    {
        operators2[idxOfOperators++] = c;
    }
    
    private String popOperator()
    {
        return operators[--idxOfOperators];
    }
    
    private char popOperator2()
    {
        return operators2[--idxOfOperators];
    }
    
    private String topOperator()
    {
        return operators[idxOfOperators - 1];
    }

    private char topOperator2()
    {
        return operators2[idxOfOperators - 1];
    }
    
    private boolean allDigits(String str)
    {
        return Pattern.matches("\\d+", str);
    }
    
    private boolean notEmptyOperatorStack()
    {
        return idxOfOperators != 0;
    }

    private int toInt(String str)
    {
        return Integer.parseInt(str);
    }

    private char toChar(String oneCharStr)
    {
        return oneCharStr.charAt(0);
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
        return operators2[idx];
    }

    List<Integer> dumpOperandStack()
    {
        return IntStream.range(0, idxOfOperands).mapToObj(i -> operands[i]).collect(toList());
    }

    List<String> dumpOperatorStack()
    {
        return IntStream.range(0, idxOfOperators).mapToObj(i -> operators[i]).collect(toList());
    }
    
    List<Character> dumpOperatorStack2()
    {
        return IntStream.range(0, idxOfOperators).mapToObj(i -> operators2[i]).collect(toList());
    }
}
