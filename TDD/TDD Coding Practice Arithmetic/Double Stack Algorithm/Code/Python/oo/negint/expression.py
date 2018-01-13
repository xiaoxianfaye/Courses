from doublestackprocessor import DoubleStackProcessor

class Expression(object):
    def __init__(self, expr, context=None):
        if context == None:
            self.ctx = Context(expr, 0)
        else:
            self.ctx = context

        self.dsp = DoubleStackProcessor()

    def eval(self):
        digitbuffer = []
        operatorbuffer = []

        while self.ctx.expr_not_end():
            c = self.ctx.next_char()
            if '(' == c:
                # else operatorBuffer.length() == 0 when operator followed by left parenthesis
                self.process_operator(operatorbuffer)

                self.dsp.push_operand(Expression(self.ctx.expr, self.ctx).eval())
            elif ')' == c:
                # else len(digitbuffer) == 0 when right parenthesis followed by right parenthesis
                self.process_operand(digitbuffer)

                return self.dsp.result()
            else:
                if c.isdigit():
                    # else len(operatorbuffer) == 0 when operator followed by digit
                    self.process_operator(operatorbuffer)

                    digitbuffer += c
                elif self.minus_leading_expr(c):
                    digitbuffer.append(c)
                else:
                    # else len(digitbuffer) == 0 when right parenthesis followed by an operator
                    self.process_operand(digitbuffer)

                    operatorbuffer += c

        # else len(digitbuffer) == 0 when expr ends with right parenthesis
        self.process_operand(digitbuffer)
        
        return self.dsp.result()

    def process_operator(self, operatorbuffer):
        self.process_operelement(operatorbuffer)

    def process_operand(self, digitbuffer):
        self.process_operelement(digitbuffer)

    def process_operelement(self, buffer):
        if len(buffer) != 0:
            self.dsp.process(self.tostr(buffer))
            del buffer[:]

    def tostr(self, lst):
        return ''.join(lst)

    def minus_leading_expr(self, c):
        return c == '-' and (self.ctx.curchar_leading_expr() or '(' == self.ctx.prev_char())

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

    def curchar_leading_expr(self):
        return self.curidx == 1

    def prev_char(self):
        return self.expr[self.curidx - 2]