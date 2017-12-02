import unittest

from doublestackprocessor import DoubleStackProcessor

class DoubleStackProcessorTestCase(unittest.TestCase):
    def test_num(self):
        dsp = DoubleStackProcessor()

        dsp.process('3')

        self.assertEquals([3], dsp._dumpOperandStack())

