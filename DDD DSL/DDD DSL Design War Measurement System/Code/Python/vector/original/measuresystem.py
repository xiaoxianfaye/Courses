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

def normalize(quantityvec, stepfactors):
    return vecop.div(quantityvec, stepfactors)

def equal(quantityvec1, quantityvec2, stepfactors):
    return vecop.equal(normalize(quantityvec1, stepfactors),
                       normalize(quantityvec2, stepfactors))

def add(quantityvec1, quantityvec2, stepfactors):
    return normalize(vecop.add(quantityvec1, quantityvec2), stepfactors)