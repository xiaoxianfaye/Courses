from doublestackprocessor import DoubleStackProcessor

class Expression(object):
    def __init__(self, expr, context=None):
        if context == None:
            self.ctx = Context(expr, 0)
        else:
            self.ctx = context

        self.dsp = DoubleStackProcessor()

    def eval(self):
        while self.ctx.expr_not_end():
            c = self.ctx.next_char()
            if '(' == c:
                self.dsp.push_operand(Expression(self.ctx.expr, self.ctx).eval())
            elif ')' == c:
                return self.dsp.result()
            else:
                self.dsp.process(c)

        return self.dsp.result()

class Context(object):
    def __init__(self, expr, curidx):
        self.expr = expr
        self.curidx = curidx

    def expr_not_end(self):
        return self.curidx < len(self.expr)

    def next_char(self):
        c = self.expr[self.curidx]
        self.curidx += 1
        return c