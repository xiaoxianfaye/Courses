package fayelab.tdd.stringtransformer.instruction.original;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import fayelab.tdd.stringtransformer.instruction.original.Entry.Key;

public class Interaction
{
    static final int NONE_SELECTED_INDEX = -1;
    static final String NONE_SELECTED_TRANS = null;

    static Map<Key, Value<?>> interactionData(Entry<?>...entries)
    {
        return Stream.of(entries)
                     .collect(LinkedHashMap::new, 
                              (resultMap, entry) -> resultMap.put(entry.getKey(), new Value<>(entry.getValue())), 
                              (map1, map2) -> map1.putAll(map2));
    }
}

class Entry<V>
{
    enum Key
    {
        AVAIL_TRANSES, AVAIL_SELECTED_INDEX, AVAIL_SELECTED_TRANS,
        CHAIN_TRANSES, CHAIN_SELECTED_INDEX, CHAIN_SELECTED_TRANS,
        VALIDATING_FAILED_REASON
    }

    private Key key;
    private V value;

    static <V> Entry<V> entry(Key key, V value)
    {
        return new Entry<>(key, value);
    }

    private Entry(Key key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public Key getKey()
    {
        return key;
    }

    public V getValue()
    {
        return value;
    }
}

class Value<T>
{
    private T value;

    Value(T value)
    {
        this.value = value;
    }

    public T get()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return Objects.toString(value);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }

        if(obj == null)
        {
            return false;
        }

        if(getClass() != obj.getClass())
        {
            return false;
        }

        return Objects.equals(value, ((Value<?>)obj).value);
    }

    @Override
    public int hashCode()
    {
        return 31 + Objects.hashCode(value);
    }

    @SuppressWarnings("unchecked")
    String[] toStrArray()
    {
        return ((List<String>)value).toArray(new String[] {});
    }

    int toInt()
    {
        return ((Integer)value).intValue();
    }

    String toStr()
    {
        return (String)value;
    }
}