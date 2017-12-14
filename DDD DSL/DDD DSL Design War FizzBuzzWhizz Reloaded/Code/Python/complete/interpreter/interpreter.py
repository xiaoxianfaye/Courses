def i_times(base, n):
    return n % base == 0

def i_contains(digit, n):
    return str(digit) in str(n)

def i_alwaystrue(param, n):
    return True

def i_tofizz(n):
    return 'Fizz'

def i_tobuzz(n):
    return 'Buzz'

def i_towhizz(n):
    return 'Whizz'

def i_tostr(n):
    return str(n)    

PREDICATION_MAP = {'TIMES':i_times, 'CONTAINS':i_contains, 'ALWAYSTRUE':i_alwaystrue}
ACTION_MAP = {'TOFIZZ':i_tofizz, 'TOBUZZ':i_tobuzz, 'TOWHIZZ':i_towhizz, 'TOSTR':i_tostr}

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
