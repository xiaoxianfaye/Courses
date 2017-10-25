package fayelab.ddd.fbwreloaded.complete.interpreter;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.complete.interpreter.Parser.*;
import static java.util.Arrays.asList;

public class ParserTest extends TestCase
{
    public void test_isNotBlank()
    {
        assertFalse(isNotBlank(""));
        assertFalse(isNotBlank(" "));
        assertFalse(isNotBlank("  "));
        assertTrue(isNotBlank("  123  "));
    }
    
    public void test_normalize()
    {
        assertEquals("spec or r3 r2 r1 rd", normalize(" spec  or r3 r2 r1  rd  "));
    }
    
    public void test_tokenize()
    {
        assertEquals(asList("spec", "or", "r3", "r2", "r1", "rd"), tokenize("spec or r3 r2 r1 rd"));
    }
    
//    public void test_parse_atom()
//    {
//        
//        Map<String, Rule> actual = new HashMap<>();
//        parseRuleTokens(asList("r1_3", "atom", "times", "3", "to_fizz"), actual);
//        parseRuleTokens(asList("r1_5", "atom", "times", "5", "to_buzz"), actual);
//        parseRuleTokens(asList("r1_7", "atom", "times", "7", "to_whizz"), actual);
//        parseRuleTokens(asList("r3", "atom", "contains", "3", "to_fizz"), actual);
//        parseRuleTokens(asList("rd", "atom", "always_true", "to_str"), actual);
//        
//        Map<String, Rule> expected = new HashMap<>(); 
//        expected.put("r1_3", atom(times(3), toFizz()));
//        expected.put("r1_5", atom(times(5), toBuzz()));
//        expected.put("r1_7", atom(times(7), toWhizz()));
//        expected.put("r3", atom(contains(3), toFizz()));
//        expected.put("rd", atom(alwaysTrue(), toStr()));
//        assertMap(expected, actual);
//    }
    
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
