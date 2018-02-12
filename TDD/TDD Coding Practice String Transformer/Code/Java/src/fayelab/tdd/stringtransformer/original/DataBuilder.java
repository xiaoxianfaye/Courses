package fayelab.tdd.stringtransformer.original;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import fayelab.tdd.stringtransformer.original.Param.Name;
import fayelab.tdd.stringtransformer.original.Presenter.ValidatingFailedReason;

public class DataBuilder
{
    static Map<Name, Object> build(Param<?>...params)
    {
        Map<Name, Object> result = new HashMap<>();
        Stream.of(params).forEach(param -> result.put(param.getName(), param.getValue()));
        return result;
    }
    
    @SuppressWarnings("unchecked")
    static String[] toStringArray(Object value)
    {
        return ((List<String>)value).toArray(new String[] {});
    }
    
    static int toInt(Object value)
    {
        return ((Integer)value).intValue();
    }
    
    static String toStr(Object value)
    {
        return (String)value;
    }
    
    static ValidatingFailedReason toValidatingFailedReason(Object value)
    {
        return (ValidatingFailedReason)value;
    }
}

class Param<T>
{
    enum Name
    {
        AVAILABLE_TRANSNAMES, AVAILABLE_SELECTED_INDEX, AVAILABLE_SELECTED_TRANSNAME,
        CHAIN_TRANSNAMES, CHAIN_SELECTED_INDEX, CHAIN_SELECTED_TRANSNAME,
        SOURCESTR, RESULTSTR,
        VALIDATING_FAILED_REASON
    };
    
    private Name name;
    private T value;
    
    public Param(Name name, T value)
    {
        this.name = name;
        this.value = value;
    }

    public Name getName()
    {
        return name;
    }

    public T getValue()
    {
        return value;
    }
}
