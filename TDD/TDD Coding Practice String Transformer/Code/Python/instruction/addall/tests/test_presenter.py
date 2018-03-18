# -*- coding: UTF-8 -*-

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
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:LOWER_TRANS})

        self.presenter.add_trans()

        self.assertEquals(ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS,
                          self.viewstub.get_on_validating_failed_data())
        expected = {CHAIN_TRANSES:[UPPER_TRANS, LOWER_TRANS],
                    CHAIN_SELECTED_INDEX:1,
                    AVAIL_SELECTED_INDEX:1}
        self.assertEquals(expected, self.viewstub.get_on_add_trans_data())

    def test_add_trans_but_avail_trans_not_specified(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:None})

        self.presenter.add_trans()

        self.assertEquals(ValidatingResult.VRFR_AVAIL_TRANS_NOT_SPECIFIED,
                          self.viewstub.get_on_validating_failed_data())
        expected = {CHAIN_TRANSES:[UPPER_TRANS, LOWER_TRANS],
                    CHAIN_SELECTED_INDEX:1,
                    AVAIL_SELECTED_INDEX:0}
        self.assertEquals(expected, self.viewstub.get_on_add_trans_data())

    def test_remove_not_the_last_trans_when_chain_has_more_than_one_transes(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:TRIM_PREFIX_SPACES_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_remove_trans_data({CHAIN_SELECTED_TRANS:LOWER_TRANS})

        self.presenter.remove_trans()

        expected = {CHAIN_TRANSES:[UPPER_TRANS, TRIM_PREFIX_SPACES_TRANS],
                    AVAIL_SELECTED_INDEX:1,
                    CHAIN_SELECTED_INDEX:1}
        self.assertEquals(expected, self.viewstub.get_on_remove_trans_data())

    def test_remove_the_last_trans_when_chain_has_more_than_one_transes(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:TRIM_PREFIX_SPACES_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_remove_trans_data({CHAIN_SELECTED_TRANS:TRIM_PREFIX_SPACES_TRANS})

        self.presenter.remove_trans()

        expected = {CHAIN_TRANSES:[UPPER_TRANS, LOWER_TRANS],
                    AVAIL_SELECTED_INDEX:2,
                    CHAIN_SELECTED_INDEX:0}
        self.assertEquals(expected, self.viewstub.get_on_remove_trans_data())

    def test_remove_a_trans_when_chain_has_only_one_transes(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_remove_trans_data({CHAIN_SELECTED_TRANS:UPPER_TRANS})

        self.presenter.remove_trans()

        expected = {CHAIN_TRANSES:[],
                    AVAIL_SELECTED_INDEX:0,
                    CHAIN_SELECTED_INDEX:NONE_SELECTED_INDEX}
        self.assertEquals(expected, self.viewstub.get_on_remove_trans_data())

    def test_remove_trans_when_chain_is_not_empty_but_chain_trans_not_specified(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_remove_trans_data({CHAIN_SELECTED_TRANS:None})

        self.presenter.remove_trans()

        self.assertEquals(ValidatingResult.VRFR_CHAIN_TRANS_NOT_SPECIFIED,
                          self.viewstub.get_on_validating_failed_data())
        expected = {CHAIN_TRANSES:[UPPER_TRANS],
                    AVAIL_SELECTED_INDEX:1,
                    CHAIN_SELECTED_INDEX:0}
        self.assertEquals(expected, self.viewstub.get_on_remove_trans_data())

    def test_remove_trans_when_chain_is_empty(self):
        self.presenter.remove_trans()

        self.assertEquals(ValidatingResult.VRFR_CHAIN_EMPTY,
                          self.viewstub.get_on_validating_failed_data())
        expected = {CHAIN_TRANSES:[],
                    AVAIL_SELECTED_INDEX:0,
                    CHAIN_SELECTED_INDEX:NONE_SELECTED_INDEX}
        self.assertEquals(expected, self.viewstub.get_on_remove_trans_data())

    def test_remove_all_transes_when_chain_is_not_empty(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.add_trans()

        self.presenter.remove_all_transes()

        expected = {CHAIN_TRANSES:[],
                    AVAIL_SELECTED_INDEX:0,
                    CHAIN_SELECTED_INDEX:NONE_SELECTED_INDEX}
        self.assertEquals(expected, self.viewstub.get_on_remove_all_transes_data())

    def test_remove_all_transes_when_chain_is_empty(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_remove_trans_data({CHAIN_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.remove_trans()

        self.presenter.remove_all_transes()

        self.assertEquals(ValidatingResult.VRFR_CHAIN_EMPTY,
                          self.viewstub.get_on_validating_failed_data())
        expected = {CHAIN_TRANSES:[],
                    AVAIL_SELECTED_INDEX:1,
                    CHAIN_SELECTED_INDEX:NONE_SELECTED_INDEX}
        self.assertEquals(expected, self.viewstub.get_on_remove_all_transes_data())

    def test_apply_trans_chain(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_apply_trans_chain_data({SOURCE_STR:'Hello, world.'})

        self.presenter.apply_trans_chain()

        self.assertEquals({AVAIL_SELECTED_INDEX:1, RESULT_STR:'HELLO, WORLD.'},
                          self.viewstub.get_on_apply_trans_chain_data())

    def test_apply_trans_chain_when_source_str_is_empty(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_apply_trans_chain_data({SOURCE_STR:''})

        self.presenter.apply_trans_chain()

        self.assertEquals(ValidatingResult.VRFR_SOURCE_STR_EMPTY,
                          self.viewstub.get_on_validating_failed_data())
        self.assertEquals({AVAIL_SELECTED_INDEX:1, RESULT_STR:''},
                          self.viewstub.get_on_apply_trans_chain_data())

    def test_apply_trans_chain_when_source_str_is_illegal(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:UPPER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_apply_trans_chain_data({SOURCE_STR:'a中文b'})

        self.presenter.apply_trans_chain()

        self.assertEquals(ValidatingResult.VRFR_SOURCE_STR_ILLEGAL,
                          self.viewstub.get_on_validating_failed_data())
        self.assertEquals({AVAIL_SELECTED_INDEX:1, RESULT_STR:''},
                          self.viewstub.get_on_apply_trans_chain_data())

    def test_apply_trans_chain_when_chain_is_empty(self):
        self.viewstub.set_add_trans_data({AVAIL_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.add_trans()
        self.viewstub.set_remove_trans_data({CHAIN_SELECTED_TRANS:LOWER_TRANS})
        self.presenter.remove_trans()
        self.viewstub.set_apply_trans_chain_data({SOURCE_STR:'Hello, world.'})

        self.presenter.apply_trans_chain()

        self.assertEquals(ValidatingResult.VRFR_CHAIN_EMPTY,
                          self.viewstub.get_on_validating_failed_data())
        self.assertEquals({AVAIL_SELECTED_INDEX:0, RESULT_STR: ''}, self.viewstub.get_on_apply_trans_chain_data())

    def test_add_all_transes(self):
        self.presenter.add_all_transes()

        expected = {CHAIN_TRANSES:[UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS],
                    AVAIL_SELECTED_INDEX:0,
                    CHAIN_SELECTED_INDEX:2}
        self.assertEquals(expected, self.viewstub.get_on_add_all_transes_data())


from view import View

class ViewStub(View):
    def __init__(self):
        self.remove_trans_data = None
        self.on_apply_trans_chain_data = None

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
    def collect_remove_trans_data(self):
        return self.remove_trans_data

    def set_remove_trans_data(self, data):
        self.remove_trans_data = data

    # Override
    def on_remove_trans(self, data):
        self.on_remove_trans_data = data

    def get_on_remove_trans_data(self):
        return self.on_remove_trans_data

    # Override
    def on_remove_all_transes(self, data):
        self.on_remove_all_transes_data = data

    def get_on_remove_all_transes_data(self):
        return self.on_remove_all_transes_data

    # Override
    def collect_apply_trans_chain_data(self):
        return self.apply_trans_chain_data

    def set_apply_trans_chain_data(self, data):
        self.apply_trans_chain_data = data

    # Override
    def on_apply_trans_chain(self, data):
        self.on_apply_trans_chain_data = data

    def get_on_apply_trans_chain_data(self):
        return self.on_apply_trans_chain_data

    # Override
    def on_add_all_transes(self, data):
        self.on_add_all_transes_data = data

    def get_on_add_all_transes_data(self):
        return self.on_add_all_transes_data

    # Override
    def on_validating_failed(self, data):
        self.on_validating_failed_data = data

    def get_on_validating_failed_data(self):
        return self.on_validating_failed_data


from businesslogic import BusinessLogic

class BusinessLogicStub(BusinessLogic):
    def get_all_transes(self):
        return [UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS]

    def transform(self, source_str, transes):
        return 'HELLO, WORLD.'