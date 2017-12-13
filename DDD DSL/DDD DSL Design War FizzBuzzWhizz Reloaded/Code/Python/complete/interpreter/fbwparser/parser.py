import re

DELIMETER = ' '

def tokenize(rule_descs):
    return [_normalize(rule_desc).split(DELIMETER) \
            for rule_desc in rule_descs if not _blank(rule_desc)]

def _normalize(line):
    return re.sub(r'\s+', DELIMETER, line.strip())

def _blank(line):
    return line.strip() == ''
    
