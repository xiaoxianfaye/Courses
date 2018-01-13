import unittest

import doublestackprocessor as dsp

class TestDoubleStackProcessor(unittest.TestCase):
    def setUp(self):
        self.operandstack = []
        self.operatorstack = []

    def test_num(self):
        dsp.process('3', self.operandstack, self.operatorstack)

        self.assertEquals([3], dsp._dump(self.operandstack))

    def test_num_plus(self):
        dsp.process('3', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)

        self.assertEquals([3], dsp._dump(self.operandstack))
        self.assertEquals(['+'], dsp._dump(self.operatorstack))

    def test_num_plus_num_plus(self):
        dsp.process('3', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)
        dsp.process('2', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)

        self.assertEquals([5], dsp._dump(self.operandstack))
        self.assertEquals(['+'], dsp._dump(self.operatorstack))

    def test_num_multiply_num_multiply(self):
        dsp.process('3', self.operandstack, self.operatorstack)
        dsp.process('*', self.operandstack, self.operatorstack)
        dsp.process('2', self.operandstack, self.operatorstack)
        dsp.process('*', self.operandstack, self.operatorstack)

        self.assertEquals([6], dsp._dump(self.operandstack))
        self.assertEquals(['*'], dsp._dump(self.operatorstack))

    def test_num_multiply_num_plus(self):
        dsp.process('3', self.operandstack, self.operatorstack)
        dsp.process('*', self.operandstack, self.operatorstack)
        dsp.process('2', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)

        self.assertEquals([6], dsp._dump(self.operandstack))
        self.assertEquals(['+'], dsp._dump(self.operatorstack))

    def test_num_plus_num_multiply_num_plus(self):
        dsp.process('3', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)
        dsp.process('2', self.operandstack, self.operatorstack)
        dsp.process('*', self.operandstack, self.operatorstack)
        dsp.process('3', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)

        self.assertEquals([9], dsp._dump(self.operandstack))
        self.assertEquals(['+'], dsp._dump(self.operatorstack))

    def test_num_plus_num_subtract(self):
        dsp.process('3', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)
        dsp.process('2', self.operandstack, self.operatorstack)
        dsp.process('-', self.operandstack, self.operatorstack)

        self.assertEquals([5], dsp._dump(self.operandstack))
        self.assertEquals(['-'], dsp._dump(self.operatorstack))

    def test_num_subtract_num_plus(self):
        dsp.process('3', self.operandstack, self.operatorstack)
        dsp.process('-', self.operandstack, self.operatorstack)
        dsp.process('2', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)

        self.assertEquals([1], dsp._dump(self.operandstack))
        self.assertEquals(['+'], dsp._dump(self.operatorstack))

    def test_num_subtract_num_divide(self):
        dsp.process('4', self.operandstack, self.operatorstack)
        dsp.process('-', self.operandstack, self.operatorstack)
        dsp.process('2', self.operandstack, self.operatorstack)
        dsp.process('/', self.operandstack, self.operatorstack)

        self.assertEquals([4, 2], dsp._dump(self.operandstack))
        self.assertEquals(['-', '/'], dsp._dump(self.operatorstack))

    def test_num_divide_num_plus(self):
        dsp.process('4', self.operandstack, self.operatorstack)
        dsp.process('/', self.operandstack, self.operatorstack)
        dsp.process('2', self.operandstack, self.operatorstack)
        dsp.process('+', self.operandstack, self.operatorstack)

        self.assertEquals([2], dsp._dump(self.operandstack))
        self.assertEquals(['+'], dsp._dump(self.operatorstack))