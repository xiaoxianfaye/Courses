import unittest

from doublestackprocessor import DoubleStackProcessor

class TestDoubleStackProcessor(unittest.TestCase):
    def test_num(self):
        dsp = DoubleStackProcessor()

        dsp.process('3')

        self.assertEquals([3], dsp._dump_operandstack())

# import unittest

# from doublestackprocessor import DoubleStackProcessor

# class TestDoubleStackProcessor(unittest.TestCase):
#     def setUp(self):
#         self.dsp = DoubleStackProcessor()

#     def test_num(self):
#         self.dsp.process('3')

#         self.assertEquals([3], self.dsp._dumpOperandStack())

#     def test_num_plus(self):
#         self.dsp.process('3')
#         self.dsp.process('+')

#         self.assertEquals([3], self.dsp._dumpOperandStack())
#         self.assertEquals(['+'], self.dsp._dumpOperatorStack())

#     def test_num_plus_num_plus(self):
#         self.dsp.process('3')
#         self.dsp.process('+')
#         self.dsp.process('2')
#         self.dsp.process('+')        

#         self.assertEquals([5], self.dsp._dumpOperandStack())
#         self.assertEquals(['+'], self.dsp._dumpOperatorStack())




