import unittest

from compiled.predication import *

class TestTimes(unittest.TestCase):
    def test_times(self):
        times3 = OOTimes(3)
        self.assertTrue(times3.predicate(6))
        self.assertFalse(times3.predicate(5))

class TestContains(unittest.TestCase):
    def test_contains(self):
        contains3 = OOContains(3)
        self.assertTrue(contains3.predicate(13))
        self.assertTrue(contains3.predicate(35))
        self.assertTrue(contains3.predicate(300))
        self.assertFalse(contains3.predicate(24))

class TestAlwaysTrue(unittest.TestCase):
    def test_alwaysTrue(self):
        alwaysTrue = OOAlwaysTrue()
        self.assertTrue(alwaysTrue.predicate(1))
        self.assertTrue(alwaysTrue.predicate(3))
        self.assertTrue(alwaysTrue.predicate(5))

