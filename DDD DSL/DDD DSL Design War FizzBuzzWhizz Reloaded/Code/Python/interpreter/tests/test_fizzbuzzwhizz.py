import unittest

from fizzbuzzwhizz import *

class TestFizzBuzzWhizz(unittest.TestCase):
    def test_atom(self):
        r1_3 = atom(times(3), tofizz())
        self.assertEquals((True, 'Fizz'), apply_rule(r1_3, 6))
        self.assertEquals((False, ''), apply_rule(r1_3, 7))

        r1_5 = atom(times(5), tobuzz())
        self.assertEquals((True, 'Buzz'), apply_rule(r1_5, 10))
        self.assertEquals((False, ''), apply_rule(r1_5, 11))

        r1_7 = atom(times(7), towhizz())
        self.assertEquals((True, 'Whizz'), apply_rule(r1_7, 14))
        self.assertEquals((False, ''), apply_rule(r1_7, 13))
        
        r3 = atom(contains(3), tofizz())
        self.assertEquals((True, 'Fizz'), apply_rule(r3, 3))
        self.assertEquals((True, 'Fizz'), apply_rule(r3, 13))
        self.assertEquals((True, 'Fizz'), apply_rule(r3, 31))
        self.assertEquals((False, ''), apply_rule(r3, 24))
        
        rd = atom(alwaystrue(), tostr())
        self.assertEquals((True, '1'), apply_rule(rd, 1))
        self.assertEquals((True, '3'), apply_rule(rd, 3))

    def test_OR(self):
        r1_3 = atom(times(3), tofizz())
        r1_5 = atom(times(5), tobuzz())
        r1_7 = atom(times(7), towhizz())

        rd = atom(alwaystrue(), tostr())

        or_35 = OR(r1_3, r1_5)
        self.assertEquals((True, 'Fizz'), apply_rule(or_35, 6))
        self.assertEquals((True, 'Buzz'), apply_rule(or_35, 10))
        self.assertEquals((True, 'Fizz'), apply_rule(or_35, 15))
        self.assertEquals((False, ''), apply_rule(or_35, 7))

        or_357 = OR3(r1_3, r1_5, r1_7)
        self.assertEquals((True, 'Fizz'), apply_rule(or_357, 6))
        self.assertEquals((True, 'Buzz'), apply_rule(or_357, 10))
        self.assertEquals((True, 'Whizz'), apply_rule(or_357, 14))
        self.assertEquals((False, ''), apply_rule(or_357, 13))

        or_357d = OR4(r1_3, r1_5, r1_7, rd)
        self.assertEquals((True, 'Fizz'), apply_rule(or_357d, 6))
        self.assertEquals((True, 'Buzz'), apply_rule(or_357d, 10))
        self.assertEquals((True, 'Whizz'), apply_rule(or_357d, 14))
        self.assertEquals((True, '13'), apply_rule(or_357d, 13))

    def test_AND(self):
        r1_3 = atom(times(3), tofizz())
        r1_5 = atom(times(5), tobuzz())
        r1_7 = atom(times(7), towhizz())

        and_35 = AND(r1_3, r1_5)
        self.assertEquals((False, ''), apply_rule(and_35, 3))
        self.assertEquals((False, ''), apply_rule(and_35, 5))
        self.assertEquals((True, 'FizzBuzz'), apply_rule(and_35, 15))
        self.assertEquals((False, ''), apply_rule(and_35, 16))

        and_37 = AND(r1_3, r1_7)
        self.assertEquals((True, 'FizzWhizz'), apply_rule(and_37, 21))
        self.assertEquals((False, ''), apply_rule(and_37, 2))

        and_57 = AND(r1_5, r1_7)
        self.assertEquals((True, 'BuzzWhizz'), apply_rule(and_57, 35))
        self.assertEquals((False, ''), apply_rule(and_57, 36))

        and_357 = AND3(r1_3, r1_5, r1_7)
        self.assertEquals((True, 'FizzBuzzWhizz'), apply_rule(and_357, 3*5*7))
        self.assertEquals((False, ''), apply_rule(and_357, 104))
    
    # def test_spec(self):
    #     s = spec()
    #     self.assertEquals((True, 'Fizz'), s(35))
    #     self.assertEquals((True, 'FizzWhizz'), s(21))
    #     self.assertEquals((True, 'BuzzWhizz'), s(70))
    #     self.assertEquals((True, 'Fizz'), s(9))
    #     self.assertEquals((True, '1'), s(1))
        