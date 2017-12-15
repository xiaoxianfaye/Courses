def join_predication(typedesc):
    def join_typedesc_param(param):
        if not param:
            return typedesc
        return '_'.join([typedesc, str(param)])
    return join_typedesc_param

PREDICATION_MAP = {'TIMES':join_predication('times'), 
                   'CONTAINS':join_predication('contains'), 
                   'ALWAYSTRUE':join_predication('always_true')}
    
def desc_predication(predication):
    return PREDICATION_MAP[predication[0]](predication[1])

ACTION_MAP = {'TOFIZZ':'to_fizz', 
              'TOBUZZ':'to_buzz', 
              'TOWHIZZ':'to_whizz', 
              'TOSTR':'to_str'}

def desc_action(action):
    return ACTION_MAP[action]

def join_rule(typedesc, param1desc, param2desc):
    return ', '.join([typedesc, param1desc, param2desc])

def wrap_braces(s):
    return '{%s}' % (s)

def desc_atom(atom_rule):
    return wrap_braces(join_rule('atom', desc_predication(atom_rule[1]), desc_action(atom_rule[2])))

def desc_or(or_rule):
    return wrap_braces(join_rule('or', desc(or_rule[1]), desc(or_rule[2])))

def desc_and(and_rule):
    return wrap_braces(join_rule('and', desc(and_rule[1]), desc(and_rule[2])))

RULE_MAP = {'ATOM':desc_atom, 'OR':desc_or, 'AND':desc_and}

def desc(rule):
    return RULE_MAP[rule[0]](rule)
