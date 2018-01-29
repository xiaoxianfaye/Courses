import unittest

from setop import *

class TestSetop(unittest.TestCase):
    def test_subset(self):
        s1 = [1, 2]
        self.assertTrue(subset([], s1))
        self.assertTrue(subset([1], s1))
        self.assertTrue(subset([2], s1))
        self.assertTrue(subset([1, 2], s1))

        s2 = ['a', 'b', 'c']
        self.assertTrue(subset([], s2))
        self.assertTrue(subset(['a'], s2))
        self.assertTrue(subset(['b'], s2))
        self.assertTrue(subset(['c'], s2))
        self.assertTrue(subset(['a', 'b'], s2))
        self.assertTrue(subset(['a', 'c'], s2))
        self.assertTrue(subset(['b', 'c'], s2))
        self.assertTrue(subset(['a', 'b', 'c'], s2))

    def test_belong(self):
        sos1 = [[1, 2], [2, 3], [3, 4]]
        self.assertTrue(belong([2, 3], sos1))
        self.assertFalse(belong([1, 3], sos1))

        sos2 = [['a', 'c', 'b'], ['c', 'd']]
        self.assertTrue(belong(['a', 'c', 'b'], sos2))
        self.assertTrue(belong(['a', 'c'], sos2))
        self.assertTrue(belong(['c', 'b'], sos2))
        self.assertFalse(belong(['a', 'd'], sos2))
        self.assertFalse(belong(['b', 'd'], sos2))