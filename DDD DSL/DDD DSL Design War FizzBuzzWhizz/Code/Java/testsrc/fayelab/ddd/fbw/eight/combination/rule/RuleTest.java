package fayelab.ddd.fbw.eight.combination.rule;

import java.util.List;

import fayelab.ddd.fbw.eight.combination.action.ToFizz;
import fayelab.ddd.fbw.eight.combination.predication.Times;

import junit.framework.TestCase;

import static fayelab.ddd.fbw.eight.combination.ListTool.*;
import static fayelab.ddd.fbw.eight.combination.SpecTool.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

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
    
    public void test_atom_rule_1_8()
    {
        Rule r1_8 = atom(times(8), toHazz());
        checkResult(true, "Hazz", r1_8.apply(16));
        checkResult(false, "", r1_8.apply(13));
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
        Rule r1_8 = atom(times(8), toHazz());
        
        Rule r1 = or(r1_3, r1_5, r1_7, r1_8);
        checkResult(true, "Fizz", r1.apply(6));
        checkResult(true, "Buzz", r1.apply(10));
        checkResult(true, "Whizz", r1.apply(14));
        checkResult(true, "Hazz", r1.apply(16));
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
        Rule r1_8 = atom(times(8), toHazz());
        
        List<Rule> atomRules = asList(r1_3, r1_5, r1_7, r1_8);
        
        List<List<Rule>> lstOfRules = flatten(asList(combinate(atomRules, 4),
                                                     combinate(atomRules, 3),
                                                     combinate(atomRules, 2)));

        Rule r2 = or(lstOfRules.stream().map(rules -> and(rules)).collect(toList()));

        checkResult(false, "", r2.apply(3));
        checkResult(false, "", r2.apply(5));
        checkResult(false, "", r2.apply(7));
        checkResult(false, "", r2.apply(8));
        checkResult(true, "FizzBuzzWhizzHazz", r2.apply(3*5*7*8));
        checkResult(false, "", r2.apply(841));
        checkResult(true, "FizzBuzzWhizz", r2.apply(3*5*7));
        checkResult(false, "", r2.apply(104));
        checkResult(true, "FizzBuzzHazz", r2.apply(3*5*8));
        checkResult(false, "", r2.apply(121));
        checkResult(true, "FizzWhizzHazz", r2.apply(3*7*8));
        checkResult(false, "", r2.apply(167));
        checkResult(true, "BuzzWhizzHazz", r2.apply(5*7*8));
        checkResult(false, "", r2.apply(281));
        checkResult(true, "FizzBuzz", r2.apply(15));
        checkResult(false, "", r2.apply(14));
        checkResult(true, "FizzWhizz", r2.apply(21));
        checkResult(false, "", r2.apply(22));
        checkResult(true, "FizzHazz", r2.apply(24));
        checkResult(false, "", r2.apply(23));
        checkResult(true, "BuzzWhizz", r2.apply(35));
        checkResult(false, "", r2.apply(34));
        checkResult(true, "BuzzHazz", r2.apply(40));
        checkResult(false, "", r2.apply(41));
        checkResult(true, "WhizzHazz", r2.apply(56));
        checkResult(false, "", r2.apply(55));
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
        checkResult(true, "FizzBuzzWhizzHazz", spec.apply(3*5*7*8));
        checkResult(true, "FizzBuzzWhizz", spec.apply(3*5*7));
        checkResult(true, "FizzBuzzHazz", spec.apply(3*5*8));
        checkResult(true, "FizzWhizzHazz", spec.apply(3*7*8));
        checkResult(true, "BuzzWhizzHazz", spec.apply(5*7*8));
        checkResult(true, "FizzBuzz", spec.apply(15));
        checkResult(true, "FizzWhizz", spec.apply(21));
        checkResult(true, "FizzHazz", spec.apply(24));
        checkResult(true, "BuzzWhizz", spec.apply(70));
        checkResult(true, "BuzzHazz", spec.apply(40));
        checkResult(true, "WhizzHazz", spec.apply(56));
        checkResult(true, "Fizz", spec.apply(9));
        checkResult(true, "Buzz", spec.apply(5));
        checkResult(true, "Whizz", spec.apply(7));
        checkResult(true, "Hazz", spec.apply(8));
        checkResult(true, "1", spec.apply(1));
    }

    private void checkResult(boolean expectedSucceeded, String expectedStr, Result actual)
    {
        assertEquals(expectedSucceeded, actual.isSucceeded());
        assertEquals(expectedStr, actual.getStr());
    }
}
