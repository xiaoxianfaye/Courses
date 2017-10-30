package fayelab.ddd.fbwreloaded.complete.compiler.oo;
import java.util.Optional;

import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule.*;
import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.complete.compiler.oo.Compiler.*;
import static fayelab.ddd.fbwreloaded.complete.compiler.oo.SpecTool.*;

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
        assertTrue(compilePredication(times(3)).predicate(3));
        assertFalse(compilePredication(times(3)).predicate(4));
        assertTrue(compilePredication(contains(3)).predicate(3));
        assertFalse(compilePredication(contains(3)).predicate(4));
        assertTrue(compilePredication(alwaysTrue()).predicate(-1));
    }
    
    public void test_compile_action()
    {
        assertEquals("Fizz", compileAction(toFizz()).act(3));
        assertEquals("Buzz", compileAction(toBuzz()).act(5));
        assertEquals("Whizz", compileAction(toWhizz()).act(7));
        assertEquals("1", compileAction(toStr()).act(1));
    }
    
    public void test_compile_atom()
    {
        OOAtom o_r1_3 = compileAtom((Atom)r1_3.getData());
        assertEquals("Fizz", o_r1_3.apply(6).get());
        assertEquals(Optional.empty(), o_r1_3.apply(7));
        
        OOAtom o_r1_5 = compileAtom((Atom)r1_5.getData());
        assertEquals("Buzz", o_r1_5.apply(10).get());
        assertEquals(Optional.empty(), o_r1_5.apply(11));
        
        OOAtom o_r1_7 = compileAtom((Atom)r1_7.getData());
        assertEquals("Whizz", o_r1_7.apply(14).get());
        assertEquals(Optional.empty(), o_r1_7.apply(13));
        
        OOAtom o_r3 = compileAtom((Atom)r3.getData());
        assertEquals("Fizz", o_r3.apply(3).get());
        assertEquals("Fizz", o_r3.apply(13).get());
        assertEquals("Fizz", o_r3.apply(31).get());
        assertEquals(Optional.empty(), o_r3.apply(24));
        
        OOAtom o_rd = compileAtom((Atom)rd.getData());
        assertEquals("6", o_rd.apply(6).get());
    }
    
    public void test_compile_or()
    {
        Rule or_35 = or(r1_3, r1_5);
        OORule o_or_35 = compileOr((Or)or_35.getData());
        assertEquals("Fizz", o_or_35.apply(6).get());
        assertEquals("Buzz", o_or_35.apply(10).get());
        assertEquals(Optional.empty(), o_or_35.apply(7));
        
        Rule or_357 = or(r1_3, r1_5, r1_7);
        OORule o_or_357 = compileOr((Or)or_357.getData());
        assertEquals("Fizz", o_or_357.apply(6).get());
        assertEquals("Buzz", o_or_357.apply(10).get());
        assertEquals("Whizz", o_or_357.apply(14).get());
        assertEquals(Optional.empty(), o_or_357.apply(13));
    }
    
    public void test_compile_and()
    {
        Rule and_35 = and(r1_3, r1_5);
        OORule o_and_35 = compileAnd((And)and_35.getData());
        assertEquals("FizzBuzz", o_and_35.apply(15).get());
        assertEquals(Optional.empty(), o_and_35.apply(16));
        
        Rule and_357 = and(r1_3, r1_5, r1_7);
        OORule o_and_357 = compileAnd((And)and_357.getData());
        assertEquals("FizzBuzzWhizz", o_and_357.apply(105).get());
        assertEquals(Optional.empty(), o_and_357.apply(104));
    }
    
    public void test_compile_spec()
    {
        Rule spec = spec();
        OORule o_spec = compile(spec);
        assertEquals("Fizz", o_spec.apply(35).get());
        assertEquals("FizzWhizz", o_spec.apply(21).get());
        assertEquals("BuzzWhizz", o_spec.apply(70).get());
        assertEquals("Fizz", o_spec.apply(9).get());
        assertEquals("1", o_spec.apply(1).get());
    }
}
