DELIMETER = ' '

def tokenize(rule_descs):
    return [_normalize(rule_desc).split(DELIMETER) \
            for rule_desc in rule_descs if _not_blank(rule_desc)]

def _normalize(line):
    return line.strip().replace(r' +', DELIMETER)

def _not_blank(line):
    return line.strip() != ''
    
