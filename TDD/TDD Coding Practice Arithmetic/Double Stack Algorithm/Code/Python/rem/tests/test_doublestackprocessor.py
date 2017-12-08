import unittest

from doublestackprocessor import DoubleStackProcessor

class TestDoubleStackProcessor(unittest.TestCase):
    def setUp(self):
        self.dsp = DoubleStackProcessor()

    def test_num(self):
        self.dsp.process('3')

        self.assertEquals([3], self.dsp._dump_operandstack())

    def test_num_plus(self):
        self.dsp.process('3')
        self.dsp.process('+')

        self.assertEquals([3], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())

    def test_num_plus_num_plus(self):
        self.dsp.process('3')
        self.dsp.process('+')
        self.dsp.process('2')
        self.dsp.process('+')        

        self.assertEquals([5], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())

    def test_num_plus_num_multiply(self):
        self.dsp.process('3')
        self.dsp.process('+')
        self.dsp.process('2')
        self.dsp.process('*')        

        self.assertEquals([3, 2], self.dsp._dump_operandstack())
        self.assertEquals(['+', '*'], self.dsp._dump_operatorstack())

    def test_num_multiply_num_multiply(self):
        self.dsp.process('3')
        self.dsp.process('*')
        self.dsp.process('2')
        self.dsp.process('*')
        
        self.assertEquals([6], self.dsp._dump_operandstack())
        self.assertEquals(['*'], self.dsp._dump_operatorstack())

    def test_num_multiply_num_plus(self):
        self.dsp.process('3')
        self.dsp.process('*')
        self.dsp.process('2')
        self.dsp.process('+')
        
        self.assertEquals([6], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())

    def test_num_plus_num_multiply_num_plus(self):
        self.dsp.process('3')
        self.dsp.process('+')
        self.dsp.process('2')
        self.dsp.process('*')
        self.dsp.process('3')
        self.dsp.process('+')
        
        self.assertEquals([9], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())

    def test_num_plus_num_subtract(self):
        self.dsp.process('3')
        self.dsp.process('+')
        self.dsp.process('2')
        self.dsp.process('-')
        
        self.assertEquals([5], self.dsp._dump_operandstack())
        self.assertEquals(['-'], self.dsp._dump_operatorstack())

    def test_num_subtract_num_plus(self):
        self.dsp.process('3')
        self.dsp.process('-')
        self.dsp.process('2')
        self.dsp.process('+')
        
        self.assertEquals([1], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())


    def test_num_subtract_num_divide(self):
        self.dsp.process('4')
        self.dsp.process('-')
        self.dsp.process('2')
        self.dsp.process('/')
        
        self.assertEquals([4, 2], self.dsp._dump_operandstack())
        self.assertEquals(['-', '/'], self.dsp._dump_operatorstack())

    def test_num_divide_num_plus(self):
        self.dsp.process('4')
        self.dsp.process('/')
        self.dsp.process('2')
        self.dsp.process('+')
        
        self.assertEquals([2], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())

    def test_num_plus_num_rem(self):
        self.dsp.process('3')
        self.dsp.process('+')
        self.dsp.process('2')
        self.dsp.process('%')
        
        self.assertEquals([3, 2], self.dsp._dump_operandstack())
        self.assertEquals(['+', '%'], self.dsp._dump_operatorstack())

    def test_num_rem_num_plus(self):
        self.dsp.process('3')
        self.dsp.process('%')
        self.dsp.process('2')
        self.dsp.process('+')
        
        self.assertEquals([1], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())        