import unittest

from businesslogic import BusinessLogicImpl

class TestBusinessLogicImpl(unittest.TestCase):
    def setUp(self):
        self.impl = BusinessLogicImpl()

    def test_transform_upper(self):
        self.assertEquals('HELLO, WORLD!', self.impl.transform('Hello, world!', ['Upper']))

    def test_transform_lower(self):
        self.assertEquals('hello, world!', self.impl.transform('Hello, world!', ['Lower']))

    def test_transform_trimprefixspaces(self):
        self.assertEquals('Hello, world!  ', self.impl.transform('  Hello, world!  ', ['TrimPrefixSpaces']))
        self.assertEquals('', self.impl.transform('  ', ['TrimPrefixSpaces']))
        self.assertEqual('Hello, world!  ', self.impl.transform('Hello, world!  ', ['TrimPrefixSpaces']))

    def test_transform(self):
        self.assertEquals("hello, world!  ",
                          self.impl.transform('  Hello, world!  ', ['Upper', 'Lower', 'TrimPrefixSpaces']))

    def test_get_all_transids(self):
        self.assertEquals(['Upper', 'Lower', 'TrimPrefixSpaces'], self.impl.get_all_transids())