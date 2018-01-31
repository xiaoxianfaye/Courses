def equal(v1, v2):
    return v1 == v2

def add(v1, v2):
    if len(v1) == len(v2):
        return [vc1 + vc2 for (vc1, vc2) in zip(v1, v2)]

    return None

def dotproduct(v1, v2):
    if len(v1) == len(v2):
        return sum([vc1 * vc2 for (vc1, vc2) in zip(v1, v2)])

    return None

def div(v1, v2):
    if len(v1) == len(v2) and len(v2) != 0 and (0 not in v2):
        return _div(v1, v2)

    return None

def _div(v1, v2):
    result = []
    carry = 0
    for (vc1, vc2) in zip(v1[::-1], v2[::-1]):
        dividend = vc1 + carry
        divisor = vc2
        remainder = dividend % divisor
        result.append(remainder)
        carry = dividend / divisor
    return result[::-1]

def _div2(v1, v2):
    return reduce(_acc_div2, zip(v1[::-1], v2[::-1]), ([], [0]))[0][::-1]

def _acc_div2((v, wcarry), (vc1, vc2)):
    dividend = vc1 + wcarry[0]
    divisor = vc2
    remainder = dividend % divisor
    v.append(remainder)
    wcarry[0] = dividend / divisor
    return v, wcarry

def dotdiv(c, v):
    if len(v) != 0 and (0 not in v):
        return _dotdiv(c, v)

    return None

def _dotdiv(c, v):
    result = []
    remainder = c
    for vc in v:
        quotient = remainder / vc
        remainder = remainder % vc
        result.append(quotient)
    return result

def _dotdiv2(c, v):
    return reduce(_acc_dotdiv2, v, [[], [c]])[0]

def _acc_dotdiv2((v, wc), vc):
    quotient = wc[0] / vc
    remainder = wc[0] % vc
    v.append(quotient)
    wc[0] = remainder
    return v, wc

def scale(c, v):
    return [c * vc for vc in v]