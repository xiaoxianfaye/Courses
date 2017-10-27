package fayelab.ddd.fbwreloaded.complete.interpreter;

import junit.framework.TestCase;

import static fayelab.ddd.fbwreloaded.complete.interpreter.RuleEqualityTool.*;
import static fayelab.ddd.fbwreloaded.complete.interpreter.SpecTool.*;

public class RuleEqualityToolTest extends TestCase
{
    public void test_equal_predication()
    {
        assertTrue(equalPredication(times(3), times(3)));
        assertFalse(equalPredication(times(3), contains(3)));
        assertFalse(equalPredication(times(3), times(5)));
    }
    
    public void test_equal_action()
    {
        assertTrue(equalAction(toFizz(), toFizz()));
        assertFalse(equalAction(toFizz(), toBuzz()));
    }
    
    public void test_equal_atom()
    {        
        assertTrue(equalAtom(new Atom(times(3), toFizz()), new Atom(times(3), toFizz())));
        assertFalse(equalAtom(new Atom(times(3), toFizz()), new Atom(times(5), toFizz())));
        assertFalse(equalAtom(new Atom(times(3), toFizz()), new Atom(times(3), toBuzz())));
    }
    
    public void test_equal_or()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());

        Or or_35_1 = new Or(r1_3, r1_5);
        Or or_35_2 = new Or(r1_3, r1_5);
        Or or_37 = new Or(r1_3, r1_7);
        
        Or or_357_1 = new Or(r1_3, or(r1_5, r1_7));
        Or or_357_2 = new Or(or(r1_3, r1_5), r1_7);
        
        assertTrue(equalOr(or_35_1, or_35_2));
        assertFalse(equalOr(or_35_1, or_37));
        assertFalse(equalOr(or_357_1, or_357_2));
    }
    
    public void test_equal_and()
    {
        Rule r1_3 = atom(times(3), toFizz());
        Rule r1_5 = atom(times(5), toBuzz());
        Rule r1_7 = atom(times(7), toWhizz());

        And and_35_1 = new And(r1_3, r1_5);
        And and_35_2 = new And(r1_3, r1_5);
        And and_37 = new And(r1_3, r1_7);
        
        And and_357_1 = new And(r1_3, and(r1_5, r1_7));
        And and_357_2 = new And(and(r1_3, r1_5), r1_7);
        
        assertTrue(equalAnd(and_35_1, and_35_2));
        assertFalse(equalAnd(and_35_1, and_37));
        assertFalse(equalAnd(and_357_1, and_357_2));
    }
    
    public void test_equal()
    {
        Rule spec1 = spec();
        Rule spec2 = spec();
        
        assertTrue(equal(spec1, spec1));
        assertTrue(equal(null, null));
        assertFalse(equal(spec1, null));
        assertFalse(equal(null, spec1));

        assertTrue(equal(spec1, spec2));
    }
}
