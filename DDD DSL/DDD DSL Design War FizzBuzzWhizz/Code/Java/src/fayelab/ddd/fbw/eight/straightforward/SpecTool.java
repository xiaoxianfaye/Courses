package fayelab.ddd.fbw.eight.straightforward;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fayelab.ddd.fbw.eight.straightforward.action.Action;
import fayelab.ddd.fbw.eight.straightforward.action.ToBuzz;
import fayelab.ddd.fbw.eight.straightforward.action.ToFizz;
import fayelab.ddd.fbw.eight.straightforward.action.ToHazz;
import fayelab.ddd.fbw.eight.straightforward.action.ToStr;
import fayelab.ddd.fbw.eight.straightforward.action.ToWhizz;
import fayelab.ddd.fbw.eight.straightforward.predication.AlwaysTrue;
import fayelab.ddd.fbw.eight.straightforward.predication.Contains;
import fayelab.ddd.fbw.eight.straightforward.predication.Predication;
import fayelab.ddd.fbw.eight.straightforward.predication.Times;
import fayelab.ddd.fbw.eight.straightforward.rule.And;
import fayelab.ddd.fbw.eight.straightforward.rule.Atom;
import fayelab.ddd.fbw.eight.straightforward.rule.Or;
import fayelab.ddd.fbw.eight.straightforward.rule.Rule;

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
    
    public static Atom atom(Predication predication, Action action)
    {
        return new Atom(predication, action);
    }
    
    public static Rule or(Rule rule1, Rule rule2)
    {
        return new Or(rule1, rule2);
    }
    
    public static Rule or(Rule...rules)
    {
        return or(Stream.of(rules).collect(Collectors.toList()));
    }
    
    private static Rule or(List<Rule> rules)
    {
        if(rules.size() == 1)
        {
            return rules.get(0);
        }
        
        Rule firstRule = rules.remove(0);
        return or(firstRule, or(rules));
    }

    public static Rule and(Rule rule1, Rule rule2)
    {
        return new And(rule1, rule2);
    }

    public static Rule and3(Rule rule1, Rule rule2, Rule rule3)
    {
        return and(rule1, and(rule2, rule3));
    }
    
    public static Rule and4(Rule rule1, Rule rule2, Rule rule3, Rule rule4)
    {
        return and(rule1, and3(rule2, rule3, rule4));
    }

    public static Rule spec()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());
        Rule r1_8 = atom(times(8), toHazz());
    
        Rule r1 = or(r1_3, r1_5, r1_7, r1_8);
        Rule r2 = or(and4(r1_3, r1_5, r1_7, r1_8),
                     and3(r1_3, r1_5, r1_7),
                     and3(r1_3, r1_5, r1_8),
                     and3(r1_3, r1_7, r1_8),
                     and3(r1_5, r1_7, r1_8),
                     and(r1_3, r1_5),
                     and(r1_3, r1_7),
                     and(r1_3, r1_8),
                     and(r1_5, r1_7),
                     and(r1_5, r1_8),
                     and(r1_7, r1_8));
        Rule r3 = atom(contains(3), toFizz());
        Rule rd = atom(alwaysTrue(), toStr());
        
        return or(r3, r2, r1, rd);
    }
}
