import doublestackprocessor as dsp

def eval_expr(expr):
    return _eval_expr(expr, [0], [], [])

def _eval_expr(expr, wcuridx, operandstack, operatorstack):
    while expr_not_end(expr, wcuridx):
        c = next_char(expr, wcuridx)
        if '(' == c:
            dsp.push_operand(_eval_expr(expr, wcuridx, [], []), operandstack)
        elif ')' == c:
            return dsp.result(operandstack, operatorstack)
        else:
            dsp.process(c, operandstack, operatorstack)

    return dsp.result(operandstack, operatorstack)

def expr_not_end(expr, wcuridx):
    return wcuridx[0] < len(expr)

def next_char(expr, wcuridx):
    c = expr[wcuridx[0]]
    wcuridx[0] += 1
    return c