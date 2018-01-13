import doublestackprocessor as dsp

def eval_expr(expr):
    return _eval_expr(expr, 0, [], [])

def _eval_expr(expr, curidx, operandstack, operatorstack):
    while not_end(expr, curidx):
        c, curidx = next_char(expr, curidx)
        if '(' == c:
            dsp.push_operand(_eval_expr(expr, curidx, [], []))
        elif ')' == c:
            return dsp.result(operandstack, operatorstack)
        else:
            dsp.process(c, operandstack, operatorstack)

    return dsp.result(operandstack, operatorstack)

def not_end(expr, curidx):
    return curidx < len(expr)

def next_char(expr, curidx):
    c = expr[curidx]
    curidx += 1
    return c, curidx
