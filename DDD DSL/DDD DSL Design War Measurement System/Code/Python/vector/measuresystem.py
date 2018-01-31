import vecop

def basefactors(stepfactors):
    result = []
    for (i, stepFactor) in enumerate(stepfactors[1:]):
        result.append(reduce(lambda x, y: x * y, stepfactors[i + 1:]))
    result.append(1)
    return result

def basefactors2(stepfactors):
    return reduce(_acc_basefactors2, stepfactors[::-1][:-1], [1])[::-1]

def _acc_basefactors2(acc, stepfactor):
    acc.append(acc[-1] * stepfactor)
    return acc

def base(quantityvec, basefactors):
    return vecop.dotproduct(quantityvec, basefactors)

def normalize(quantityvec, stepfactors):
    return vecop.div(quantityvec, stepfactors)

def equal(quantityvec1, quantityvec2, stepfactors):
    return vecop.equal(normalize(quantityvec1, stepfactors),
                       normalize(quantityvec2, stepfactors))

def add(quantityvec1, quantityvec2, stepfactors):
    return normalize(vecop.add(quantityvec1, quantityvec2), stepfactors)

def scale(c, quantityvec, stepfactors):
    return normalize(vecop.scale(c, quantityvec), stepfactors)