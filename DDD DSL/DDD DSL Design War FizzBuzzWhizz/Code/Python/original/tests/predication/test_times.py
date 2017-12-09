import unittest

from predication.times import Times

class TestTimes(unittest.TestCase):
    def test_times_3(self):
        times3 = Times(3)
        self.assertTrue(times3.predicate(6))
        self.assertFalse(times3.predicate(5))

