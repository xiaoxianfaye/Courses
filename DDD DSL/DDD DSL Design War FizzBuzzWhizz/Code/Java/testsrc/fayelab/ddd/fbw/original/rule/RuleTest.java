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
    
    public void test_atom_rule_1_5()
    {
        Rule r1_5 = atom(times(5), toBuzz());
        checkResult(true, "Buzz", r1_5.apply(10));
        checkResult(false, "", r1_5.apply(11));
    }
    
    public void test_atom_rule_1_7()
    {
        Rule r1_7 = atom(times(7), toWhizz());
        checkResult(true, "Whizz", r1_7.apply(14));
        checkResult(false, "", r1_7.apply(13));
    }
    
    public void test_or_rule()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        
        Rule or_35 = or(r1_3, r1_5);
        checkResult(true, "Fizz", or_35.apply(6));
        checkResult(true, "Buzz", or_35.apply(10));
        checkResult(true, "Fizz", or_35.apply(15));
        checkResult(false, "", or_35.apply(7));
    }

    public void test_rule_1()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());
        
        Rule r1 = or3(r1_3, r1_5, r1_7);
        checkResult(true, "Fizz", r1.apply(6));
        checkResult(true, "Buzz", r1.apply(10));
        checkResult(true, "Whizz", r1.apply(14));
        checkResult(false, "", r1.apply(13));
    }
    
    public void test_and_rule()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        
        Rule and_35 = and(r1_3, r1_5);
        checkResult(false, "", and_35.apply(3));
        checkResult(false, "", and_35.apply(5));
        checkResult(true, "FizzBuzz", and_35.apply(15));
        checkResult(false, "", and_35.apply(16));
    }
    
    public void test_rule_2()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());
        
        Rule r2 = or4(and3(r1_3, r1_5, r1_7),
                      and(r1_3, r1_5),
                      and(r1_3, r1_7),
                      and(r1_5, r1_7));
        checkResult(false, "", r2.apply(3));
        checkResult(false, "", r2.apply(5));
        checkResult(false, "", r2.apply(7));
        checkResult(true, "FizzBuzzWhizz", r2.apply(3*5*7));
        checkResult(false, "", r2.apply(104));
        checkResult(true, "FizzBuzz", r2.apply(15));
        checkResult(false, "", r2.apply(14));
        checkResult(true, "FizzWhizz", r2.apply(21));
        checkResult(false, "", r2.apply(22));
        checkResult(true, "BuzzWhizz", r2.apply(35));
        checkResult(false, "", r2.apply(34));
    }
    
    public void test_rule_3()
    {
        Rule r3 = atom(contains(3), toFizz());
        checkResult(true, "Fizz", r3.apply(3));
        checkResult(true, "Fizz", r3.apply(13));
        checkResult(true, "Fizz", r3.apply(31));
        checkResult(false, "", r3.apply(24));
    }
    
    public void test_default_rule()
    {
        Rule rd = atom(alwaysTrue(), toStr());
        checkResult(true, "1", rd.apply(1));
        checkResult(true, "3", rd.apply(3));
    }
    
    public void test_spec()
    {
        Rule spec = spec();
        checkResult(true, "Fizz", spec.apply(35));
        checkResult(true, "FizzBuzz", spec.apply(15));
        checkResult(true, "FizzWhizz", spec.apply(21));
        checkResult(true, "BuzzWhizz", spec.apply(70));
        checkResult(true, "Fizz", spec.apply(9));
        checkResult(true, "1", spec.apply(1));
    }

    private void checkResult(boolean expectedSucceeded, String expectedStr, Result actual)
    {
        assertEquals(expectedSucceeded, actual.isSucceeded());
        assertEquals(expectedStr, actual.getStr());
    }
}
