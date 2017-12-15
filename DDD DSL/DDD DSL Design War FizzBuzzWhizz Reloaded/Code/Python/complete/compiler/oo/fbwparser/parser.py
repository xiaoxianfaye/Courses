import re

from rule import *

DELIMETER = ' '

PREDICATION_MAP = {'times':lambda param: times(int(param)),
                   'contains':lambda param: contains(int(param)),
                   'always_true':lambda param: alwaystrue()}

ACTION_MAP = {'to_fizz':tofizz, 'to_buzz':tobuzz, 'to_whizz':towhizz, 'to_str':tostr}

def parse(prog_file_full_name):
    with open(prog_file_full_name, 'r') as f:
        rule = parse_rule_descs(f.readlines())
    return rule

def parse_rule_descs(rule_descs):
    return parse_tokens(tokenize(rule_descs))

def tokenize(rule_descs):
    return [_normalize(rule_desc).split(DELIMETER) \
            for rule_desc in rule_descs if not _blank(rule_desc)]

def _normalize(line):
    return re.sub(r'\s+', DELIMETER, line.strip())

def _blank(line):
    return line.strip() == ''
    
def parse_tokens(tokens):
    rule_map = {}
    for rule_tokens in tokens:
        parse_rule_tokens(rule_tokens, rule_map)
    return rule_map.get('spec')

def parse_atom(rule_tokens, _rule_map):
    predication_type = _extract_predication_type(rule_tokens)
    predication_param = _extract_predication_param(rule_tokens, predication_type)
    action_type = _extract_action_type(rule_tokens, predication_type)
    return atom(_parse_predication(predication_type, predication_param), _parse_action(action_type))

def parse_or(rule_tokens, rule_map):
    return ORN_list(_parser_ref_rules(rule_tokens, rule_map))

def parse_and(rule_tokens, rule_map):
    return ANDN_list(_parser_ref_rules(rule_tokens, rule_map))

def _extract_predication_type(rule_tokens):
    return rule_tokens[2]

def _extract_predication_param(rule_tokens, predication_type):
    return None if predication_type == 'always_true' else rule_tokens[3]

def _extract_action_type(rule_tokens, predication_type):
    return rule_tokens[3] if predication_type == 'always_true' else rule_tokens[4]

def _parse_predication(predication_type, predication_param):
    return PREDICATION_MAP[predication_type](predication_param)

def _parse_action(action_type):
    return ACTION_MAP[action_type]()

def _parser_ref_rules(rule_tokens, rule_map):
    return [rule_map[rule_name] for rule_name in rule_tokens[2:]]

RULE_TYPE_AND_FUNC_MAP = {'atom':parse_atom, 'or':parse_or, 'and':parse_and}

def parse_rule_tokens(rule_tokens, rule_map):
    rule_name = _extract_rule_name(rule_tokens)
    rule_type = _extract_rule_type(rule_tokens)

    rule_map[rule_name] = RULE_TYPE_AND_FUNC_MAP[rule_type](rule_tokens, rule_map)

def _extract_rule_name(rule_tokens):
    return rule_tokens[0]

def _extract_rule_type(rule_tokens):
    return rule_tokens[1]
