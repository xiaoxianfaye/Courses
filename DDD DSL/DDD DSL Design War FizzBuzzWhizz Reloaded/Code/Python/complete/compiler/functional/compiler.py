from impl import *

PREDICATION_MAP = {'TIMES':i_times, 'CONTAINS':i_contains, 'ALWAYSTRUE':i_alwaystrue}

def compile_predication(predication):
    return PREDICATION_MAP[predication[0]](predication[1])

ACTION_MAP = {'TOFIZZ':i_tofizz, 'TOBUZZ':i_tobuzz, 'TOWHIZZ':i_towhizz, 'TOSTR':i_tostr}

def compile_action(action):
    return ACTION_MAP[action]()

def compile_atom(atom_rule):
    return i_atom(compile_predication(atom_rule[1]), compile_action(atom_rule[2]))

def compile_or(or_rule):
    return i_or(compile(or_rule[1]), compile(or_rule[2]))

def compile_and(and_rule):
    return i_and(compile(and_rule[1]), compile(and_rule[2]))

RULE_MAP = {'ATOM':compile_atom, 'OR':compile_or, 'AND':compile_and}

def compile(rule):
    return RULE_MAP[rule[0]](rule)