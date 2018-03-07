import unittest

from businesslogic import BusinessLogicImpl

from trans import *

class TestBusinessLogicImpl(unittest.TestCase):
    def setUp(self):
        self.impl = BusinessLogicImpl()

    def test_get_all_transes(self):
        self.assertEquals([UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS, REVERSE_TRANS],
                          self.impl.get_all_transes())

    def test_transform_upper(self):
        self.assertEquals('HELLO, WORLD.', self.impl.transform('Hello, world.', [UPPER_TRANS]))

    def test_transform_lower(self):
        self.assertEquals('hello, world.', self.impl.transform('Hello, world.', [LOWER_TRANS]))

    def test_transform_trimprefixspaces(self):
        self.assertEquals('Hello, world.  ', self.impl.transform('  Hello, world.  ', [TRIM_PREFIX_SPACES_TRANS]))
        self.assertEquals('', self.impl.transform('  ', [TRIM_PREFIX_SPACES_TRANS]))
        self.assertEquals('Hello, world.  ', self.impl.transform('Hello, world.  ', [TRIM_PREFIX_SPACES_TRANS]))

    def test_transform_reverse(self):
        self.assertEquals('  .dlrow ,olleH  ', self.impl.transform("  Hello, world.  ", [REVERSE_TRANS]))

    def test_transform(self):
        self.assertEquals("hello, world.  ",
                          self.impl.transform('  Hello, world.  ',
                                              [UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS]))