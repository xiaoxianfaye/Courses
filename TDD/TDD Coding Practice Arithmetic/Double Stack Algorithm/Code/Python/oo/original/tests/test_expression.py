import unittest

from expression import Expression, Context

class TestExpression(unittest.TestCase):
    def test_num(self):
        expr = Expression("3")
        self.assertEquals(3, expr.eval())

    def test_expr_has_one_operator(self):
        expr = Expression("1+2")
        self.assertEquals(3, expr.eval())

    def test_expr_has_multiple_operators(self):
        expr = Expression("1+2*3")
        self.assertEquals(7, expr.eval())

    def test_expr_context(self):
        expr = Expression(None, Context("3", 0))
        self.assertEquals(3, expr.eval())

    def test_expr_has_parentheses(self):
        expr = Expression("2*(1+2)")
        self.assertEquals(6, expr.eval())

    def test_expr_final(self):
        expr = Expression("((1+2)+1)*2")
        self.assertEquals(8, expr.eval())

        expr = Expression("(1+(1+2))*2")
        self.assertEquals(8, expr.eval())

        expr = Expression("(2-(1-2))*3+(2-(2+1))*3")
        self.assertEquals(6, expr.eval())