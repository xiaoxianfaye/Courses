package fayelab.ddd.fbwreloaded.complete.interpreter;

import junit.framework.TestCase;

import java.util.List;

import static fayelab.ddd.fbwreloaded.complete.interpreter.Parser.*;
import static java.util.Arrays.asList;

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
    
    public void test_parseTokens()
    {        
//        List<List<String>> spec1 = asList(
//                asList("r1_3", "atom", "times", "3", "to_fizz"),);
//        
//        assertEquals(SpecTool.spec(), parseTokens(SPEC_TOKENS));
    }
    
//  public void test_parse_atom()
//  {
//      
//      Map<String, Rule> actual = new HashMap<>();
//      parseRuleTokens(asList("r1_3", "atom", "times", "3", "to_fizz"), actual);
//      parseRuleTokens(asList("r1_5", "atom", "times", "5", "to_buzz"), actual);
//      parseRuleTokens(asList("r1_7", "atom", "times", "7", "to_whizz"), actual);
//      parseRuleTokens(asList("r3", "atom", "contains", "3", "to_fizz"), actual);
//      parseRuleTokens(asList("rd", "atom", "always_true", "to_str"), actual);
//      
//      Map<String, Rule> expected = new HashMap<>(); 
//      expected.put("r1_3", atom(times(3), toFizz()));
//      expected.put("r1_5", atom(times(5), toBuzz()));
//      expected.put("r1_7", atom(times(7), toWhizz()));
//      expected.put("r3", atom(contains(3), toFizz()));
//      expected.put("rd", atom(alwaysTrue(), toStr()));
//      assertMap(expected, actual);
//  }
    
    

    
//    private void assertMap(Map<String, Rule> expected, Map<String, Rule> actual)
//    {
//        Set<String> actualKeySet = actual.keySet();
//        
//        assertEquals(expected.keySet(), actualKeySet);
//        
//        expected.forEach((name, rule) -> {
//            String expectedRuleStr = PrintInterpreter.interpret(rule);
//            Rule actualRule = actual.get(name);
//            assertNotNull(actualRule);
//            String actualRuleStr = PrintInterpreter.interpret(actualRule);
//            assertEquals(expectedRuleStr, actualRuleStr);
//        });
//    }
}
