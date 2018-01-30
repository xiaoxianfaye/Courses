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
    return reduce(_acc_div2, zip(v1[::-1], v2[::-1]), [[], 0])[0][::-1]

def _acc_div2(acc, pair):
    dividend = pair[0] + acc[1]
    divisor = pair[1]
    remainder = dividend % divisor
    acc[0].append(remainder)
    acc[1] = dividend / divisor
    return acc

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
    return reduce(_acc_dotdiv2, v, [[], c])[0]

def _acc_dotdiv2(acc, vc):
    quotient = acc[1] / vc
    remainder = acc[1] % vc
    acc[0].append(quotient)
    acc[1] = remainder
    return acc

def scale(c, v):
    return [c * vc for vc in v]