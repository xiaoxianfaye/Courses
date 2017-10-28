package fayelab.ddd.fbwreloaded.complete.compiler.oo;

import static fayelab.ddd.fbwreloaded.complete.compiler.oo.Action.Type.*;
import static fayelab.ddd.fbwreloaded.complete.compiler.oo.Predication.Type.*;
import static fayelab.ddd.fbwreloaded.complete.compiler.oo.Rule.Type.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.Arrays.asList;

public class SpecTool
{
    private static int ANY_NUMBER = -1;
    
    public static Predication times(int base)
    {
        return new Predication(TIMES, base);
    }
    
    public static Predication contains(int digit)
    {
        return new Predication(CONTAINS, digit);
    }
    
    public static Predication alwaysTrue()
    {
        return new Predication(ALWAYSTRUE, ANY_NUMBER);
    }
    
    public static Action toFizz()
    {
        return new Action(TOFIZZ);
    }
    
    public static Action toBuzz()
    {
        return new Action(TOBUZZ);
    }
    
    public static Action toWhizz()
    {
        return new Action(TOWHIZZ);
    }
    
    public static Action toStr()
    {
        return new Action(TOSTR);
    }
    
    public static Rule atom(Predication predication, Action action)
    {
        return new Rule(ATOM, new Atom(predication, action));
    }
    
    public static Rule or(Rule rule1, Rule rule2)
    {
        return new Rule(OR, new Or(rule1, rule2));
    }
    
    public static Rule or(Rule...rules)
    {
        return or(asList(rules));
    }
    
    public static Rule or(List<Rule> rules)
    {
        return combine(rules, (rule1, rule2) -> or(rule1, rule2));
    }
    
    public static Rule and(Rule rule1, Rule rule2)
    {
        return new Rule(AND, new And(rule1, rule2));
    }
    
    public static Rule and(Rule...rules)
    {
        return and(asList(rules));
    }
    
    public static Rule and(List<Rule> rules)
    {
        return combine(rules, (rule1, rule2) -> and(rule1, rule2));
    }
    
    private static Rule combine(List<Rule> rules, BiFunction<Rule, Rule, Rule> biCombine)
    {
        if(rules.size() == 1)
        {
            return rules.get(0);
        }
        
        List<Rule> newRules = new ArrayList<>(rules);
        Rule firstRule = newRules.remove(0);
        return biCombine.apply(firstRule, combine(newRules, biCombine));
    }
    
    public static Rule spec()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());
    
        Rule r1 = or(r1_3, r1_5, r1_7);
        Rule r2 = or(and(r1_3, r1_5, r1_7),
                     and(r1_3, r1_5),
                     and(r1_3, r1_7),
                     and(r1_5, r1_7));
        Rule r3 = atom(contains(3), toFizz());
        Rule rd = atom(alwaysTrue(), toStr());
        
        return or(r3, r2, r1, rd);
    }
}
