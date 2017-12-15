from compiled.predication import *
from compiled.action import *
from compiled.rule import *

def c_times(base):
    return OOTimes(base)

def c_contains(digit):
    return OOContains(digit)

def c_alwaystrue(param):
    return OOAlwaysTrue()

PREDICATION_MAP = {'TIMES':c_times, 'CONTAINS':c_contains, 'ALWAYSTRUE':c_alwaystrue}

def compile_predication(predication):
    return PREDICATION_MAP[predication[0]](predication[1])

def c_tofizz():
    return OOToFizz()

def c_tobuzz():
    return OOToBuzz()

def c_towhizz():
    return OOToWhizz()

def c_tostr():
    return OOToStr()

ACTION_MAP = {'TOFIZZ':c_tofizz, 'TOBUZZ':c_tobuzz, 'TOWHIZZ':c_towhizz, 'TOSTR':c_tostr}

def compile_action(action):
    return ACTION_MAP[action]()

def compile_atom(atom_rule):
    return OOAtom(compile_predication(atom_rule[1]), compile_action(atom_rule[2]))

def compile_or(or_rule):
    return OOOR(compile(or_rule[1]), compile(or_rule[2]))

def compile_and(and_rule):
    return OOAND(compile(and_rule[1]), compile(and_rule[2]))

RULE_MAP = {'ATOM':compile_atom, 'OR':compile_or, 'AND':compile_and}

def compile(rule):
    return RULE_MAP[rule[0]](rule)