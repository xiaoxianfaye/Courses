package fayelab.ddd.fbwreloaded.complete.compiler.functional;

public class Action
{
    enum Type {TOFIZZ, TOBUZZ, TOWHIZZ, TOSTR};

    private Type type;

    public Action(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }
}
