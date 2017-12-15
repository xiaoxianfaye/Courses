class OORule(object):
    def apply(self, n):
        pass

class OOAtom(OORule):
    def __init__(self, predication, action):
        self.predication = predication
        self.action = action

    def apply(self, n):
        if self.predication.predicate(n):
            return True, self.action.act(n)

        return False, ''

class OOOR(OORule):
    def __init__(self, rule1, rule2):
        self.rule1 = rule1
        self.rule2 = rule2

    def apply(self, n):
        result1 = self.rule1.apply(n)
        if result1[0]:
            return result1

        return self.rule2.apply(n)

class OOAND(OORule):
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

def OOOR3(rule1, rule2, rule3):
    return OOOR(rule1, OOOR(rule2, rule3))

def OOOR4(rule1, rule2, rule3, rule4):
    return OOOR(rule1, OOOR3(rule2, rule3, rule4))

def OOAND3(rule1, rule2, rule3):
    return OOAND(rule1, OOAND(rule2, rule3))

from compiled.predication import *
from compiled.action import *

def spec():
    r1_3 = OOAtom(OOTimes(3), OOToFizz())
    r1_5 = OOAtom(OOTimes(5), OOToBuzz())
    r1_7 = OOAtom(OOTimes(7), OOToWhizz())

    r1 = OOOR3(r1_3, r1_5, r1_7)
    r2 = OOOR4(OOAND3(r1_3, r1_5, r1_7),
               OOAND(r1_3, r1_5),
               OOAND(r1_3, r1_7),
               OOAND(r1_5, r1_7))
    r3 = OOAtom(OOContains(3), OOToFizz())
    rd = OOAtom(OOAlwaysTrue(), OOToStr())
    
    return OOOR4(r3, r2, r1, rd)
