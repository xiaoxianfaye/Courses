import unittest

from doublestackprocessor import DoubleStackProcessor

class TestDoubleStackProcessor(unittest.TestCase):
    def setUp(self):
        self.dsp = DoubleStackProcessor()

    def test_num(self):
        self.dsp.process('12')

        self.assertEquals([12], self.dsp._dump_operandstack())

    def test_num_plus(self):
        self.dsp.process('12')
        self.dsp.process('+')

        self.assertEquals([12], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())

    def test_num_plus_num_plus(self):
        self.dsp.process('12')
        self.dsp.process('+')
        self.dsp.process('34')
        self.dsp.process('+')

        self.assertEquals([46], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())

    def test_num_plus_num_multiply(self):
        self.dsp.process('12')
        self.dsp.process('+')
        self.dsp.process('34')
        self.dsp.process('*')        

        self.assertEquals([12, 34], self.dsp._dump_operandstack())
        self.assertEquals(['+', '*'], self.dsp._dump_operatorstack())

    def test_num_multiply_num_multiply(self):
        self.dsp.process('12');
        self.dsp.process('*');
        self.dsp.process('10');
        self.dsp.process('*');
        
        self.assertEquals([120], self.dsp._dump_operandstack());
        self.assertEquals(['*'], self.dsp._dump_operatorstack());
    
    def test_num_multiply_num_plus(self):
        self.dsp.process('12');
        self.dsp.process('*');
        self.dsp.process('10');
        self.dsp.process('+');
        
        self.assertEquals([120], self.dsp._dump_operandstack());
        self.assertEquals(['+'], self.dsp._dump_operatorstack());
    
    def test_num_plus_num_subtract(self):
        self.dsp.process('12');
        self.dsp.process('+');
        self.dsp.process('34');
        self.dsp.process('-');
        
        self.assertEquals([46], self.dsp._dump_operandstack());
        self.assertEquals(['-'], self.dsp._dump_operatorstack());

    def test_num_subtract_num_plus(self):
        self.dsp.process('34');
        self.dsp.process('-');
        self.dsp.process('12');
        self.dsp.process('+');
        
        self.assertEquals([22], self.dsp._dump_operandstack());
        self.assertEquals(['+'], self.dsp._dump_operatorstack());
    
    def test_num_subtract_num_divide(self):
        self.dsp.process('34');
        self.dsp.process('-');
        self.dsp.process('12');
        self.dsp.process('/');
        
        self.assertEquals([34, 12], self.dsp._dump_operandstack());
        self.assertEquals(['-', '/'], self.dsp._dump_operatorstack());
    
    def test_num_divide_num_plus(self):
        self.dsp.process('24');
        self.dsp.process('/');
        self.dsp.process('12');
        self.dsp.process('+');
        
        self.assertEquals([2], self.dsp._dump_operandstack());
        self.assertEquals(['+'], self.dsp._dump_operatorstack());
    
    def test_num_plus_num_rem(self):
        self.dsp.process('12');
        self.dsp.process('+');
        self.dsp.process('34');
        self.dsp.process('%');
        
        self.assertEquals([12, 34], self.dsp._dump_operandstack());
        self.assertEquals(['+', '%'], self.dsp._dump_operatorstack());
    
    def test_num_rem_num_plus(self):
        self.dsp.process('34');
        self.dsp.process('%');
        self.dsp.process('12');
        self.dsp.process('+');
                
        self.assertEquals([10], self.dsp._dump_operandstack());
        self.assertEquals(['+'], self.dsp._dump_operatorstack());

    def test_num_plus_num_multiply_num_plus(self):
        self.dsp.process('12')
        self.dsp.process('+')
        self.dsp.process('10')
        self.dsp.process('*')
        self.dsp.process('34')
        self.dsp.process('+')
        
        self.assertEquals([352], self.dsp._dump_operandstack())
        self.assertEquals(['+'], self.dsp._dump_operatorstack())
