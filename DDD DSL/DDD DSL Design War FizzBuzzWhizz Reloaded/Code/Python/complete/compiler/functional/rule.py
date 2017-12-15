def times(base):
    return 'TIMES', base

def contains(digit):
    return 'CONTAINS', digit

def alwaystrue():
    return 'ALWAYSTRUE', None

def tofizz():
    return 'TOFIZZ'

def tobuzz():
    return 'TOBUZZ'

def towhizz():
    return 'TOWHIZZ'

def tostr():
    return 'TOSTR'

def atom(predication, action):
    return 'ATOM', predication, action

def OR(rule1, rule2):
    return 'OR', rule1, rule2

def AND(rule1, rule2):
    return 'AND', rule1, rule2

def ORN(*rules):
    return ORN_list(list(rules))

def ORN_list(rules):
    return _combine(rules, OR)

def ANDN(*rules):
    return ANDN_list(list(rules))

def ANDN_list(rules):
    return _combine(rules, AND)

def _combine(rules, func):
    if len(rules) == 1:
        return rules[0]

    return func(rules[0], _combine(rules[1:], func))

def spec():
    r1_3 = atom(times(3), tofizz())
    r1_5 = atom(times(5), tobuzz())
    r1_7 = atom(times(7), towhizz())

    r1 = ORN(r1_3, r1_5, r1_7)
    r2 = ORN(ANDN(r1_3, r1_5, r1_7),
             AND(r1_3, r1_5),
             AND(r1_3, r1_7),
             AND(r1_5, r1_7))
    r3 = atom(contains(3), tofizz())
    rd = atom(alwaystrue(), tostr())
    
    return ORN(r3, r2, r1, rd)

