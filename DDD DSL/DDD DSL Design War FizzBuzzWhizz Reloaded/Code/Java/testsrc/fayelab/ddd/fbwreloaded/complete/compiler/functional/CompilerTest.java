package fayelab.ddd.fbwreloaded.complete.compiler.functional;

import java.util.Optional;
import java.util.function.Function;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.complete.compiler.functional.Compiler.*;
import static fayelab.ddd.fbwreloaded.complete.compiler.functional.SpecTool.*;

public class CompilerTest extends TestCase
{
    private Rule r1_3;
    private Rule r1_5;
    private Rule r1_7;
    private Rule r3;
    private Rule rd;
    
    @Override
    protected void setUp()
    {
        r1_3 = atom(times(3), toFizz());
        r1_5 = atom(times(5), toBuzz());
        r1_7 = atom(times(7), toWhizz());
        r3 = atom(contains(3), toFizz());
        rd = atom(alwaysTrue(), toStr());
    }

    public void test_compile_predication()
    {
        assertTrue(compilePredication(times(3)).test(3));
        assertFalse(compilePredication(times(3)).test(4));
        assertTrue(compilePredication(contains(3)).test(3));
        assertFalse(compilePredication(contains(3)).test(4));
        assertTrue(compilePredication(alwaysTrue()).test(-1));
    }
    
    public void test_compile_action()
    {
        assertEquals("Fizz", compileAction(toFizz()).apply(3));
        assertEquals("Buzz", compileAction(toBuzz()).apply(5));
        assertEquals("Whizz", compileAction(toWhizz()).apply(7));
        assertEquals("1", compileAction(toStr()).apply(1));
    }
    
    public void test_compile_atom()
    {
        Function<Integer, Optional<String>> c_r1_3 = compileAtom((Atom)r1_3.getData());
        assertEquals("Fizz", c_r1_3.apply(6).get());
        assertEquals(Optional.empty(), c_r1_3.apply(7));
        
        Function<Integer, Optional<String>> c_r1_5 = compileAtom((Atom)r1_5.getData());
        assertEquals("Buzz", c_r1_5.apply(10).get());
        assertEquals(Optional.empty(), c_r1_5.apply(11));
        
        Function<Integer, Optional<String>> c_r1_7 = compileAtom((Atom)r1_7.getData());
        assertEquals("Whizz", c_r1_7.apply(14).get());
        assertEquals(Optional.empty(), c_r1_7.apply(13));
        
        Function<Integer, Optional<String>> c_r3 = compileAtom((Atom)r3.getData());
        assertEquals("Fizz", c_r3.apply(3).get());
        assertEquals("Fizz", c_r3.apply(13).get());
        assertEquals("Fizz", c_r3.apply(31).get());
        assertEquals(Optional.empty(), c_r3.apply(24));
        
        Function<Integer, Optional<String>> c_rd = compileAtom((Atom)rd.getData());
        assertEquals("6", c_rd.apply(6).get());
    }
    
    public void test_compile_or()
    {
        Rule or_35 = or(r1_3, r1_5);
        Function<Integer, Optional<String>> c_or_35 = compileOr((Or)or_35.getData());
        assertEquals("Fizz", c_or_35.apply(6).get());
        assertEquals("Buzz", c_or_35.apply(10).get());
        assertEquals("Fizz", c_or_35.apply(15).get());
        assertEquals(Optional.empty(), c_or_35.apply(7));
        
        Rule or_357 = or(r1_3, r1_5, r1_7);
        Function<Integer, Optional<String>> c_or_357 = compileOr((Or)or_357.getData());
        assertEquals("Fizz", c_or_357.apply(6).get());
        assertEquals("Buzz", c_or_357.apply(10).get());
        assertEquals("Whizz", c_or_357.apply(14).get());
        assertEquals(Optional.empty(), c_or_357.apply(13));
    }
    
    public void test_compile_and()
    {
        Rule and_35 = and(r1_3, r1_5);
        Function<Integer, Optional<String>> c_and_35 = compileAnd((And)and_35.getData());
        assertEquals(Optional.empty(), c_and_35.apply(3));
        assertEquals(Optional.empty(), c_and_35.apply(5));
        assertEquals("FizzBuzz", c_and_35.apply(15).get());
        assertEquals(Optional.empty(), c_and_35.apply(16));
        
        Rule and_357 = and(r1_3, r1_5, r1_7);
        Function<Integer, Optional<String>> c_and_357 = compileAnd((And)and_357.getData());
        assertEquals("FizzBuzzWhizz", c_and_357.apply(3*5*7).get());
        assertEquals(Optional.empty(), c_and_357.apply(104));
    }
    
    public void test_compile_spec()
    {
        Rule spec = spec();
        Function<Integer, Optional<String>> c_spec = compile(spec);
        assertEquals("Fizz", c_spec.apply(35).get());
        assertEquals("FizzWhizz", c_spec.apply(21).get());
        assertEquals("BuzzWhizz", c_spec.apply(70).get());
        assertEquals("Fizz", c_spec.apply(9).get());
        assertEquals("1", c_spec.apply(1).get());
    }
}
