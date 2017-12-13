import unittest

from parser import *

class TestParser(unittest.TestCase):
    SPEC_TOKENS = [['r1_3', 'atom', 'times', '3', 'to_fizz'],
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

    def test_tokenize(self):
        rule_descs = ["r1_3 atom times 3 to_fizz",
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
        
        self.assertEquals(TestParser.SPEC_TOKENS, tokenize(rule_descs))