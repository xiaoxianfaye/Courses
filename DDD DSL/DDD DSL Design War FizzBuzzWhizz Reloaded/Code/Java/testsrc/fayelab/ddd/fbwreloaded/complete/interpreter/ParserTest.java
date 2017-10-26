package fayelab.ddd.fbwreloaded.complete.interpreter;

import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

import static fayelab.ddd.fbwreloaded.complete.interpreter.Parser.*;
import static java.util.Arrays.asList;

import java.util.HashMap;

import static fayelab.ddd.fbwreloaded.complete.interpreter.SpecTool.*;
import static fayelab.ddd.fbwreloaded.complete.interpreter.RuleEqualityTool.equal;

public class ParserTest extends TestCase
{
    private static final List<List<String>> SPEC_TOKENS = asList(
            asList("r1_3", "atom", "times", "3", "to_fizz"),
            asList("r1_5", "atom", "times", "5", "to_buzz"),
            asList("r1_7", "atom", "times", "7", "to_whizz"),
            asList("r1", "or", "r1_3", "r1_5", "r1_7"),
            asList("r1_357", "and", "r1_3", "r1_5", "r1_7"),
            asList("r1_35", "and", "r1_3", "r1_5"),
            asList("r1_37", "and", "r1_3", "r1_7"),
            asList("r1_57", "and", "r1_5", "r1_7"),
            asList("r2", "or", "r1_357", "r1_35", "r1_37", "r1_57"),
            asList("r3", "atom", "contains", "3", "to_fizz"),
            asList("rd", "atom", "always_true", "to_str"),
            asList("spec", "or", "r3", "r2", "r1", "rd"));

    private Rule r1_3;
    private Rule r1_5;
    private Rule r1_7;
    private Map<String, Rule> ruleNameAndRuleMap;
    
    @Override
    protected void setUp()
    {
        r1_3 = atom(times(3), toFizz());
        r1_5 = atom(times(5), toBuzz());
        r1_7 = atom(times(7), toWhizz());
        
        ruleNameAndRuleMap = new HashMap<String, Rule>();
        ruleNameAndRuleMap.put("r1_3", r1_3);
        ruleNameAndRuleMap.put("r1_5", r1_5);
        ruleNameAndRuleMap.put("r1_7", r1_7);
    }
    
    public void test_tokenize()
    {
        List<String> ruleDescs = asList(
                "r1_3 atom times 3 to_fizz",
                "r1_5 atom times 5 to_buzz",
                "r1_7 atom times 7 to_whizz",
                "",
                "r1 or r1_3 r1_5 r1_7",
                "",
                "r1_357 and r1_3 r1_5 r1_7",
                "r1_35 and r1_3 r1_5",
                "r1_37 and r1_3 r1_7",
                "r1_57 and r1_5 r1_7",
                "",
                "r2 or r1_357 r1_35 r1_37 r1_57",
                "",
                "r3 atom contains 3 to_fizz",
                "",
                "rd atom always_true to_str",
                "  ",
                " spec  or r3 r2 r1  rd  ");
        
        assertEquals(SPEC_TOKENS, tokenize(ruleDescs));
    }
    
    public void test_parseAtom()
    {
        assertTrue(equal(atom(times(3), toFizz()), parseAtom(asList("r1_3", "atom", "times", "3", "to_fizz"))));
        assertTrue(equal(atom(contains(3), toFizz()), parseAtom(asList("r3", "atom", "contains", "3", "to_fizz"))));
        assertTrue(equal(atom(alwaysTrue(), toStr()), parseAtom(asList("rd", "atom", "always_true", "to_str"))));
    }
    
    public void test_parseOr()
    {
        assertTrue(equal(or(r1_3, r1_5, r1_7), 
                   parseOr(asList("or", "r1", "r1_3", "r1_5", "r1_7"), ruleNameAndRuleMap)));
    }
    
    public void test_parseAnd()
    {
        assertTrue(equal(and(r1_3, r1_5, r1_7), 
                   parseAnd(asList("and", "r1_357", "r1_3", "r1_5", "r1_7"), ruleNameAndRuleMap)));
    }
    
    public void test_parseRuleTokens()
    {
        Map<String, Rule> actual = new HashMap<>();
        
        SPEC_TOKENS.stream().forEach(ruleTokens -> parseRuleTokens(ruleTokens, actual));

        Map<String, Rule> expected = new HashMap<>(); 
        expected.put("r1_3", r1_3);
        expected.put("r1_5", r1_5);
        expected.put("r1_7", r1_7);
        
        expected.put("r1", or(r1_3, r1_5, r1_7));
        
        Rule r1_357 = and(r1_3, r1_5, r1_7);
        expected.put("r1_357", r1_357);
        Rule r1_35 = and(r1_3, r1_5);
        expected.put("r1_35", r1_35);
        Rule r1_37 = and(r1_3, r1_7);
        expected.put("r1_37", r1_37);
        Rule r1_57 = and(r1_5, r1_7);
        expected.put("r1_57", r1_57);
        
        expected.put("r2", or(r1_357, r1_35, r1_37, r1_57));
        
        Rule r3 = atom(contains(3), toFizz());
        expected.put("r3", r3);
        Rule rd = atom(alwaysTrue(), toStr());
        expected.put("rd", rd);
        
        expected.put("spec", rd);
        
        assertMap(expected, actual);
    }

    public void test_parseTokens()
    {        
//        List<List<String>> spec1 = asList(
//                asList("r1_3", "atom", "times", "3", "to_fizz"),);
//        
//        assertEquals(SpecTool.spec(), parseTokens(SPEC_TOKENS));
    }
    
    private void assertMap(Map<String, Rule> expected, Map<String, Rule> actual)
    {
        assertEquals(expected.keySet(), actual.keySet());
        
        expected.keySet().forEach(ruleName -> {assertTrue(equal(expected.get(ruleName), actual.get(ruleName)));});
    }
}
