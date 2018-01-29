import unittest

from trianglecounter import *

class TestTrianglecounter(unittest.TestCase):
    def test_connected(self):
        lines = [['a', 'c', 'b'], ['c', 'd']]
        self.assertTrue(connected('a', 'c', lines))
        self.assertTrue(connected('c', 'b', lines))
        self.assertTrue(connected('c', 'd', lines))
        self.assertFalse(connected('a', 'd', lines))
        self.assertFalse(connected('b', 'd', lines))

    def test_on_a_line(self):
        lines = [['a', 'c', 'b'], ['c', 'd']]
        self.assertTrue(on_a_line('a', 'c', 'b', lines))
        self.assertFalse(on_a_line('a', 'c', 'd', lines))
        self.assertFalse(on_a_line('b', 'c', 'd', lines))

    def test_triangle(self):
        lines = [['a', 'c', 'b'], ['c', 'd'], ['a', 'd'], ['b', 'd']]
        self.assertTrue(triangle('a', 'c', 'd', lines))
        self.assertTrue(triangle('b', 'c', 'd', lines))
        self.assertTrue(triangle('a', 'b', 'd', lines))
        self.assertFalse(triangle('a', 'c', 'b', lines))

    def test_inner_count(self):
        self.assertEquals(3, inner_count(['a', 'b', 'c', 'd'],
                                         [['a', 'c', 'b'],['a', 'd'], ['c', 'd'], ['b', 'd']]))

    def test_parse_points(self):
        self.assertEquals(['a', 'b', 'c', 'd'], parse_points("abcd"))

    def test_parseLines(self):
        self.assertEquals([['a', 'c', 'b'], ['a', 'd'], ['c', 'd'], ['b', 'd']],
                          parse_lines(["acb", "ad", "cd", "bd"]))

    def test_count(self):
        self.assertEquals(24, count("abcdefghijk", ["abc", "adef", "aghi", "ajk", "bdgj", "cehj", "cfik"]))

    def test_count_2(self):
        self.assertEquals(72, count("abcdefghijklmnopqrstu", ["abcd", "agmnps", "ahkqrt", "aiju", "defghi",
                                                              "donlkj", "dstu", "uqlmfb", "urpoec"]))