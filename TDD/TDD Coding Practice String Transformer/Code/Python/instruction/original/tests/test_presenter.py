import unittest

from presenter import Presenter
from validator import ValidatingResult

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

    def test_add_not_the_last_trans(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})

        self.presenter.add_trans()

        expected = {CHAIN_TRANSES:[UPPER_TRANS],
                    CHAIN_SELECTED_INDEX:0,
                    AVAIL_SELECTED_INDEX:1}
        self.assertEquals(expected, self.viewstub.get_on_add_trans_data())

    def test_add_the_last_trans(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:TRIM_PREFIX_SPACES_TRANS})

        self.presenter.add_trans()

        expected = {CHAIN_TRANSES:[TRIM_PREFIX_SPACES_TRANS],
                    CHAIN_SELECTED_INDEX:0,
                    AVAIL_SELECTED_INDEX:0}
        self.assertEquals(expected, self.viewstub.get_on_add_trans_data())

    def test_add_already_existed_in_chain_trans(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS: UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS: LOWER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS: LOWER_TRANS})

        self.presenter.add_trans()

        self.assertEquals(ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS,
                          self.viewstub.get_on_validating_failed_data())
        expected = {CHAIN_TRANSES:[UPPER_TRANS, LOWER_TRANS],
                    CHAIN_SELECTED_INDEX:1,
                    AVAIL_SELECTED_INDEX:1}
        self.assertEquals(expected, self.viewstub.get_on_add_trans_data())


from view import View

class ViewStub(View):
    # Override
    def set_presenter(self, presenter): pass

    # Override
    def on_init(self, data):
        self.on_init_data = data

    def get_on_init_data(self):
        return self.on_init_data

    # Override
    def collect_add_trans_data(self):
        return self.add_trans_data

    def set_add_trans_data(self, data):
        self.add_trans_data = data

    # Override
    def on_add_trans(self, data):
        self.on_add_trans_data = data

    def get_on_add_trans_data(self):
        return self.on_add_trans_data

    # Override
    def on_validating_failed(self, data):
        self.on_validating_failed_data = data

    def get_on_validating_failed_data(self):
        return self.on_validating_failed_data


from businesslogic import BusinessLogic

class BusinessLogicStub(BusinessLogic):
    def get_all_transes(self):
        return [UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS]