import unittest

from layout import Component, Beside, Above

from Tkinter import Tk, Canvas

TOLERANCE = 1

def _check_rectangle(testcase, expected, actual):
    testcase.assertEquals(len(expected), len(actual))
    for i in range(0, len(expected)):
        testcase.assertAlmostEquals(expected[i], actual[i], delta=TOLERANCE)

def _container():
    return Canvas(Tk())

class ComponentStub(Component):
    def AT(self, left, top, width, height):
        self.rectangle = [left, top, width, height]

    def IN(self, container):
        self.container = container

class TestBeside(unittest.TestCase):
    def setUp(self):
        self.leftcmp = ComponentStub()
        self.rightcmp = ComponentStub()
        self.beside = Beside(self.leftcmp, self.rightcmp, 0.8)

    def test_at(self):
        self.beside.AT(20, 10, 300, 60)

        _check_rectangle(self, [20, 10, 240, 60], self.leftcmp.rectangle)
        _check_rectangle(self, [260, 10, 60, 60], self.rightcmp.rectangle)

    def test_in(self):
        container = _container()

        self.beside.IN(container)

        self.assertTrue(container == self.leftcmp.container)
        self.assertTrue(container == self.rightcmp.container)

class TestAbove(unittest.TestCase):
    def setUp(self):
        self.upcmp = ComponentStub()
        self.downcmp = ComponentStub()
        self.above = Above(self.upcmp, self.downcmp, 0.5)

    def test_at(self):
        self.above.AT(20, 10, 300, 60)

        _check_rectangle(self, [20, 10, 300, 30], self.upcmp.rectangle)
        _check_rectangle(self, [20, 40, 300, 30], self.downcmp.rectangle)

    def test_in(self):
        container = _container()

        self.above.IN(container)

        self.assertTrue(container == self.upcmp.container)
        self.assertTrue(container == self.upcmp.container)