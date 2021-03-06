package fayelab.ddd.fbw.eight.combination;

import java.util.List;
import java.util.function.BinaryOperator;

import fayelab.ddd.fbw.eight.combination.action.Action;
import fayelab.ddd.fbw.eight.combination.action.ToBuzz;
import fayelab.ddd.fbw.eight.combination.action.ToFizz;
import fayelab.ddd.fbw.eight.combination.action.ToHazz;
import fayelab.ddd.fbw.eight.combination.action.ToStr;
import fayelab.ddd.fbw.eight.combination.action.ToWhizz;
import fayelab.ddd.fbw.eight.combination.predication.AlwaysTrue;
import fayelab.ddd.fbw.eight.combination.predication.Contains;
import fayelab.ddd.fbw.eight.combination.predication.Predication;
import fayelab.ddd.fbw.eight.combination.predication.Times;
import fayelab.ddd.fbw.eight.combination.rule.And;
import fayelab.ddd.fbw.eight.combination.rule.Atom;
import fayelab.ddd.fbw.eight.combination.rule.Or;
import fayelab.ddd.fbw.eight.combination.rule.Rule;

import static fayelab.ddd.fbw.eight.combination.ListTool.combinate;
import static fayelab.ddd.fbw.eight.combination.ListTool.flatten;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class SpecTool
{
    public static Predication times(int n)
    {
        return new Times(n);
    }

    public static Predication contains(int digit)
    {
        return new Contains(digit);
    }

    public static Predication alwaysTrue()
    {
        return new AlwaysTrue();
    }
    
    public static Action toFizz()
    {
        return new ToFizz();
    }

    public static Action toBuzz()
    {
        return new ToBuzz();
    }
    
    public static Action toWhizz()
    {
        return new ToWhizz();
    }

    public static Action toStr()
    {
        return new ToStr();
    }
    
    public static Action toHazz()
    {
        return new ToHazz();
    }
    
    public static Rule atom(Predication predication, Action action)
    {
        return new Atom(predication, action);
    }
    
    public static Rule or(Rule rule1, Rule rule2)
    {
        return new Or(rule1, rule2);
    }
    
    public static Rule or(Rule...rules)
    {
        return or(asList(rules));
    }
    
    public static Rule or(List<Rule> rules)
    {
        return combine(rules, Or::new);
    }

    public static Rule and(Rule rule1, Rule rule2)
    {
        return new And(rule1, rule2);
    }
    
    public static Rule and(Rule...rules)
    {
        return and(asList(rules));
    }
    
    public static Rule and(List<Rule> rules)
    {
        return combine(rules, And::new);
    }
    
    private static Rule combine(List<Rule> rules, BinaryOperator<Rule> biCombine)
    {
        return rules.stream().reduce(biCombine).get();
    }

    public static Rule spec()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());
        Rule r1_8 = atom(times(8), toHazz());
        
        List<Rule> atomRules = asList(r1_3, r1_5, r1_7, r1_8);
    
        Rule r1 = or(atomRules);
        
        List<List<Rule>> lstOfRules = flatten(asList(combinate(atomRules, 4),
                                                     combinate(atomRules, 3),
                                                     combinate(atomRules, 2)));
        Rule r2 = or(lstOfRules.stream().map(rules -> and(rules)).collect(toList()));

        Rule r3 = atom(contains(3), toFizz());
        Rule rd = atom(alwaysTrue(), toStr());
        
        return or(r3, r2, r1, rd);
    }
}
