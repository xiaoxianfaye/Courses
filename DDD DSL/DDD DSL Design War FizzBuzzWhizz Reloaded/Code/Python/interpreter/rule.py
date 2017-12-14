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

def OR3(rule1, rule2, rule3):
    return OR(rule1, OR(rule2, rule3))

def OR4(rule1, rule2, rule3, rule4):
    return OR(rule1, OR3(rule2, rule3, rule4))

def AND(rule1, rule2):
    return 'AND', rule1, rule2

def AND3(rule1, rule2, rule3):
    return AND(rule1, AND(rule2, rule3))

def spec():
    r1_3 = atom(times(3), tofizz())
    r1_5 = atom(times(5), tobuzz())
    r1_7 = atom(times(7), towhizz())

    r1 = OR3(r1_3, r1_5, r1_7)
    r2 = OR4(AND3(r1_3, r1_5, r1_7),
             AND(r1_3, r1_5),
             AND(r1_3, r1_7),
             AND(r1_5, r1_7))
    r3 = atom(contains(3), tofizz())
    rd = atom(alwaystrue(), tostr())
    
    return OR4(r3, r2, r1, rd)