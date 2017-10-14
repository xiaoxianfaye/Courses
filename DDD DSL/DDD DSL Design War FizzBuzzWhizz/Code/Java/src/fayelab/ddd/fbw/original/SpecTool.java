package fayelab.ddd.fbw.original;

import fayelab.ddd.fbw.original.action.Action;
import fayelab.ddd.fbw.original.action.ToFizz;
import fayelab.ddd.fbw.original.predication.Predication;
import fayelab.ddd.fbw.original.predication.Times;
import fayelab.ddd.fbw.original.rule.Atom;

public class SpecTool
{
    public static Predication times(int n)
    {
        return new Times(3);
    }
    
    public static Action toFizz()
    {
        return new ToFizz();
    }
    
    public static Atom atom(Predication predication, Action action)
    {
        return new Atom(predication, action);
    }
}
