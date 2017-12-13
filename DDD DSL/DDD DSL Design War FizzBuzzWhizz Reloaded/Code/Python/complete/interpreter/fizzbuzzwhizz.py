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
    return _ORN(list(rules))

def _ORN(rules):
    return _combine(rules, OR)

def ANDN(*rules):
    return _ANDN(list(rules))

def _ANDN(rules):
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

def _times(base, n):
    return n % base == 0

def _contains(digit, n):
    return str(digit) in str(n)

def _alwaystrue(param, n):
    return True

def _tofizz(n):
    return 'Fizz'

def _tobuzz(n):
    return 'Buzz'

def _towhizz(n):
    return 'Whizz'

def _tostr(n):
    return str(n)    

PREDICATION_MAP = {'TIMES': _times, 'CONTAINS':_contains, 'ALWAYSTRUE':_alwaystrue}
ACTION_MAP = {'TOFIZZ':_tofizz, 'TOBUZZ':_tobuzz, 'TOWHIZZ':_towhizz, 'TOSTR':_tostr}

def apply_atom(predication, action, n):
    if apply_predication(predication, n):
        return True, apply_action(action, n)
    return False, ''

def apply_predication(predication, n):
    if predication[0] in PREDICATION_MAP:
        return PREDICATION_MAP[predication[0]](predication[1], n)
    return False

def apply_action(action, n):
    if action in ACTION_MAP:
        return ACTION_MAP[action](n)
    return ''

def apply_or(rule1, rule2, n):
    result1 = apply_rule(rule1, n)
    if result1[0]:
        return result1
    return apply_rule(rule2, n)

def apply_and(rule1, rule2, n):
    result1 = apply_rule(rule1, n)
    if not result1[0]:
        return False, ''
    result2 = apply_rule(rule2, n)
    if not result2[0]:
        return False, ''
    return True, ''.join([result1[1], result2[1]])

RULE_MAP = {'ATOM':apply_atom, 'OR':apply_or, 'AND':apply_and}

def apply_rule(rule, n):
    if rule[0] in RULE_MAP:
        return RULE_MAP[rule[0]](rule[1], rule[2], n)
    else:
        return False, ''

def run():
    s = spec()
    results = [apply_rule(s, n) for n in range(1, 101)]
    output(results)

def output(results):
    print [result[1] for result in results]

if __name__ == '__main__':
    run()    
