import unittest

from action.tofizz import ToFizz

class TestToFizz(unittest.TestCase):
    def test_tofizz(self):
        toFizz = ToFizz()
        self.assertEquals('Fizz', toFizz.act(3))

