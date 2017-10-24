package fayelab.ddd.fbwreloaded.interpreter;

import static fayelab.ddd.fbwreloaded.interpreter.Action.Type.*;
import static fayelab.ddd.fbwreloaded.interpreter.Predication.Type.*;
import static fayelab.ddd.fbwreloaded.interpreter.Rule.Type.*;

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
        return new Rule(AND, new And(rule1, rule2));
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
