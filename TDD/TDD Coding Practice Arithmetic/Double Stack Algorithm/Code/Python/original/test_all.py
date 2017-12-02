import unittest
import sys

from test.test_doublestackprocessor import DoubleStackProcessorTestCase

# def test_suite(ts):
#     suite = unittest.TestLoader().loadTestsFromTestCase(ts)
#     ret = unittest.TextTestRunner(verbosity=3).run(suite)
#     if len(ret.errors) > 0 or len(ret.failures) > 0:
#         sys.exit(1)

# def test_all():
#     test_suite(DoubleStackProcessorTestCase)

if __name__ == '__main__':
    unittest.main()