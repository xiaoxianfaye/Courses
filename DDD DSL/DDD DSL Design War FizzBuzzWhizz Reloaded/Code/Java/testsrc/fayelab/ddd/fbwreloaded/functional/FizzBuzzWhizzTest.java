package fayelab.ddd.fbwreloaded.functional;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.functional.FizzBuzzWhizz.*;

public class FizzBuzzWhizzTest extends TestCase
{
    public void test_times()
    {
        Predicate<Integer> times3 = times(3);
        assertTrue(times3.test(3));
        assertTrue(times3.test(6));
        assertFalse(times3.test(5));
        
        Predicate<Integer> times5 = times(5);
        assertTrue(times5.test(5));
        assertTrue(times5.test(10));
        assertFalse(times5.test(7));
        
        Predicate<Integer> times7 = times(7);
        assertTrue(times7.test(7));
        assertTrue(times7.test(14));
        assertFalse(times7.test(3));
    }
    
    public void test_contains()
    {
        Predicate<Integer> contains3 = contains(3);
        assertTrue(contains3.test(3));
        assertTrue(contains3.test(13));
        assertTrue(contains3.test(31));
        assertTrue(contains3.test(103));
        assertTrue(contains3.test(130));
        assertTrue(contains3.test(310));
        assertFalse(contains3.test(5));
    }
    
    public void test_alwaysTrue()
    {
        Predicate<Integer> alwaysTrue = alwaysTrue();
        assertTrue(alwaysTrue.test(1));
    }
    
    public void test_toFizz()
    {
        Function<Integer, String> toFizz = toFizz();
        assertEquals("Fizz", toFizz.apply(3));
    }
    
    public void test_toBuzz()
    {
        Function<Integer, String> toBuzz = toBuzz();
        assertEquals("Buzz", toBuzz.apply(5));
    }
    
    public void test_toWhizz()
    {
        Function<Integer, String> toWhizz = toWhizz();
        assertEquals("Whizz", toWhizz.apply(7));
    }
    
    public void test_toStr()
    {
        Function<Integer, String> toStr = toStr();
        assertEquals("6", toStr.apply(6));
    }
    
    public void test_atom()
    {
        Function<Integer, Optional<String>> r1_3 = atom(times(3), toFizz());
        assertEquals("Fizz", r1_3.apply(6).get());
        assertEquals(Optional.empty(), r1_3.apply(7));
        
        Function<Integer, Optional<String>> r1_5 = atom(times(5), toBuzz());
        assertEquals("Buzz", r1_5.apply(10).get());
        assertEquals(Optional.empty(), r1_5.apply(11));
        
        Function<Integer, Optional<String>> r1_7 = atom(times(7), toWhizz());
        assertEquals("Whizz", r1_7.apply(14).get());
        assertEquals(Optional.empty(), r1_7.apply(13));
        
        Function<Integer, Optional<String>> r3 = atom(contains(3), toFizz());
        assertEquals("Fizz", r3.apply(3).get());
        assertEquals("Fizz", r3.apply(13).get());
        assertEquals("Fizz", r3.apply(31).get());
        assertEquals(Optional.empty(), r3.apply(24));
        
        Function<Integer, Optional<String>> rd = atom(alwaysTrue(), toStr());
        assertEquals("6", rd.apply(6).get());
    }
    
    public void test_or()
    {
        Function<Integer, Optional<String>> r1_3 = atom(times(3), toFizz());
        Function<Integer, Optional<String>> r1_5 = atom(times(5), toBuzz());
        Function<Integer, Optional<String>> r1_7 = atom(times(7), toWhizz());
        
        Function<Integer, Optional<String>> rd = atom(alwaysTrue(), toStr());
        
        Function<Integer, Optional<String>> or_35 = or(r1_3, r1_5);
        assertEquals("Fizz", or_35.apply(6).get());
        assertEquals("Buzz", or_35.apply(10).get());
        assertEquals(Optional.empty(), or_35.apply(7));
        
        Function<Integer, Optional<String>> or_357 = or3(r1_3, r1_5, r1_7);
        assertEquals("Fizz", or_357.apply(6).get());
        assertEquals("Buzz", or_357.apply(10).get());
        assertEquals("Whizz", or_357.apply(14).get());
        assertEquals(Optional.empty(), or_357.apply(13));
        
        Function<Integer, Optional<String>> or_357d = or4(r1_3, r1_5, r1_7, rd);
        assertEquals("Fizz", or_357d.apply(6).get());
        assertEquals("Buzz", or_357d.apply(10).get());
        assertEquals("Whizz", or_357d.apply(14).get());
        assertEquals("13", or_357d.apply(13).get());
    }
    
    public void test_and()
    {
        Function<Integer, Optional<String>> r1_3 = atom(times(3), toFizz());
        Function<Integer, Optional<String>> r1_5 = atom(times(5), toBuzz());
        Function<Integer, Optional<String>> r1_7 = atom(times(7), toWhizz());
        
        Function<Integer, Optional<String>> and_35 = and(r1_3, r1_5);
        assertEquals("FizzBuzz", and_35.apply(15).get());
        assertEquals(Optional.empty(), and_35.apply(16));
        
        Function<Integer, Optional<String>> and_37 = and(r1_3, r1_7);
        assertEquals("FizzWhizz", and_37.apply(21).get());
        assertEquals(Optional.empty(), and_37.apply(22));
        
        Function<Integer, Optional<String>> and_57 = and(r1_5, r1_7);
        assertEquals("BuzzWhizz", and_57.apply(35).get());
        assertEquals(Optional.empty(), and_37.apply(36));
        
        Function<Integer, Optional<String>> and_357 = and3(r1_3, r1_5, r1_7);
        assertEquals("FizzBuzzWhizz", and_357.apply(105).get());
        assertEquals(Optional.empty(), and_357.apply(104));
    }
    
    public void test_spec()
    {
        Function<Integer, Optional<String>> spec = spec();
        assertEquals("Fizz", spec.apply(35).get());
        assertEquals("FizzWhizz", spec.apply(21).get());
        assertEquals("BuzzWhizz", spec.apply(70).get());
        assertEquals("Fizz", spec.apply(9).get());
        assertEquals("1", spec.apply(1).get());
    }
}
