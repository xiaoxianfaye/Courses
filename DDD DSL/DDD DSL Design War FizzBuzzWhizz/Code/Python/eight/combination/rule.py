class Rule(object):
    def apply(self, n):
        pass

class Atom(Rule):
    def __init__(self, predication, action):
        self.predication = predication
        self.action = action

    def apply(self, n):
        if self.predication.predicate(n):
            return True, self.action.act(n)

        return False, ''

class OR(Rule):
    def __init__(self, rule1, rule2):
        self.rule1 = rule1
        self.rule2 = rule2

    def apply(self, n):
        result1 = self.rule1.apply(n)
        if result1[0]:
            return result1

        return self.rule2.apply(n)

class AND(Rule):
    def __init__(self, rule1, rule2):
        self.rule1 = rule1
        self.rule2 = rule2

    def apply(self, n):
        result1 = self.rule1.apply(n)
        if not result1[0]:
            return False, ''

        result2 = self.rule2.apply(n)
        if not result2[0]:
            return False, ''

        return True, ''.join([result1[1], result2[1]])

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

from predication import Times, Contains, AlwaysTrue
from action import ToFizz, ToBuzz, ToWhizz, ToStr, ToHazz

def spec():
    r1_3 = Atom(Times(3), ToFizz())
    r1_5 = Atom(Times(5), ToBuzz())
    r1_7 = Atom(Times(7), ToWhizz())
    r1_8 = Atom(Times(8), ToHazz())

    r1 = ORN(r1_3, r1_5, r1_7, r1_8)

    atom_rules = [r1_3, r1_5, r1_7, r1_8]
    combinated_rules = flatten([combinate(atom_rules, 4), 
                                combinate(atom_rules, 3), 
                                combinate(atom_rules, 2)])
    r2 = _ORN([_ANDN(rules) for rules in combinated_rules])

    r3 = Atom(Contains(3), ToFizz())
    rd = Atom(AlwaysTrue(), ToStr())
    
    return ORN(r3, r2, r1, rd)

import itertools

def combinate(lst, n):
    return [list(ele) for ele in itertools.combinations(lst, n)]

def flatten(embeddedlist):
    return list(itertools.chain.from_iterable(embeddedlist))
