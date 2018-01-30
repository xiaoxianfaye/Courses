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
