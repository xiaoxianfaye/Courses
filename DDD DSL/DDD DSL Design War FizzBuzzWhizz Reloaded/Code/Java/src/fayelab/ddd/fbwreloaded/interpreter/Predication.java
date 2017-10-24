package fayelab.ddd.fbwreloaded.interpreter;

public class Predication
{
    enum Type {TIMES, CONTAINS, ALWAYSTRUE};
    
    private Type type;
    private int param;
    
    public Predication(Type type, int param)
    {
        this.type = type;
        this.param = param;
    }

    public Type getType()
    {
        return type;
    }

    public int getParam()
    {
        return param;
    }
}
