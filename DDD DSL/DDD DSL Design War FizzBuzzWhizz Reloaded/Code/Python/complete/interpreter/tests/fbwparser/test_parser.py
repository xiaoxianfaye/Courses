import unittest

from fbwparser.parser import *

class TestParser(unittest.TestCase):
    RULE_DESCS = ["r1_3 atom times 3 to_fizz",
                  "r1_5 atom times 5 to_buzz",
                  "r1_7 atom times 7 to_whizz",
                  "",
                  "r1 or r1_3 r1_5 r1_7",
                  "",
                  "r1_357 and r1_3 r1_5 r1_7",
                  "r1_35 and r1_3 r1_5",
                  "r1_37 and r1_3 r1_7",
                  "r1_57 and r1_5 r1_7",
                  "",
                  "r2 or r1_357 r1_35 r1_37 r1_57",
                  "",
                  "r3 atom contains 3 to_fizz",
                  "",
                  "rd atom always_true to_str",
                  "  ",
                  " spec  or r3 r2 r1  rd  ",
                  " "]

    RULE_TOKENS = [['r1_3', 'atom', 'times', '3', 'to_fizz'],
                   ['r1_5', 'atom', 'times', '5', 'to_buzz'],
                   ['r1_7', 'atom', 'times', '7', 'to_whizz'],
                   ['r1', 'or', 'r1_3', 'r1_5', 'r1_7'],
                   ['r1_357', 'and', 'r1_3', 'r1_5', 'r1_7'],
                   ['r1_35', 'and', 'r1_3', 'r1_5'],
                   ['r1_37', 'and', 'r1_3', 'r1_7'],
                   ['r1_57', 'and', 'r1_5', 'r1_7'],
                   ['r2', 'or', 'r1_357', 'r1_35', 'r1_37', 'r1_57'],
                   ['r3', 'atom', 'contains', '3', 'to_fizz'],
                   ['rd', 'atom', 'always_true', 'to_str'],
                   ['spec', 'or', 'r3', 'r2', 'r1', 'rd']]

    def setUp(self):
        self.r1_3 = atom(times(3), tofizz())
        self.r1_5 = atom(times(5), tobuzz())
        self.r1_7 = atom(times(7), towhizz())
        self.rule_map = {'r1_3':self.r1_3, 'r1_5':self.r1_5, 'r1_7':self.r1_7}

    def test_tokenize(self):
        self.assertEquals(TestParser.RULE_TOKENS, tokenize(TestParser.RULE_DESCS))

    def test_parse_atom(self):
        rule_map = {}

        self.assertEquals(atom(times(3), tofizz()), 
                          parse_atom(['r1_3', 'atom', 'times', '3', 'to_fizz'], rule_map))
        self.assertEquals(atom(times(5), tobuzz()), 
                          parse_atom(['r1_5', 'atom', 'times', '5', 'to_buzz'], rule_map))
        self.assertEquals(atom(times(7), towhizz()), 
                          parse_atom(['r1_7', 'atom', 'times', '7', 'to_whizz'], rule_map))
        self.assertEquals(atom(contains(3), tofizz()), 
                          parse_atom(['r3', 'atom', 'contains', '3', 'to_fizz'], rule_map))
        self.assertEquals(atom(alwaystrue(), tostr()), 
                          parse_atom(['rd', 'atom', 'always_true', 'to_str'], rule_map))

    def test_parse_or(self):
        self.assertEquals(ORN(self.r1_3, self.r1_5, self.r1_7), 
                          parse_or(['r1', 'or', 'r1_3', 'r1_5', 'r1_7'], self.rule_map))

    def test_parse_and(self):
        self.assertEquals(ANDN(self.r1_3, self.r1_5, self.r1_7), 
                          parse_and(['r1_357', 'and', 'r1_3', 'r1_5', 'r1_7'], self.rule_map))

    def test_parse_rule_tokens_when_atom(self):
        rule_map = {}
        parse_rule_tokens(['r1_3', 'atom', 'times', '3', 'to_fizz'], rule_map)

        self.assertEquals({'r1_3':self.r1_3}, rule_map)

    def test_parse_rule_tokens_when_or(self):
        rule_map = {}
        parse_rule_tokens(['r1_3', 'atom', 'times', '3', 'to_fizz'], rule_map)
        parse_rule_tokens(['r1_5', 'atom', 'times', '5', 'to_buzz'], rule_map)
        parse_rule_tokens(['r1_7', 'atom', 'times', '7', 'to_whizz'], rule_map)
        parse_rule_tokens(['r1', 'or', 'r1_3', 'r1_5', 'r1_7'], rule_map)

        self.assertEquals({'r1_3':self.r1_3, 'r1_5':self.r1_5, 'r1_7':self.r1_7, 
                           'r1':ORN(self.r1_3, self.r1_5, self.r1_7)}, rule_map)

    def test_parse_rule_tokens_when_and(self):
        rule_map = {}
        parse_rule_tokens(['r1_3', 'atom', 'times', '3', 'to_fizz'], rule_map)
        parse_rule_tokens(['r1_5', 'atom', 'times', '5', 'to_buzz'], rule_map)
        parse_rule_tokens(['r1_7', 'atom', 'times', '7', 'to_whizz'], rule_map)
        parse_rule_tokens(['r1_357', 'and', 'r1_3', 'r1_5', 'r1_7'], rule_map)
        
        self.assertEquals({'r1_3':self.r1_3, 'r1_5':self.r1_5, 'r1_7':self.r1_7, 
                           'r1_357':ANDN(self.r1_3, self.r1_5, self.r1_7)}, rule_map)

    def test_parse_rule_tokens_when_spec(self):
        rule_map = {}
        for rule_tokens in TestParser.RULE_TOKENS:
            parse_rule_tokens(rule_tokens, rule_map)

        expected_rule_map = {
            'r1_3':self.r1_3, 
            'r1_5':self.r1_5, 
            'r1_7':self.r1_7
        }

        r1 = ORN(self.r1_3, self.r1_5, self.r1_7)
        expected_rule_map['r1'] = r1

        r1_357 = ANDN(self.r1_3, self.r1_5, self.r1_7)
        expected_rule_map['r1_357'] = r1_357
        r1_35 = ANDN(self.r1_3, self.r1_5)
        expected_rule_map['r1_35'] = r1_35
        r1_37 = ANDN(self.r1_3, self.r1_7)
        expected_rule_map['r1_37'] = r1_37
        r1_57 = ANDN(self.r1_5, self.r1_7)
        expected_rule_map['r1_57'] = r1_57
        r2 = ORN(r1_357, r1_35, r1_37, r1_57)
        expected_rule_map['r2'] = r2
        
        r3 = atom(contains(3), tofizz())
        expected_rule_map['r3'] = r3
        
        rd = atom(alwaystrue(), tostr())
        expected_rule_map['rd'] = rd
        
        spec = ORN(r3, r2, r1, rd)
        expected_rule_map['spec'] = spec
        
        self.maxDiff = None
        self.assertEquals(expected_rule_map, rule_map)

    def test_parse_tokens(self):
        self.assertEquals(spec(), parse_tokens(TestParser.RULE_TOKENS))

    def test_parse_rule_descs(self):
        self.assertEquals(spec(), parse_rule_descs(TestParser.RULE_DESCS))
