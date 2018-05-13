def equal(v1, v2):
    return v1 == v2

def add(v1, v2):
    return [vc1 + vc2 for (vc1, vc2) in zip(v1, v2)]

def dotproduct(v1, v2):
    return sum([vc1 * vc2 for (vc1, vc2) in zip(v1, v2)])

def div(v1, v2):
    result = []
    quotient = 0
    for (vc1, vc2) in zip(v1[::-1], v2[::-1]):
        dividend = vc1 + quotient
        divisor = vc2
        quotient = dividend / divisor
        result.append(dividend % divisor)
    return result[::-1]

def div2(v1, v2):
    def acc_div2((result, wquotient), (vc1, vc2)):
        dividend = vc1 + wquotient[0]
        divisor = vc2
        wquotient[0] = dividend / divisor
        result.append(dividend % divisor)
        return result, wquotient

    return reduce(acc_div2, zip(v1[::-1], v2[::-1]), ([], [0]))[0][::-1]

def dotdiv(c, v):
    result = []
    remainder = c
    for vc in v:
        quotient = remainder / vc
        remainder = remainder % vc
        result.append(quotient)
    return result

def dotdiv2(c, v):
    def acc_dotdiv2((v, wc), vc):
        quotient = wc[0] / vc
        wc[0] = wc[0] % vc
        v.append(quotient)
        return v, wc

    return reduce(acc_dotdiv2, v, [[], [c]])[0]

def scale(c, v):
    return [c * vc for vc in v]