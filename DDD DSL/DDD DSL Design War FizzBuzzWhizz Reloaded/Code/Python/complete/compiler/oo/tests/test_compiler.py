import unittest

from compiler import *
from rule import *

class TestCompiler(unittest.TestCase):
    def setUp(self):
        self.r1_3 = atom(times(3), tofizz())
        self.r1_5 = atom(times(5), tobuzz())
        self.r1_7 = atom(times(7), towhizz())
        self.r3 = atom(contains(3), tofizz())
        self.rd = atom(alwaystrue(), tostr())

    def test_compile_predication(self):
        self.assertTrue(compile_predication(times(3)).predicate(3))
        self.assertFalse(compile_predication(times(3)).predicate(4))
        self.assertTrue(compile_predication(contains(3)).predicate(3))
        self.assertFalse(compile_predication(contains(3)).predicate(4))
        self.assertTrue(compile_predication(alwaystrue()).predicate(-1))

    def test_compile_action(self):
        self.assertEquals('Fizz', compile_action(tofizz()).act(3))
        self.assertEquals('Buzz', compile_action(tobuzz()).act(5))
        self.assertEquals('Whizz', compile_action(towhizz()).act(7))
        self.assertEquals('1', compile_action(tostr()).act(1))

    def test_compile_atom(self):
        c_r1_3 = compile_atom(self.r1_3)
        self.assertEquals((True, 'Fizz'), c_r1_3.apply(6))
        self.assertEquals((False, ''), c_r1_3.apply(7))
        
        c_r1_5 = compile_atom(self.r1_5)
        self.assertEquals((True, 'Buzz'), c_r1_5.apply(10))
        self.assertEquals((False, ''), c_r1_5.apply(11))
        
        c_r1_7 = compile_atom(self.r1_7)
        self.assertEquals((True, 'Whizz'), c_r1_7.apply(14))
        self.assertEquals((False, ''), c_r1_7.apply(13))
        
        c_r3 = compile_atom(self.r3)
        self.assertEquals((True, 'Fizz'), c_r3.apply(3))
        self.assertEquals((True, 'Fizz'), c_r3.apply(13))
        self.assertEquals((True, 'Fizz'), c_r3.apply(31))
        self.assertEquals((False, ''), c_r3.apply(24))
        
        c_rd = compile_atom(self.rd)
        self.assertEquals((True, '6'), c_rd.apply(6))

    def test_compile_or(self):
        or_35 = OR(self.r1_3, self.r1_5)
        c_or_35 = compile_or(or_35)
        self.assertEquals((True, 'Fizz'), c_or_35.apply(6))
        self.assertEquals((True, 'Buzz'), c_or_35.apply(10))
        self.assertEquals((True, 'Fizz'), c_or_35.apply(15))
        self.assertEquals((False, ''), c_or_35.apply(7))
        
        or_357 = ORN(self.r1_3, self.r1_5, self.r1_7)
        c_or_357 = compile_or(or_357)
        self.assertEquals((True, 'Fizz'), c_or_357.apply(6))
        self.assertEquals((True, 'Buzz'), c_or_357.apply(10))
        self.assertEquals((True, 'Whizz'), c_or_357.apply(14))
        self.assertEquals((False, ''), c_or_357.apply(13))

    def test_compile_and(self):
        and_35 = AND(self.r1_3, self.r1_5)
        c_and_35 = compile_and(and_35)
        self.assertEquals((False, ''), c_and_35.apply(3))
        self.assertEquals((False, ''), c_and_35.apply(5))
        self.assertEquals((True, 'FizzBuzz'), c_and_35.apply(15))
        self.assertEquals((False, ''), c_and_35.apply(16))
        
        and_357 = ANDN(self.r1_3, self.r1_5, self.r1_7)
        c_and_357 = compile_and(and_357)
        self.assertEquals((True, 'FizzBuzzWhizz'), c_and_357.apply(3*5*7))
        self.assertEquals((False, ''), c_and_357.apply(104))

    def test_compile_spec(self):
        c_spec = compile(spec());
        self.assertEquals((True, 'Fizz'), c_spec.apply(35))
        self.assertEquals((True, 'FizzWhizz'), c_spec.apply(21))
        self.assertEquals((True, 'BuzzWhizz'), c_spec.apply(70))
        self.assertEquals((True, 'Fizz'), c_spec.apply(9))
        self.assertEquals((True, '1'), c_spec.apply(1))
