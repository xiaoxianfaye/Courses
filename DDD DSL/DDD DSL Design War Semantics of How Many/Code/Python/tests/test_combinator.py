import unittest

from combinator import *

class TestCombinator(unittest.TestCase):
    def test_combinate(self):
        s = [1, 2, 3, 4]
        self.assertEquals([[1], [2], [3], [4]], combinate(s, 1))
        self.assertEquals([[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]], combinate(s, 2))
        self.assertEquals([[1, 2, 3], [1, 2, 4], [1, 3, 4], [2, 3, 4]], combinate(s, 3))
        self.assertEquals([[1, 2, 3, 4]], combinate(s, 4))

        self.assertEquals([['a', 'b', 'c'], ['a', 'b', 'd'], ['a', 'c', 'd'], ['b', 'c', 'd']],
                          combinate(['a', 'b', 'c', 'd'], 3))

    def test_combinate2(self):
        s = [1, 2, 3, 4]
        self.assertEquals([[1], [2], [3], [4]], combinate2(s, 1))
        self.assertEquals([[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]], combinate2(s, 2))
        self.assertEquals([[1, 2, 3], [1, 2, 4], [1, 3, 4], [2, 3, 4]], combinate2(s, 3))
        self.assertEquals([[1, 2, 3, 4]], combinate2(s, 4))

        self.assertEquals([['a', 'b', 'c'], ['a', 'b', 'd'], ['a', 'c', 'd'], ['b', 'c', 'd']],
                          combinate2(['a', 'b', 'c', 'd'], 3))