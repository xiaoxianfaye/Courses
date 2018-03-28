import unittest

from expression import Expression

class TestExpression(unittest.TestCase):
    def test_multidigit_operand(self):
        expr = Expression("12")
        self.assertEquals(12, expr.eval())

    def test_multichar_operator(self):
        expr = Expression("10**2")
        self.assertEquals(100, expr.eval())

    def test_negative_operand(self):
        expr = Expression("-12")
        self.assertEquals(-12, expr.eval())

    def test_parenthesis_without_negative(self):
        expr = Expression("10*(48-(12+34))")
        self.assertEquals(20, expr.eval())

    def test_parenthesis_with_negative(self):
        expr = Expression("((12+(-11))*2-11)**2")
        self.assertEquals(81, expr.eval())