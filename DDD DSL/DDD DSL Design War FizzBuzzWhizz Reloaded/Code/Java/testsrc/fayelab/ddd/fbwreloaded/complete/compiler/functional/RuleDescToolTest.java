package fayelab.ddd.fbwreloaded.complete.compiler.functional;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.complete.compiler.functional.RuleDescTool.*;
import static fayelab.ddd.fbwreloaded.complete.compiler.functional.SpecTool.*;

public class RuleDescToolTest extends TestCase
{
    public void test_desc_predication()
    {
        assertEquals("times_3", descPredication(times(3)));
        assertEquals("contains_3", descPredication(contains(3)));
        assertEquals("always_true", descPredication(alwaysTrue()));
    }
    
    public void test_desc_action()
    {
        assertEquals("to_fizz", descAction(toFizz()));
        assertEquals("to_buzz", descAction(toBuzz()));
        assertEquals("to_whizz", descAction(toWhizz()));
        assertEquals("to_str", descAction(toStr()));
    }
    
    public void test_desc_atom()
    {
        assertEquals("{atom, times_3, to_fizz}", descAtom(new Atom(times(3), toFizz())));
        assertEquals("{atom, times_5, to_buzz}", descAtom(new Atom(times(5), toBuzz())));
        assertEquals("{atom, times_7, to_whizz}", descAtom(new Atom(times(7), toWhizz())));
        assertEquals("{atom, contains_3, to_fizz}", descAtom(new Atom(contains(3), toFizz())));
        assertEquals("{atom, always_true, to_str}", descAtom(new Atom(alwaysTrue(), toStr())));
    }
    
    public void test_desc_or()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());

        Or or_57 = new Or(r1_5, r1_7);
        assertEquals("{or, {atom, times_5, to_buzz}, {atom, times_7, to_whizz}}", descOr(or_57));
                
        Or or_357 = new Or(r1_3, or(r1_5, r1_7));
        assertEquals("{or, {atom, times_3, to_fizz}, "
                        + "{or, {atom, times_5, to_buzz}, "
                             + "{atom, times_7, to_whizz}}}", 
                     descOr(or_357));
    }
    
    public void test_desc_and()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());

        And and_57 = new And(r1_5, r1_7);
        assertEquals("{and, {atom, times_5, to_buzz}, {atom, times_7, to_whizz}}", descAnd(and_57));
                
        And and_357 = new And(r1_3, and(r1_5, r1_7));
        assertEquals("{and, {atom, times_3, to_fizz}, "
                         + "{and, {atom, times_5, to_buzz}, "
                             + "{atom, times_7, to_whizz}}}", 
                     descAnd(and_357));
    }
    
    public void test_desc_spec()
    {
        Rule spec = spec();
        assertEquals("{or, {atom, contains_3, to_fizz}, "
                        + "{or, {or, {and, {atom, times_3, to_fizz}, "
                                        + "{and, {atom, times_5, to_buzz}, "
                                              + "{atom, times_7, to_whizz}}}, "
                                  + "{or, {and, {atom, times_3, to_fizz}, "
                                             + "{atom, times_5, to_buzz}}, "
                                       + "{or, {and, {atom, times_3, to_fizz}, "
                                                  + "{atom, times_7, to_whizz}}, "
                                            + "{and, {atom, times_5, to_buzz}, "
                                                  + "{atom, times_7, to_whizz}}}}}, "
                             + "{or, {or, {atom, times_3, to_fizz}, "
                                       + "{or, {atom, times_5, to_buzz}, "
                                            + "{atom, times_7, to_whizz}}}, "
                                  + "{atom, always_true, to_str}}}}", 
                     desc(spec));
    }
}
