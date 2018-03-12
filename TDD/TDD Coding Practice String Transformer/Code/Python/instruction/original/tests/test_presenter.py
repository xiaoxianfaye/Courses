import unittest

from presenter import Presenter

from interaction import *
from trans import *

class TestPresenter(unittest.TestCase):
    def setUp(self):
        self.viewstub = ViewStub()
        self.businesslogicstub = BusinessLogicStub()
        self.presenter = Presenter(self.viewstub, self.businesslogicstub)

        self.presenter.init()

    def test_init(self):
        expected = {AVAIL_TRANSES:[UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS],
                    AVAIL_SELECTED_INDEX:0}

        self.assertEquals(expected, self.viewstub.get_on_init_data())


from view import View

class ViewStub(View):
    # Override
    def on_init(self, data):
        self.on_init_data = data

    def get_on_init_data(self):
        return self.on_init_data


from businesslogic import BusinessLogic

class BusinessLogicStub(BusinessLogic):
    def get_all_transes(self):
        return [UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS]