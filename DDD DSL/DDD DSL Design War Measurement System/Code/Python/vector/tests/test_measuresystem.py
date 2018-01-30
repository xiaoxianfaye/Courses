import unittest

from measuresystem import *

class TestMeasuresystem(unittest.TestCase):
    PLACEHOLDER = 0x7FFFFFFF

    def test_basefactors(self):
        self.assertEquals([1760 * 3 * 12, 3 * 12, 12, 1], basefactors([TestMeasuresystem.PLACEHOLDER, 1760, 3, 12]))