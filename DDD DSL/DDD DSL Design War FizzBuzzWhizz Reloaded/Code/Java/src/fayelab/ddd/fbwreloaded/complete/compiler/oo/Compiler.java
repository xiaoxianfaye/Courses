package fayelab.ddd.fbwreloaded.complete.compiler.oo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action.*;
import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication.*;
import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule.*;

public class Compiler
{
    private static Map<Predication.Type, Function<Integer, OOPredication>> predicationTypeAndFuncMap;
    
    static
    {
        predicationTypeAndFuncMap = new HashMap<>();
        predicationTypeAndFuncMap.put(Predication.Type.TIMES, Compiler::times);
        predicationTypeAndFuncMap.put(Predication.Type.CONTAINS, Compiler::contains);
        predicationTypeAndFuncMap.put(Predication.Type.ALWAYSTRUE, any -> alwaysTrue());
    }
    
    private static Map<Action.Type, Supplier<OOAction>> actionTypeAndFuncMap;
    
    static
    {
        actionTypeAndFuncMap = new HashMap<>();
        actionTypeAndFuncMap.put(Action.Type.TOFIZZ, Compiler::toFizz);
        actionTypeAndFuncMap.put(Action.Type.TOBUZZ, Compiler::toBuzz);
        actionTypeAndFuncMap.put(Action.Type.TOWHIZZ, Compiler::toWhizz);
        actionTypeAndFuncMap.put(Action.Type.TOSTR, Compiler::toStr);
    }
    
    private static Map<Rule.Type, Function<Rule, OORule>> ruleTypeAndFuncMap;
    
    static
    {
        ruleTypeAndFuncMap = new HashMap<>();
        ruleTypeAndFuncMap.put(Rule.Type.ATOM, rule -> compileAtom((Atom)rule.getData()));
        ruleTypeAndFuncMap.put(Rule.Type.OR, rule -> compileOr((Or)rule.getData()));
        ruleTypeAndFuncMap.put(Rule.Type.AND, rule -> compileAnd((And)rule.getData()));
    }
    
    public static OORule compile(Rule rule)
    {
        return ruleTypeAndFuncMap.get(rule.getType()).apply(rule);
    }
    
    static OOPredication compilePredication(Predication predication)
    {
        return predicationTypeAndFuncMap.get(predication.getType()).apply(predication.getParam());
    }
    
    static OOAction compileAction(Action action)
    {
        return actionTypeAndFuncMap.get(action.getType()).get();
    }
    
    static OOAtom compileAtom(Atom atom)
    {
        return new OOAtom(compilePredication(atom.getPredication()), compileAction(atom.getAction()));
    }

    static OOOr compileOr(Or or)
    {
        return new OOOr(compile(or.getRule1()), compile(or.getRule2()));
    }

    static OORule compileAnd(And and)
    {
        return new OOAnd(compile(and.getRule1()), compile(and.getRule2()));
    }
    
    private static OOPredication times(int base)
    {
        return new OOTimes(base);
    }

    private static OOPredication contains(int digit)
    {
        return new OOContains(digit);
    }

    private static OOPredication alwaysTrue()
    {
        return new OOAlwaysTrue();
    }
    
    private static OOAction toFizz()
    {
        return new OOToFizz();
    }
    
    private static OOAction toBuzz()
    {
        return new OOToBuzz();
    }
    
    private static OOAction toWhizz()
    {
        return new OOToWhizz();
    }
    
    private static OOAction toStr()
    {
        return new OOToStr();
    }
}
