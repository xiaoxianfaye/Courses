package fayelab.ddd.fbwreloaded.complete.interpreter;

import java.util.Optional;
import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static fayelab.ddd.fbwreloaded.complete.interpreter.Interpreter.interpret;
import static fayelab.ddd.fbwreloaded.complete.interpreter.SpecTool.*;

public class InterpreterTest extends TestCase
{
    public void test_atom()
    {
        Rule r1_3 = atom(times(3), toFizz());
        assertEquals("Fizz", interpret(r1_3, 6).get());
        assertEquals(Optional.empty(), interpret(r1_3, 7));
        
        Rule r1_5 = atom(times(5), toBuzz());
        assertEquals("Buzz", interpret(r1_5, 10).get());
        assertEquals(Optional.empty(), interpret(r1_5, 11));
        
        Rule r1_7 = atom(times(7), toWhizz());
        assertEquals("Whizz", interpret(r1_7, 14).get());
        assertEquals(Optional.empty(), interpret(r1_7, 13));
        
        Rule r3 = atom(contains(3), toFizz());
        assertEquals("Fizz", interpret(r3, 3).get());
        assertEquals("Fizz", interpret(r3, 13).get());
        assertEquals("Fizz", interpret(r3, 31).get());
        assertEquals(Optional.empty(), interpret(r3, 24));
        
        Rule rd = atom(alwaysTrue(), toStr());
        assertEquals("6", interpret(rd, 6).get());
    }
    
    public void test_and()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());
        
        Rule and_35 = and(r1_3, r1_5);
        assertEquals("FizzBuzz", interpret(and_35, 15).get());
        assertEquals(Optional.empty(), interpret(and_35, 16));
        
        Rule and_37 = and(r1_3, r1_7);
        assertEquals("FizzWhizz", interpret(and_37, 21).get());
        assertEquals(Optional.empty(), interpret(and_37, 22));
        
        Rule and_57 = and(r1_5, r1_7);
        assertEquals("BuzzWhizz", interpret(and_57, 35).get());
        assertEquals(Optional.empty(), interpret(and_57, 36));
                
        Rule and_357 = and(r1_3, r1_5, r1_7);
        assertEquals("FizzBuzzWhizz", interpret(and_357, 105).get());
        assertEquals(Optional.empty(), interpret(and_357, 104));
        
        Rule and_357_2 = and(asList(r1_3, r1_5, r1_7));
        assertEquals("FizzBuzzWhizz", interpret(and_357_2, 105).get());
        assertEquals(Optional.empty(), interpret(and_357_2, 104));
    }
    
    public void test_or()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());
        
        Rule rd = atom(alwaysTrue(), toStr());
        
        Rule or_35 = or(r1_3, r1_5);
        assertEquals("Fizz", interpret(or_35, 6).get());
        assertEquals("Buzz", interpret(or_35, 10).get());
        assertEquals(Optional.empty(), interpret(or_35, 7));
        
        Rule or_357 = or(r1_3, r1_5, r1_7);
        assertEquals("Fizz", interpret(or_357, 6).get());
        assertEquals("Fizz", interpret(or_357, 6).get());
        assertEquals("Buzz", interpret(or_357, 10).get());
        assertEquals("Whizz", interpret(or_357, 14).get());
        assertEquals(Optional.empty(), interpret(or_357, 13));
        
        Rule or_357d = or(r1_3, r1_5, r1_7, rd);
        assertEquals("Fizz", interpret(or_357d, 6).get());
        assertEquals("Buzz", interpret(or_357d, 10).get());
        assertEquals("Whizz", interpret(or_357d, 14).get());
        assertEquals("13", interpret(or_357d, 13).get());
        
        Rule or_357d_2 = or(asList(r1_3, r1_5, r1_7, rd));
        assertEquals("Fizz", interpret(or_357d_2, 6).get());
        assertEquals("Buzz", interpret(or_357d_2, 10).get());
        assertEquals("Whizz", interpret(or_357d_2, 14).get());
        assertEquals("13", interpret(or_357d_2, 13).get());
    }
}
