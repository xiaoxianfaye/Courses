import unittest

from expression import Expression, Context

class TestExpression(unittest.TestCase):
    def test_num(self):
        expr = Expression("12")
        self.assertEquals(12, expr.eval())

    def test_expr_has_one_operator(self):
        expr = Expression("12+34")
        self.assertEquals(46, expr.eval())

    def test_expr_has_multiple_operators(self):
        expr = Expression("12+34*10")
        self.assertEquals(352, expr.eval())

    def test_expr_with_right_parenthesis_followed_by_an_operator(self):
        expr = Expression("(12+34)*10")
        self.assertEquals(460, expr.eval())

    def test_expr_ends_with_right_parenthesis(self):
        expr = Expression("10*(12+34)")
        self.assertEquals(460, expr.eval())

    def test_expr_with_right_parenthesis_followed_by_right_parenthesis(self):
        expr = Expression("(48-(12+34))*10")
        self.assertEquals(20, expr.eval())
        
        expr = Expression("10*(48-(12+34))")
        self.assertEquals(20, expr.eval())

    def test_expr_with_nested_parentheses(self):
        expr = Expression("((10+21)+34)*20")
        self.assertEquals(1300, expr.eval())
        
        expr = Expression("(50-(12+34))*20")
        self.assertEquals(80, expr.eval())
        
        expr = Expression("((11+23)%11)*20")
        self.assertEquals(20, expr.eval())
                
        expr = Expression("(12-(15-12))*10/(24-(12+10))/3")
        self.assertEquals(15, expr.eval())
        
        expr = Expression("(100-(1920-1900))*2/(24-(12+10))/2")
        self.assertEquals(40, expr.eval())
