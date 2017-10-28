package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule;

import java.util.Optional;

import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action.*;
import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication.*;

import junit.framework.TestCase;

public class OORuleTest extends TestCase
{
    public void test_atom_rule_1_3()
    {
        OORule r1_3 = new OOAtom(new OOTimes(3), new OOToFizz());
        assertEquals(Optional.of("Fizz"), r1_3.apply(3));
        assertEquals(Optional.empty(), r1_3.apply(4));
    }
    
    public void test_atom_rule_1_5()
    {
        OORule r1_5 = new OOAtom(new OOTimes(5), new OOToBuzz());
        assertEquals(Optional.of("Buzz"), r1_5.apply(10));
        assertEquals(Optional.empty(), r1_5.apply(11));
    }
    
    public void test_atom_rule_1_7()
    {
        OORule r1_7 = new OOAtom(new OOTimes(7), new OOToWhizz());
        assertEquals(Optional.of("Whizz"), r1_7.apply(14));
        assertEquals(Optional.empty(), r1_7.apply(13));
    }
    
    public void test_or_rule()
    {
        OORule r1_3 = new OOAtom(new OOTimes(3), new OOToFizz());
        OORule r1_5 = new OOAtom(new OOTimes(5), new OOToBuzz());
        
        OORule or_35 = new OOOr(r1_3, r1_5);
        assertEquals(Optional.of("Fizz"), or_35.apply(6));
        assertEquals(Optional.of("Buzz"), or_35.apply(10));
        assertEquals(Optional.empty(), or_35.apply(7));
    }

    public void test_rule_1()
    {
        OORule r1_3 = new OOAtom(new OOTimes(3), new OOToFizz());
        OORule r1_5 = new OOAtom(new OOTimes(5), new OOToBuzz());
        OORule r1_7 = new OOAtom(new OOTimes(7), new OOToWhizz());
        
        OORule r1 = new OOOr(r1_3, new OOOr(r1_5, r1_7));
        assertEquals(Optional.of("Fizz"), r1.apply(6));
        assertEquals(Optional.of("Buzz"), r1.apply(10));
        assertEquals(Optional.of("Whizz"), r1.apply(14));
        assertEquals(Optional.empty(), r1.apply(13));
    }
    
    public void test_and_rule()
    {
        OORule r1_3 = new OOAtom(new OOTimes(3), new OOToFizz());
        OORule r1_5 = new OOAtom(new OOTimes(5), new OOToBuzz());
        
        OORule and_35 = new OOAnd(r1_3, r1_5);
        assertEquals(Optional.of("FizzBuzz"), and_35.apply(15));
        assertEquals(Optional.empty(), and_35.apply(16));
    }
    
    public void test_rule_2()
    {
        OORule r1_3 = new OOAtom(new OOTimes(3), new OOToFizz());
        OORule r1_5 = new OOAtom(new OOTimes(5), new OOToBuzz());
        OORule r1_7 = new OOAtom(new OOTimes(7), new OOToWhizz());
        
        OORule r2 = new OOOr(new OOAnd(r1_3, new OOAnd(r1_5, r1_7)),
                             new OOOr(new OOAnd(r1_3, r1_5),
                                      new OOOr(new OOAnd(r1_3, r1_7),
                                               new OOAnd(r1_5, r1_7))));
        assertEquals(Optional.of("FizzBuzzWhizz"), r2.apply(105));
        assertEquals(Optional.empty(), r2.apply(104));
        assertEquals(Optional.of("FizzBuzz"), r2.apply(15));
        assertEquals(Optional.empty(),  r2.apply(14));
        assertEquals(Optional.of("FizzWhizz"), r2.apply(21));
        assertEquals(Optional.empty(), r2.apply(22));
        assertEquals(Optional.of("BuzzWhizz"), r2.apply(35));
        assertEquals(Optional.empty(), r2.apply(34));
    }
    
    public void test_rule_3()
    {
        OORule r3 = new OOAtom(new OOContains(3), new OOToFizz());
        assertEquals(Optional.of("Fizz"), r3.apply(3));
        assertEquals(Optional.of("Fizz"), r3.apply(13));
        assertEquals(Optional.of("Fizz"), r3.apply(31));
        assertEquals(Optional.empty(), r3.apply(24));
    }
    
    public void test_default_rule()
    {
        OORule rd = new OOAtom(new OOAlwaysTrue(), new OOToStr());
        assertEquals(Optional.of("4"), rd.apply(4));
    }
}
