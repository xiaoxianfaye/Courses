package fayelab.ddd.fbwreloaded.complete.compiler.functional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class RuleDescTool
{
    private static Map<Predication.Type, Function<Predication, String>> predicationTypeAndFuncMap;
    
    static
    {
        predicationTypeAndFuncMap = new HashMap<>();
        predicationTypeAndFuncMap.put(Predication.Type.TIMES, 
                                      predication -> joinPredication("times", predication.getParam()));
        predicationTypeAndFuncMap.put(Predication.Type.CONTAINS, 
                                      predication -> joinPredication("contains", predication.getParam()));
        predicationTypeAndFuncMap.put(Predication.Type.ALWAYSTRUE, 
                                      predication -> "always_true");
    }
    
    private static Map<Action.Type, Supplier<String>> actionTypeAndFuncMap;
    
    static
    {
        actionTypeAndFuncMap = new HashMap<>();
        actionTypeAndFuncMap.put(Action.Type.TOFIZZ, () -> "to_fizz");
        actionTypeAndFuncMap.put(Action.Type.TOBUZZ, () -> "to_buzz");
        actionTypeAndFuncMap.put(Action.Type.TOWHIZZ, () -> "to_whizz");
        actionTypeAndFuncMap.put(Action.Type.TOSTR, () -> "to_str");
    }
    
    private static Map<Rule.Type, Function<Rule, String>> ruleTypeAndFuncMap;
    
    static
    {
        ruleTypeAndFuncMap = new HashMap<>();
        ruleTypeAndFuncMap.put(Rule.Type.ATOM, rule -> descAtom((Atom)rule.getData()));
        ruleTypeAndFuncMap.put(Rule.Type.OR, rule -> descOr((Or)rule.getData()));
        ruleTypeAndFuncMap.put(Rule.Type.AND, rule -> descAnd((And)rule.getData()));
    }
        
    static String desc(Rule rule)
    {
        return ruleTypeAndFuncMap.get(rule.getType()).apply(rule);
    }
    
    static String descPredication(Predication predication)
    {
        return predicationTypeAndFuncMap.get(predication.getType()).apply(predication);
    }

    private static String joinPredication(String type, int param)
    {
        return String.join("_", type, String.valueOf(param));
    }
    
    static String descAction(Action action)
    {
        return actionTypeAndFuncMap.get(action.getType()).get();
    }
    
    static String descAtom(Atom atom)
    {
        return wrapWithBraces(joinRule("atom", descPredication(atom.getPredication()), descAction(atom.getAction())));
    }

    static String descOr(Or or)
    {
        return wrapWithBraces(joinRule("or", desc(or.getRule1()), desc(or.getRule2())));
    }
    
    static String descAnd(And and)
    {
        return wrapWithBraces(joinRule("and", desc(and.getRule1()), desc(and.getRule2())));
    }
    
    private static String joinRule(String...ruleElementDescs)
    {
        return String.join(", ", ruleElementDescs);
    }

    private static String wrapWithBraces(String str)
    {
        return String.format("{%s}", str);
    }
}
