import unittest

from measuresystem import *

class TestMeasuresystem(unittest.TestCase):
    STEPFACTORS = [0x7FFFFFFF, 1760, 3, 12]
    BASEFACTORS = [1760 * 3 * 12, 3 * 12, 12, 1]

    def test_basefactors(self):
        self.assertEquals(TestMeasuresystem.BASEFACTORS,
                          basefactors(TestMeasuresystem.STEPFACTORS))

    def test_base(self):
        self.assertEquals(63472, base([1, 2, 3, 4], TestMeasuresystem.BASEFACTORS))
        self.assertEquals(63396, base([1, 0, 3, 0], TestMeasuresystem.BASEFACTORS))

    def test_equal(self):
        self.assertTrue(equal([1, 2, 3, 4], [0, 0, 0, 63472], TestMeasuresystem.BASEFACTORS))
        self.assertTrue(equal([0, 1765, 0, 40], [1, 6, 0, 4], TestMeasuresystem.BASEFACTORS))
        self.assertFalse(equal([0, 1765, 0, 41], [1, 6, 0, 4], TestMeasuresystem.BASEFACTORS))

    def test_normalize(self):
        self.assertEquals([1, 4, 0, 1],
                          normalize(63505, TestMeasuresystem.BASEFACTORS))

    def test_add(self):
        self.assertEquals([0, 0, 2, 0],
                          add([0, 0, 0, 13], [0, 0, 0, 11], TestMeasuresystem.BASEFACTORS))
        self.assertEquals([0, 3, 0, 0],
                          add([0, 0, 3, 0], [0, 2, 0, 0], TestMeasuresystem.BASEFACTORS))

    def test_scale(self):
        self.assertEquals([0, 6, 0, 0], scale(2, [0, 2, 3, 0], TestMeasuresystem.BASEFACTORS))
        self.assertEquals([0, 6, 1, 1], add(scale(2, [0, 2, 3, 0], TestMeasuresystem.BASEFACTORS),
                                            [0, 0, 0, 13], TestMeasuresystem.BASEFACTORS))

