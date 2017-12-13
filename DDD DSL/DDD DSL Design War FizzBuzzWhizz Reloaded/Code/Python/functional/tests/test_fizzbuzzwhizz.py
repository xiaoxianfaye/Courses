import unittest

from fizzbuzzwhizz import *

class TestFizzBuzzWhizz(unittest.TestCase):
    def test_times(self):
        times3 = times(3)
        self.assertTrue(times3(3))
        self.assertTrue(times3(6))
        self.assertFalse(times3(5))

        times5 = times(5)
        self.assertTrue(times5(5))
        self.assertTrue(times5(10))
        self.assertFalse(times5(7))
        
        times7 = times(7)
        self.assertTrue(times7(7))
        self.assertTrue(times7(14))
        self.assertFalse(times7(3))

    def test_contains(self):
        contains3 = contains(3)
        self.assertTrue(contains3(13))
        self.assertTrue(contains3(35))
        self.assertTrue(contains3(300))
        self.assertFalse(contains3(24))

    def test_alwaystrue(self):
        at = alwaystrue()
        self.assertTrue(at(1))
        self.assertTrue(at(3))
        self.assertTrue(at(5))

    def test_tofizz(self):
        tf = tofizz()
        self.assertEquals('Fizz', tf(3))

    def test_tobuzz(self):
        tb = tobuzz()
        self.assertEquals('Buzz', tb(5))

    def test_towhizz(self):
        tw = towhizz()
        self.assertEquals('Whizz', tw(7))

    def test_tostr(self):
        ts = tostr()
        self.assertEquals('6', ts(6))        

    def test_atom(self):
        r1_3 = atom(times(3), tofizz())
        self.assertEquals((True, 'Fizz'), r1_3(3))
        self.assertEquals((False, ''), r1_3(4))

        r1_5 = atom(times(5), tobuzz())
        self.assertEquals((True, 'Buzz'), r1_5(10))
        self.assertEquals((False, ''), r1_5(11))
    
        r1_7 = atom(times(7), towhizz())
        self.assertEquals((True, 'Whizz'), r1_7(14))
        self.assertEquals((False, ''), r1_7(13))

        r3 = atom(contains(3), tofizz())
        self.assertEquals((True, 'Fizz'), r3(3))
        self.assertEquals((True, 'Fizz'), r3(13))
        self.assertEquals((True, 'Fizz'), r3(31))
        self.assertEquals((False, ''), r3(24))

        rd = atom(alwaystrue(), tostr())
        self.assertEquals((True, '1'), rd(1))
        self.assertEquals((True, '3'), rd(3))

    def test_OR(self):
        r1_3 = atom(times(3), tofizz())
        r1_5 = atom(times(5), tobuzz())
        r1_7 = atom(times(7), towhizz())

        rd = atom(alwaystrue(), tostr())

        or_35 = OR(r1_3, r1_5)
        self.assertEquals((True, 'Fizz'), or_35(6))
        self.assertEquals((True, 'Buzz'), or_35(10))
        self.assertEquals((True, 'Fizz'), or_35(15))
        self.assertEquals((False, ''), or_35(7))

        or_357 = OR3(r1_3, r1_5, r1_7)
        self.assertEquals((True, 'Fizz'), or_357(6))
        self.assertEquals((True, 'Buzz'), or_357(10))
        self.assertEquals((True, 'Whizz'), or_357(14))
        self.assertEquals((False, ''), or_357(13))

        or_357d = OR4(r1_3, r1_5, r1_7, rd)
        self.assertEquals((True, 'Fizz'), or_357d(6))
        self.assertEquals((True, 'Buzz'), or_357d(10))
        self.assertEquals((True, 'Whizz'), or_357d(14))
        self.assertEquals((True, '13'), or_357d(13))

    def test_AND(self):
        r1_3 = atom(times(3), tofizz())
        r1_5 = atom(times(5), tobuzz())
        r1_7 = atom(times(7), towhizz())

        and_35 = AND(r1_3, r1_5)
        self.assertEquals((False, ''), and_35(3))
        self.assertEquals((False, ''), and_35(5))
        self.assertEquals((True, 'FizzBuzz'), and_35(15))
        self.assertEquals((False, ''), and_35(16))

        and_37 = AND(r1_3, r1_7)
        self.assertEquals((True, 'FizzWhizz'), and_37(21))
        self.assertEquals((False, ''), and_37(2))

        and_57 = AND(r1_5, r1_7)
        self.assertEquals((True, 'BuzzWhizz'), and_57(35))
        self.assertEquals((False, ''), and_57(36))

        and_357 = AND3(r1_3, r1_5, r1_7)
        self.assertEquals((True, 'FizzBuzzWhizz'), and_357(3*5*7))
        self.assertEquals((False, ''), and_357(104))
    
    def test_spec(self):
        s = spec()
        self.assertEquals((True, 'Fizz'), s(35))
        self.assertEquals((True, 'FizzWhizz'), s(21))
        self.assertEquals((True, 'BuzzWhizz'), s(70))
        self.assertEquals((True, 'Fizz'), s(9))
        self.assertEquals((True, '1'), s(1))
        