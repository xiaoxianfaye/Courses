package fayelab.ddd.fbwreloaded.complete.compiler.functional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Compiler
{
    private static Map<Predication.Type, Function<Integer, Predicate<Integer>>> predicationTypeAndFuncMap;
    
    static
    {
        predicationTypeAndFuncMap = new HashMap<>();
        predicationTypeAndFuncMap.put(Predication.Type.TIMES, Compiler::times);
        predicationTypeAndFuncMap.put(Predication.Type.CONTAINS, Compiler::contains);
        predicationTypeAndFuncMap.put(Predication.Type.ALWAYSTRUE, any -> alwaysTrue());
    }
    
    private static Map<Action.Type, Supplier<Function<Integer, String>>> actionTypeAndFuncMap;
    
    static
    {
        actionTypeAndFuncMap = new HashMap<>();
        actionTypeAndFuncMap.put(Action.Type.TOFIZZ, Compiler::toFizz);
        actionTypeAndFuncMap.put(Action.Type.TOBUZZ, Compiler::toBuzz);
        actionTypeAndFuncMap.put(Action.Type.TOWHIZZ, Compiler::toWhizz);
        actionTypeAndFuncMap.put(Action.Type.TOSTR, Compiler::toStr);
    }
    
    private static Map<Rule.Type, Function<Rule, Function<Integer, Optional<String>>>> ruleTypeAndFuncMap;
    
    static
    {
        ruleTypeAndFuncMap = new HashMap<>();
        ruleTypeAndFuncMap.put(Rule.Type.ATOM, rule -> compileAtom((Atom)rule.getData()));
        ruleTypeAndFuncMap.put(Rule.Type.OR, rule -> compileOr((Or)rule.getData()));
        ruleTypeAndFuncMap.put(Rule.Type.AND, rule -> compileAnd((And)rule.getData()));
    }
    
    public static Function<Integer, Optional<String>> compile(Rule rule)
    {
        return ruleTypeAndFuncMap.get(rule.getType()).apply(rule);
    }
    
    static Predicate<Integer> compilePredication(Predication predication)
    {
        return predicationTypeAndFuncMap.get(predication.getType()).apply(predication.getParam());
    }
    
    static Function<Integer, String> compileAction(Action action)
    {
        return actionTypeAndFuncMap.get(action.getType()).get();
    }
    
    static Function<Integer, Optional<String>> compileAtom(Atom atom)
    {
        return n -> {
            Predicate<Integer> predication = compilePredication(atom.getPredication());
            Function<Integer, String> action = compileAction(atom.getAction());
            if(predication.test(n))
            {
                return Optional.of(action.apply(n));
            }
            
            return Optional.empty();
        };
    }

    static Function<Integer, Optional<String>> compileOr(Or or)
    {
        return n -> {
            Optional<String> result1 = compile(or.getRule1()).apply(n);
            if(result1.isPresent())
            {
                return result1;
            }
            
            return compile(or.getRule2()).apply(n);
        };
    }

    static Function<Integer, Optional<String>> compileAnd(And and)
    {
        return n -> {
            Optional<String> result1 = compile(and.getRule1()).apply(n);
            if(!result1.isPresent())
            {
                return Optional.empty();
            }
            
            Optional<String> result2 = compile(and.getRule2()).apply(n);
            if(!result2.isPresent())
            {
                return Optional.empty();
            }
            
            return Optional.of(result1.get() + result2.get());
        };
    }
    
    private static Predicate<Integer> times(int base)
    {
        return n -> n % base == 0;
    }

    private static Predicate<Integer> contains(int digit)
    {
        return n -> {
            int p1 = n % 10;
            int p2 = (n / 10) % 10;
            int p3 = (n / 10 / 10) % 10;
            return p1 == digit || p2 == digit || p3 == digit;
        };
    }

    private static Predicate<Integer> alwaysTrue()
    {
        return n -> true;
    }
    
    private static Function<Integer, String> toFizz()
    {
        return n -> "Fizz";
    }
    
    private static Function<Integer, String> toBuzz()
    {
        return n -> "Buzz";
    }
    
    private static Function<Integer, String> toWhizz()
    {
        return n -> "Whizz";
    }
    
    private static Function<Integer, String> toStr()
    {
        return n -> String.valueOf(n);
    }
}
