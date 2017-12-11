import unittest

from predication import Times, Contains, AlwaysTrue

class TestTimes(unittest.TestCase):
    def test_times(self):
        times3 = Times(3)
        self.assertTrue(times3.predicate(6))
        self.assertFalse(times3.predicate(5))

class TestContains(unittest.TestCase):
    def test_contains(self):
        contains3 = Contains(3)
        self.assertTrue(contains3.predicate(13))
        self.assertTrue(contains3.predicate(35))
        self.assertTrue(contains3.predicate(300))
        self.assertFalse(contains3.predicate(24))

class TestAlwaysTrue(unittest.TestCase):
    def test_alwaysTrue(self):
        alwaysTrue = AlwaysTrue()
        self.assertTrue(alwaysTrue.predicate(1))
        self.assertTrue(alwaysTrue.predicate(3))
        self.assertTrue(alwaysTrue.predicate(5))

