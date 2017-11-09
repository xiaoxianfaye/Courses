package fayelab.ddd.fbwreloaded.interpreter;

import java.util.Optional;

public class Interpreter
{
    public static Optional<String> interpret(Rule rule, int n)
    {
        return applyRule(rule, n);
    }

    private static Optional<String> applyRule(Rule rule, int n)
    {
        switch(rule.getType())
        {
            case ATOM:
                return applyAtom((Atom)rule.getData(), n);
            case OR:
                return applyOr((Or)rule.getData(), n);    
            case AND:
                return applyAnd((And)rule.getData(), n);
            default:
                return Optional.empty();
        }
    }

    private static Optional<String> applyAtom(Atom atom, int n)
    {
        if(applyPredication(atom.getPredication(), n))
        {
            return Optional.of(applyAction(atom.getAction(), n));
        }
        
        return Optional.empty();
    }

    private static boolean applyPredication(Predication predication, int n)
    {
        switch(predication.getType())
        {
            case TIMES:
                return times(predication.getParam(), n);
            case CONTAINS:
                return contains(predication.getParam(), n);
            case ALWAYSTRUE:
                return alwaysTrue();
            default:
                return false;
        }
    }

    private static String applyAction(Action action, int n)
    {
        switch(action.getType())
        {
            case TOFIZZ:
                return "Fizz";
            case TOBUZZ:
                return "Buzz";
            case TOWHIZZ:
                return "Whizz";
            case TOSTR:
                return String.valueOf(n);
            default:
                return "";
        }
    }
    
    private static Optional<String> applyOr(Or or, int n)
    {
        Optional<String> result1 = applyRule(or.getRule1(), n);
        if(result1.isPresent())
        {
            return result1;
        }
        
        return applyRule(or.getRule2(), n);
    }

    private static Optional<String> applyAnd(And and, int n)
    {
        Optional<String> result1 = applyRule(and.getRule1(), n);
        if(!result1.isPresent())
        {
            return Optional.empty();
        }
        
        Optional<String> result2 = applyRule(and.getRule2(), n);
        if(!result2.isPresent())
        {
            return Optional.empty();
        }
        
        return Optional.of(result1.get() + result2.get());
    }
    
    private static boolean times(int base, int n)
    {
        return n % base == 0;
    }

    private static boolean contains(int digit, int n)
    {
        int p1 = n % 10;
        int p2 = (n / 10) % 10;
        int p3 = (n / 10 / 10) % 10;
        return p1 == digit || p2 == digit || p3 == digit;
    }

    private static boolean alwaysTrue()
    {
        return true;
    }
}
