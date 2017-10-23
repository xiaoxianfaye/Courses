package fayelab.ddd.fbwreloaded.oo;

import fayelab.ddd.fbwreloaded.oo.action.Action;
import fayelab.ddd.fbwreloaded.oo.action.ToFizz;
import fayelab.ddd.fbwreloaded.oo.action.ToStr;
import fayelab.ddd.fbwreloaded.oo.action.ToBuzz;
import fayelab.ddd.fbwreloaded.oo.action.ToWhizz;
import fayelab.ddd.fbwreloaded.oo.predication.AlwaysTrue;
import fayelab.ddd.fbwreloaded.oo.predication.Contains;
import fayelab.ddd.fbwreloaded.oo.predication.Predication;
import fayelab.ddd.fbwreloaded.oo.predication.Times;
import fayelab.ddd.fbwreloaded.oo.rule.And;
import fayelab.ddd.fbwreloaded.oo.rule.Atom;
import fayelab.ddd.fbwreloaded.oo.rule.Or;
import fayelab.ddd.fbwreloaded.oo.rule.Rule;

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
    
    public static Atom atom(Predication predication, Action action)
    {
        return new Atom(predication, action);
    }
    
    public static Rule or(Rule rule1, Rule rule2)
    {
        return new Or(rule1, rule2);
    }
    
    public static Rule or3(Rule rule1, Rule rule2, Rule rule3)
    {
        return or(rule1, or(rule2, rule3));
    }

    public static Rule or4(Rule rule1, Rule rule2, Rule rule3, Rule rule4)
    {
        return or(rule1, or3(rule2, rule3, rule4));
    }
    
    public static Rule and(Rule rule1, Rule rule2)
    {
        return new And(rule1, rule2);
    }

    public static Rule and3(Rule rule1, Rule rule2, Rule rule3)
    {
        return and(rule1, and(rule2, rule3));
    }

    public static Rule spec()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());
    
        Rule r1 = or3(r1_3, r1_5, r1_7);
        Rule r2 = or4(and3(r1_3, r1_5, r1_7),
                      and(r1_3, r1_5),
                      and(r1_3, r1_7),
                      and(r1_5, r1_7));
        Rule r3 = atom(contains(3), toFizz());
        Rule rd = atom(alwaysTrue(), toStr());
        
        return or4(r3, r2, r1, rd);
    }
}
