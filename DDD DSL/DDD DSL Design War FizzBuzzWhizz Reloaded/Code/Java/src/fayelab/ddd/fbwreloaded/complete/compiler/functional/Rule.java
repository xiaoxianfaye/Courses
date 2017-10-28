package fayelab.ddd.fbwreloaded.complete.compiler.functional;

public class Rule
{
    enum Type {ATOM, AND, OR};
    
    private Type type;
    private Object data;
    
    public Rule(Type type, Object data)
    {
        this.type = type;
        this.data = data;
    }

    public Type getType()
    {
        return type;
    }

    public Object getData()
    {
        return data;
    }
}

class Atom
{
    private Predication predication;
    private Action action;

    public Atom(Predication predication, Action action)
    {
        this.predication = predication;
        this.action = action;
    }

    public Predication getPredication()
    {
        return predication;
    }

    public Action getAction()
    {
        return action;
    }
}

class And
{
    private Rule rule1;
    private Rule rule2;

    public And(Rule rule1, Rule rule2)
    {
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    public Rule getRule1()
    {
        return rule1;
    }

    public Rule getRule2()
    {
        return rule2;
    }
}

class Or
{
    private Rule rule1;
    private Rule rule2;

    public Or(Rule rule1, Rule rule2)
    {
        this.rule1 = rule1;
        this.rule2 = rule2;
    }

    public Rule getRule1()
    {
        return rule1;
    }

    public Rule getRule2()
    {
        return rule2;
    }
}
