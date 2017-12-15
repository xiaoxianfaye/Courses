import unittest

from compiled.rule import *
from compiled.predication import *
from compiled.action import *

class TestAtom(unittest.TestCase):
    def test_atom_rule_1_3(self):
        r1_3 = OOAtom(OOTimes(3), OOToFizz())
        self.assertEquals((True, 'Fizz'), r1_3.apply(3))
        self.assertEquals((False, ''), r1_3.apply(4))

    def test_atom_rule_1_5(self):
        r1_5 = OOAtom(OOTimes(5), OOToBuzz())
        self.assertEquals((True, 'Buzz'), r1_5.apply(10))
        self.assertEquals((False, ''), r1_5.apply(11))
    
    def test_atom_rule_1_7(self):
        r1_7 = OOAtom(OOTimes(7), OOToWhizz())
        self.assertEquals((True, 'Whizz'), r1_7.apply(14))
        self.assertEquals((False, ''), r1_7.apply(13))

class TestOR(unittest.TestCase):
    def test_or_rule(self):
        r1_3 = OOAtom(OOTimes(3), OOToFizz())
        r1_5 = OOAtom(OOTimes(5), OOToBuzz())
        
        or_35 = OOOR(r1_3, r1_5)
        self.assertEquals((True, 'Fizz'), or_35.apply(6))
        self.assertEquals((True, 'Buzz'), or_35.apply(10))
        self.assertEquals((True, 'Fizz'), or_35.apply(15))
        self.assertEquals((False, ''), or_35.apply(7))

class TestAND(unittest.TestCase):
    def test_and_rule(self):
        r1_3 = OOAtom(OOTimes(3), OOToFizz())
        r1_5 = OOAtom(OOTimes(5), OOToBuzz())

        and_35 = OOAND(r1_3, r1_5)
        self.assertEquals((False, ''), and_35.apply(3))
        self.assertEquals((False, ''), and_35.apply(5))
        self.assertEquals((True, 'FizzBuzz'), and_35.apply(15))
        self.assertEquals((False, ''), and_35.apply(16))

class TestRule(unittest.TestCase):
    def test_rule_1(self):
        r1_3 = OOAtom(OOTimes(3), OOToFizz())
        r1_5 = OOAtom(OOTimes(5), OOToBuzz())
        r1_7 = OOAtom(OOTimes(7), OOToWhizz())
        
        r1 = OOOR3(r1_3, r1_5, r1_7)
        self.assertEquals((True, 'Fizz'), r1.apply(6))
        self.assertEquals((True, 'Buzz'), r1.apply(10))
        self.assertEquals((True, 'Whizz'), r1.apply(14))
        self.assertEquals((False, ''), r1.apply(13))

    def test_rule_2(self):
        r1_3 = OOAtom(OOTimes(3), OOToFizz())
        r1_5 = OOAtom(OOTimes(5), OOToBuzz())
        r1_7 = OOAtom(OOTimes(7), OOToWhizz())
        
        r2 = OOOR4(OOAND3(r1_3, r1_5, r1_7),
                   OOAND(r1_3, r1_5),
                   OOAND(r1_3, r1_7),
                   OOAND(r1_5, r1_7))
        self.assertEquals((False, ''), r2.apply(3))
        self.assertEquals((False, ''), r2.apply(5))
        self.assertEquals((False, ''), r2.apply(7))
        self.assertEquals((True, 'FizzBuzzWhizz'), r2.apply(3*5*7))
        self.assertEquals((False, ''), r2.apply(104))
        self.assertEquals((True, 'FizzBuzz'), r2.apply(15))
        self.assertEquals((False, ''), r2.apply(14))
        self.assertEquals((True, 'FizzWhizz'), r2.apply(21))
        self.assertEquals((False, ''), r2.apply(22))
        self.assertEquals((True, 'BuzzWhizz'), r2.apply(35))
        self.assertEquals((False, ''), r2.apply(34))

    def test_rule_3(self):
        r3 = OOAtom(OOContains(3), OOToFizz())
        self.assertEquals((True, 'Fizz'), r3.apply(3))
        self.assertEquals((True, 'Fizz'), r3.apply(13))
        self.assertEquals((True, 'Fizz'), r3.apply(31))
        self.assertEquals((False, ''), r3.apply(24))

    def test_default_rule(self):
        rd = OOAtom(OOAlwaysTrue(), OOToStr())
        self.assertEquals((True, '1'), rd.apply(1))
        self.assertEquals((True, '3'), rd.apply(3))

    def test_spec(self):
        s = spec()
        self.assertEquals((True, 'Fizz'), s.apply(35))
        self.assertEquals((True, 'FizzBuzz'), s.apply(15))
        self.assertEquals((True, 'FizzWhizz'), s.apply(21))
        self.assertEquals((True, 'BuzzWhizz'), s.apply(70))
        self.assertEquals((True, 'Fizz'), s.apply(9))
        self.assertEquals((True, '1'), s.apply(1))
