import unittest

from businesslogic import BusinessLogicImpl

from trans import *

class TestBusinessLogicImpl(unittest.TestCase):
    def test_get_all_transes(self):
        impl = BusinessLogicImpl()
        self.assertEquals([UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS], impl.get_all_transes())