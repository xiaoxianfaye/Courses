import unittest

from action import ToFizz, ToBuzz, ToWhizz, ToStr

class TestToFizz(unittest.TestCase):
    def test_toFizz(self):
        toFizz = ToFizz()
        self.assertEquals('Fizz', toFizz.act(3))

class TestToBuzz(unittest.TestCase):
    def test_toBuzz(self):
        toBuzz = ToBuzz()
        self.assertEquals('Buzz', toBuzz.act(5))

class TestToWhizz(unittest.TestCase):
    def test_toWhizz(self):
        toWhizz = ToWhizz()
        self.assertEquals('Whizz', toWhizz.act(7))

class TestToStr(unittest.TestCase):
    def test_toStr(self):
        toStr = ToStr()
        self.assertEquals('1', toStr.act(1))
        self.assertEquals('10', toStr.act(10))
