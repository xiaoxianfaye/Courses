import unittest

import expression as expr

class TestExpression(unittest.TestCase):
    def test_num(self):
        self.assertEquals(3, expr.eval_expr("3"))

    def test_expr_has_parentheses(self):
        self.assertEquals(6, expr.eval_expr("2*(1+2)"))

    def test_expr_final(self):
        self.assertEquals(8, expr.eval_expr("((1+2)+1)*2"))
        self.assertEquals(8, expr.eval_expr("(1+(1+2))*2"))
        self.assertEquals(6, expr.eval_expr("(2-(1-2))*3+(2-(2+1))*3"))