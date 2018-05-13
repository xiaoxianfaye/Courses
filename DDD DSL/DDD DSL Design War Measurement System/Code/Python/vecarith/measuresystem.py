import vecop

def basefactors(stepfactors):
    result = []
    for (i, stepfactor) in enumerate(stepfactors):
        result.append(reduce(lambda x, y: x * y, stepfactors[i + 1:], 1))
    return result

def basefactors2(stepfactors):
    def acc_basefactors2(acc, stepfactor):
        acc.append(acc[-1] * stepfactor)
        return acc

    return reduce(acc_basefactors2, stepfactors[::-1][:-1], [1])[::-1]

def base(quantityvec, basefactors):
    return vecop.dotproduct(quantityvec, basefactors)

def equal(quantityvec1, quantityvec2, basefactors):
    return base(quantityvec1, basefactors) == base(quantityvec2, basefactors)

def normalize(quantityvalue, basefactors):
    return vecop.dotdiv(quantityvalue, basefactors)

def add(quantityvec1, quantityvec2, basefactors):
    return normalize(base(quantityvec1, basefactors) + base(quantityvec2, basefactors), basefactors)

def scale(c, quantityvec, basefactors):
    return normalize(c * base(quantityvec, basefactors), basefactors)