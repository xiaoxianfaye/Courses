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

    