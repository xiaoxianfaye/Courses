import unittest

from rule import *
from ruledescriber import *

class TestRuleDescriber(unittest.TestCase):
    def test_desc_predication(self):
        self.assertEquals("times_3", desc_predication(times(3)))
        self.assertEquals("contains_3", desc_predication(contains(3)))
        self.assertEquals("always_true", desc_predication(alwaystrue()))

    def test_desc_action(self):
        self.assertEquals("to_fizz", desc_action(tofizz()))
        self.assertEquals("to_buzz", desc_action(tobuzz()))
        self.assertEquals("to_whizz", desc_action(towhizz()))
        self.assertEquals("to_str", desc_action(tostr()))

    def test_desc_atom(self):
        self.assertEquals("{atom, times_3, to_fizz}", desc_atom(atom(times(3), tofizz())))
        self.assertEquals("{atom, times_5, to_buzz}", desc_atom(atom(times(5), tobuzz())))
        self.assertEquals("{atom, times_7, to_whizz}", desc_atom(atom(times(7), towhizz())))
        self.assertEquals("{atom, contains_3, to_fizz}", desc_atom(atom(contains(3), tofizz())))
        self.assertEquals("{atom, always_true, to_str}", desc_atom(atom(alwaystrue(), tostr())))

    def test_desc_or(self):
        r1_3 = atom(times(3), tofizz())
        r1_5 = atom(times(5), tobuzz())
        r1_7 = atom(times(7), towhizz())

        or_57 = OR(r1_5, r1_7)
        self.assertEquals("{or, {atom, times_5, to_buzz}, {atom, times_7, to_whizz}}", 
                          desc_or(or_57))
                
        or_357 = ORN(r1_3, r1_5, r1_7)
        self.assertEquals("{or, {atom, times_3, to_fizz}, " \
                               "{or, {atom, times_5, to_buzz}, " \
                                    "{atom, times_7, to_whizz}}}",
                          desc_or(or_357))

    def test_desc_and(self):
        r1_3 = atom(times(3), tofizz())
        r1_5 = atom(times(5), tobuzz())
        r1_7 = atom(times(7), towhizz())

        and_57 = AND(r1_5, r1_7)
        self.assertEquals("{and, {atom, times_5, to_buzz}, {atom, times_7, to_whizz}}", 
                          desc_and(and_57))
                
        and_357 = ANDN(r1_3, r1_5, r1_7)
        self.assertEquals("{and, {atom, times_3, to_fizz}, " \
                                "{and, {atom, times_5, to_buzz}, " \
                                      "{atom, times_7, to_whizz}}}", 
                          desc_and(and_357))

    def test_desc_spec(self):
        self.assertEquals("{or, {atom, contains_3, to_fizz}, " \
                               "{or, {or, {and, {atom, times_3, to_fizz}, " \
                                               "{and, {atom, times_5, to_buzz}, " \
                                                     "{atom, times_7, to_whizz}}}, " \
                                         "{or, {and, {atom, times_3, to_fizz}, " \
                                                    "{atom, times_5, to_buzz}}, " \
                                              "{or, {and, {atom, times_3, to_fizz}, " \
                                                         "{atom, times_7, to_whizz}}, " \
                                                   "{and, {atom, times_5, to_buzz}, " \
                                                         "{atom, times_7, to_whizz}}}}}, " \
                                    "{or, {or, {atom, times_3, to_fizz}, " \
                                              "{or, {atom, times_5, to_buzz}, " \
                                                   "{atom, times_7, to_whizz}}}, " \
                                         "{atom, always_true, to_str}}}}", 
                          desc(spec()))

