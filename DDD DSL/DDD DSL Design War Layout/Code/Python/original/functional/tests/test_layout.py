import unittest

from layout import *

from Tkinter import Tk, Canvas

TOLERANCE = 1

class TestLayout(unittest.TestCase):
    def setUp(self):
        self.rectangles = []
        self.containers = []
        self.container = Canvas(Tk())

    def test_beside(self):
        leftcmp = self._componentstub()
        rightcmp = self._componentstub()

        beside(leftcmp, rightcmp, 0.8)(rectangle(20, 10, 300, 60))(self.container)

        self._check_rectangle(rectangle(20, 10, 240, 60), self.rectangles[0])
        self._check_rectangle(rectangle(260, 10, 60, 60), self.rectangles[1])

        self.assertTrue(self.container == self.containers[0])
        self.assertTrue(self.container == self.containers[1])

    def test_above(self):
        upcmp = self._componentstub()
        downcmp = self._componentstub()

        above(upcmp, downcmp, 0.5)(rectangle(20, 10, 300, 60))(self.container)

        self._check_rectangle(rectangle(20, 10, 300, 30), self.rectangles[0])
        self._check_rectangle(rectangle(20, 40, 300, 30), self.rectangles[1])

        self.assertTrue(self.container == self.containers[0])
        self.assertTrue(self.container == self.containers[1])

    def _componentstub(self):
        def AT(_rectangle):
            def IN(_container):
                self.rectangles.append(_rectangle)
                self.containers.append(_container)
            return IN
        return AT

    def _check_rectangle(self, expected, actual):
        self.assertEquals(len(expected), len(actual))
        for (k, v) in actual.items():
            self.assertTrue(k in expected.keys())
            self.assertAlmostEquals(expected[k], v, delta=TOLERANCE)