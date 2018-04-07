package fayelab.tdd.stringtransformer.instruction.reverse;

import junit.framework.TestCase;

import java.util.Map;
import fayelab.tdd.stringtransformer.instruction.reverse.Entry.Key;
import fayelab.tdd.stringtransformer.instruction.reverse.ValidatingResult.FailedReason;

import static java.util.Arrays.asList;
import static fayelab.tdd.stringtransformer.instruction.reverse.Trans.*;
import static fayelab.tdd.stringtransformer.instruction.reverse.Entry.*;
import static fayelab.tdd.stringtransformer.instruction.reverse.Entry.Key.*;
import static fayelab.tdd.stringtransformer.instruction.reverse.Interaction.*;

public class InteractionTest extends TestCase
{
    private Map<Key, Value<?>> data;

    @Override
    protected void setUp()
    {
        data = mockData();
    }

    public void test_equals()
    {
        assertEquals(mockData(), data);
    }

    public void test_toString()
    {
        assertEquals("{AVAIL_TRANSES=[Upper, Lower, TrimPrefixSpaces], AVAIL_SELECTED_INDEX=0}", data.toString());
    }

    public void test_toStrArray()
    {
        String[] actual = new Value<>(asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS)).toStrArray();
        assertEquals(asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS), asList(actual));
    }

    public void test_toInt()
    {
        assertEquals(0, new Value<>(0).toInt());
    }

    public void test_toStr()
    {
        assertEquals(UPPER_TRANS, new Value<>(UPPER_TRANS).toStr());
    }

    public void test_toFailedReason()
    {
        assertEquals(FailedReason.SOURCE_STR_EMPTY, new Value<>(FailedReason.SOURCE_STR_EMPTY).toFailedReason());
    }

    private Map<Key, Value<?>> mockData()
    {
        return interactionData(
                entry(AVAIL_TRANSES, asList(UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS)),
                entry(AVAIL_SELECTED_INDEX, 0));
    }
}