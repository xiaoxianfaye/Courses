import unittest

from vecop import *

class TestVecop(unittest.TestCase):
    PLACEHOLDER = 0x7FFFFFFF

    def test_equal(self):
        self.assertTrue(equal([1, 2, 3], [1, 2, 3]))
        self.assertTrue(equal([], []))
        self.assertFalse(equal([1, 2], [1, 2, 3]))
        self.assertFalse(equal([1, 3, 4], [1, 2, 3]))

    def test_add(self):
        self.assertEquals([5, 7, 9], add([1, 2, 3], [4, 5, 6]))
        self.assertEquals([], add([], []))
        self.assertIsNone(add([1, 2], [4, 5, 6]))

    def test_dotproduct(self):
        self.assertEquals(102, dotproduct([1, 0, 2], [100, 10, 1]))
        self.assertEquals(0, dotproduct([], []))
        self.assertIsNone(dotproduct([1, 2], [4, 5, 6]))

    def test_div(self):
        self.assertEquals([0, 0, 2, 0], div([0, 0, 0, 24], [TestVecop.PLACEHOLDER, 1760, 3, 12]))
        self.assertEquals([2, 6, 0, 4], div([1, 1765, 0, 40], [TestVecop.PLACEHOLDER, 1760, 3, 12]))
        self.assertIsNone(div([1, 2, 3], [1, 2]))
        self.assertIsNone(div([], []))
        self.assertIsNone(div([1, 2], [0, 4]))

    def test_dotdiv(self):
        self.assertEquals([1, 0, 2], dotdiv(102, [100, 10, 1]))
        self.assertIsNone(dotdiv(0, []))
        self.assertIsNone(dotdiv(0, [0, 4]))

    def test_scale(self):
        self.assertEquals([2, 4, 6], scale(2, [1, 2, 3]))
        self.assertEquals([0, 0], scale(0, [1, 2]))
        self.assertEquals([], scale(2, []))