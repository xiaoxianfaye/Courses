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

def AND3(rule1, rule2, rule3):
    return AND(rule1, AND(rule2, rule3))

def AND4(rule1, rule2, rule3, rule4):
    return AND(rule1, AND3(rule2, rule3, rule4))

def ORN(*rules):
    return _ORN(list(rules))

def _ORN(rules):
    if len(rules) == 1:
        return rules[0]

    return OR(rules[0], _ORN(rules[1:]))

from predication import Times, Contains, AlwaysTrue
from action import ToFizz, ToBuzz, ToWhizz, ToStr, ToHazz

def spec():
    r1_3 = Atom(Times(3), ToFizz())
    r1_5 = Atom(Times(5), ToBuzz())
    r1_7 = Atom(Times(7), ToWhizz())
    r1_8 = Atom(Times(8), ToHazz())

    r1 = ORN(r1_3, r1_5, r1_7, r1_8)
    r2 = ORN(AND4(r1_3, r1_5, r1_7, r1_8),
             AND3(r1_3, r1_5, r1_7),
             AND3(r1_3, r1_5, r1_8),
             AND3(r1_3, r1_7, r1_8),
             AND3(r1_5, r1_7, r1_8),
             AND(r1_3, r1_5),
             AND(r1_3, r1_7),
             AND(r1_3, r1_8),
             AND(r1_5, r1_7),
             AND(r1_5, r1_8),
             AND(r1_7, r1_8))
    r3 = Atom(Contains(3), ToFizz())
    rd = Atom(AlwaysTrue(), ToStr())
    
    return ORN(r3, r2, r1, rd)
