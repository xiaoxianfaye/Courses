import unittest

from tests.fbwparser.test_parser import TestParser

from tests.compiled.test_predication import TestTimes, TestContains, TestAlwaysTrue
from tests.compiled.test_action import TestToFizz, TestToBuzz, TestToWhizz, TestToStr
from tests.compiled.test_rule import TestAtom, TestOR, TestAND, TestRule

from tests.test_compiler import TestCompiler

if __name__ == '__main__':
    unittest.main()