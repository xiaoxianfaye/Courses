from doublestackprocessor import DoubleStackProcessor

class Expression(object):
    def __init__(self, expr, context=None):
        if context == None:
            self.ctx = Context(expr, 0)
        else:
            self.ctx = context

        self.dsp = DoubleStackProcessor()
        self.parser = Parser(self.ctx)

    def eval(self):
        while self.parser.expr_not_end():
            item = self.parser.next_item()
            if '(' == item:
                self.dsp.push_operand(Expression(self.ctx.expr, self.ctx).eval())
            elif ')' == item:
                return self.dsp.result()
            else:
                self.dsp.process(item)
        
        return self.dsp.result()


class Parser(object):
    def __init__(self, ctx):
        self.ctx = ctx

    def expr_not_end(self):
        return self.ctx.curidx < len(self.ctx.expr)

    def next_item(self):
        c = self._next_char()
        return c + self._subsequence(c)

    def _next_char(self):
        c = self.ctx.expr[self.ctx.curidx]
        self.ctx.curidx += 1
        return c

    def _subsequence(self, c):
        if c.isdigit() or self._isnegative(c):
            return self._next_operand()

        if self._isopchar(c):
            return self._next_operator()

        return ''

    def _next_operand(self):
        return self._next_item(self._isdigitchar)

    def _next_operator(self):
        return self._next_item(self._isopchar)

    def _next_item(self, pred):
        result = ''
        while pred(self._peek_char()):
            result += self._next_char()
        return result

    def _peek_char(self):
        return self.ctx.expr[self.ctx.curidx] if self.expr_not_end() else ''

    def _prev_char(self):
        return self.ctx.expr[self.ctx.curidx - 2]

    def _isdigitchar(self, c):
        return c.isdigit()

    def _isopchar(self, c):
        return c in '+-*/%'

    def _isnegative(self, c):
        return c == '-' and (self.ctx.curidx == 1 or self._prev_char() == '(')


class Context(object):
    def __init__(self, expr, curidx):
        self.expr = expr
        self.curidx = curidx