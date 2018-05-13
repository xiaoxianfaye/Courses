import unittest

from measuresystem_ui import *

class TestMeasuresystemUI(unittest.TestCase):
    STEPFACTORS = [0x7FFFFFFF, 1760, 3, 12]
    SYSUNITS = ['Mile', 'Yard', 'Feet', 'Inch']

    def test_parse_unitconversion_desc(self):
        self.assertEquals((TestMeasuresystemUI.STEPFACTORS, TestMeasuresystemUI.SYSUNITS),
                          parse_unitconversion_desc(' Mile  1760 Yard 3 Feet 12 Inch  '))

        self.assertEquals((TestMeasuresystemUI.STEPFACTORS, TestMeasuresystemUI.SYSUNITS),
                          parse_unitconversion_desc2(' Mile  1760 Yard 3 Feet 12 Inch  '))

    def test_parse_quantity_desc(self):
        self.assertEquals([1, 2, 3, 4], parse_quantity_desc(' 1 Mile  2 Yard 3 Feet 4 Inch  ', TestMeasuresystemUI.SYSUNITS))
        self.assertEquals([1, 2, 0, 4], parse_quantity_desc('1 Mile 4 Inch 2 Yard', TestMeasuresystemUI.SYSUNITS))
        self.assertEquals([1, 2, 0, 4], parse_quantity_desc('1 Mile 2 Yard 4 Inch', TestMeasuresystemUI.SYSUNITS))
        self.assertEquals([0, 0, 3, 0], parse_quantity_desc('3 Feet', TestMeasuresystemUI.SYSUNITS))

        self.assertEquals([1, 2, 3, 4], parse_quantity_desc2(' 1 Mile  2 Yard 3 Feet 4 Inch  ', TestMeasuresystemUI.SYSUNITS))
        self.assertEquals([1, 2, 0, 4], parse_quantity_desc2('1 Mile 4 Inch 2 Yard', TestMeasuresystemUI.SYSUNITS))
        self.assertEquals([1, 2, 0, 4], parse_quantity_desc2('1 Mile 2 Yard 4 Inch', TestMeasuresystemUI.SYSUNITS))
        self.assertEquals([0, 0, 3, 0], parse_quantity_desc2('3 Feet', TestMeasuresystemUI.SYSUNITS))

    def test_equal(self):
        self.assertTrue(equal('1 Mile 2 Yard 3 Feet 4 Inch', '63472 Inch',
                              TestMeasuresystemUI.SYSUNITS, TestMeasuresystemUI.STEPFACTORS))
        self.assertTrue(equal('1765 Yard 40 Inch', '1 Mile 6 Yard 4 Inch',
                              TestMeasuresystemUI.SYSUNITS, TestMeasuresystemUI.STEPFACTORS))
        self.assertFalse(equal('1765 Yard 41 Inch', '1 Mile 6 Yard 4 Inch',
                              TestMeasuresystemUI.SYSUNITS, TestMeasuresystemUI.STEPFACTORS))

    def test_format(self):
        self.assertEquals('1 Mile 3 Yard 4 Inch', format([1, 3, 0, 4], TestMeasuresystemUI.SYSUNITS))
        self.assertEquals('2 Feet 0 Inch', format([0, 0, 2, 0], TestMeasuresystemUI.SYSUNITS))

    def test_add(self):
        self.assertEquals('2 Feet 0 Inch',
                          add('13 Inch', '11 Inch',
                              TestMeasuresystemUI.SYSUNITS, TestMeasuresystemUI.STEPFACTORS))
        self.assertEquals('3 Yard 0 Inch',
                          add('3 Feet', '2 Yard',
                              TestMeasuresystemUI.SYSUNITS, TestMeasuresystemUI.STEPFACTORS))

    def test_base_format(self):
        self.assertEquals('63472 Inch',
                          base_format([1, 3, 0, 4],
                                      TestMeasuresystemUI.SYSUNITS, TestMeasuresystemUI.STEPFACTORS))