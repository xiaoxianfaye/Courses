package fayelab.ddd.fbw.original.rule;

import fayelab.ddd.fbw.original.action.ToFizz;
import fayelab.ddd.fbw.original.predication.Times;
import junit.framework.TestCase;

import static fayelab.ddd.fbw.original.SpecTool.*;

public class RuleTest extends TestCase
{
    public void test_atom_rule_1_3()
    {
        Rule r1_3 = new Atom(new Times(3), new ToFizz());
        checkResult(true, "Fizz", r1_3.apply(3));
        checkResult(false, "", r1_3.apply(4));
        
        Rule r1_3_t = atom(times(3), toFizz());
        checkResult(true, "Fizz", r1_3_t.apply(3));
        checkResult(false, "", r1_3_t.apply(4));
    }

    private void checkResult(boolean expectedSucceeded, String expectedStr, Result actual)
    {
        assertEquals(expectedSucceeded, actual.isSucceeded());
        assertEquals(expectedStr, actual.getStr());
    }
}
