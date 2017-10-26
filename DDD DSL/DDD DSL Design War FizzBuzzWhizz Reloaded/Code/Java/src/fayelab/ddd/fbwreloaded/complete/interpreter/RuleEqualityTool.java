package fayelab.ddd.fbwreloaded.complete.interpreter;

public class RuleEqualityTool
{
    public static boolean equal(Rule rule1, Rule rule2)
    {
        if(rule1 == rule2)
        {
            return true;
        }
        
        if(rule1 != null && rule2 != null && rule1.getType() == rule2.getType())
        {
            switch(rule1.getType())
            {
                case ATOM:
                    return equalAtom((Atom)rule1.getData(), (Atom)rule2.getData());
                case AND:
                    return equalAnd((And)rule1.getData(), (And)rule2.getData());
                case OR:
                    return equalOr((Or)rule1.getData(), (Or)rule2.getData());
                default:
                    return false;
            }
        }
        
        return false;
    }
    
    static boolean equalPredication(Predication predication1, Predication predication2)
    {
        return predication1.getType() == predication2.getType()
            && predication1.getParam() == predication2.getParam();
    }
    
    static boolean equalAction(Action action1, Action action2)
    {
        return action1.getType() == action2.getType();
    }
    
    static boolean equalAtom(Atom atom1, Atom atom2)
    {
        return equalPredication(atom1.getPredication(), atom2.getPredication()) 
            && equalAction(atom1.getAction(), atom2.getAction());
    }
    
    static boolean equalOr(Or or1, Or or2)
    {
        return equal(or1.getRule1(), or2.getRule1())
            && equal(or1.getRule2(), or2.getRule2());
    }
    
    static boolean equalAnd(And and1, And and2)
    {
        return equal(and1.getRule1(), and2.getRule1())
            && equal(and1.getRule2(), and2.getRule2());
    }
}
