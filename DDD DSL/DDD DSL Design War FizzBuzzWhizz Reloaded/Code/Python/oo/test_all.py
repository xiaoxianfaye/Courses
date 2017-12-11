import unittest

from tests.test_predication import TestTimes, TestContains, TestAlwaysTrue
from tests.test_action import TestToFizz, TestToBuzz, TestToWhizz, TestToStr
from tests.test_rule import TestAtom, TestOR, TestAND, TestRule

if __name__ == '__main__':
    unittest.main()