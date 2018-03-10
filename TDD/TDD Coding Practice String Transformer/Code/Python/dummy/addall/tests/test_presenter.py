# -*- coding: UTF-8 -*-

import unittest

from presenter import Presenter

from trans import *

class TestPresenter(unittest.TestCase):
    def setUp(self):
        self.viewstub = ViewStub()
        self.businesslogicstub = BusinessLogicStub()
        self.presenter = Presenter(self.viewstub, self.businesslogicstub)

        self.presenter.init()

    def test_init(self):
        self.assertEquals([UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS], self.viewstub.get_avail_transes())
        self.assertEquals(0, self.viewstub.get_avail_selected_index())

    def test_add_not_the_last_trans(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)

        self.presenter.add_trans()

        self.assertEquals([UPPER_TRANS], self.viewstub.get_chain_transes())
        self.assertEquals(0, self.viewstub.get_chain_selected_index())
        self.assertEquals(1, self.viewstub.get_avail_selected_index())

    def test_add_the_last_trans(self):
        self.viewstub.set_avail_selected_trans(TRIM_PREFIX_SPACES_TRANS)

        self.presenter.add_trans()

        self.assertEquals([TRIM_PREFIX_SPACES_TRANS], self.viewstub.get_chain_transes())
        self.assertEquals(0, self.viewstub.get_chain_selected_index())
        self.assertEquals(0, self.viewstub.get_avail_selected_index())

    def test_add_already_existed_in_chain_trans(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)

        self.presenter.add_trans()

        self.assertTrue(self.viewstub.is_add_already_existed_in_chain_trans_notified())
        self.assertEquals([UPPER_TRANS, LOWER_TRANS], self.viewstub.get_chain_transes())
        self.assertEquals(1, self.viewstub.get_chain_selected_index())
        self.assertEquals(1, self.viewstub.get_avail_selected_index())

    def test_add_trans_but_avail_trans_not_specified(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(None)

        self.presenter.add_trans()

        self.assertTrue(self.viewstub.is_avail_trans_not_specified_notified())
        self.assertEquals([UPPER_TRANS, LOWER_TRANS], self.viewstub.get_chain_transes())
        self.assertEquals(1, self.viewstub.get_chain_selected_index())
        self.assertEquals(0, self.viewstub.get_avail_selected_index())

    def test_remove_not_the_last_trans_when_chain_has_more_than_one_transes(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(TRIM_PREFIX_SPACES_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(LOWER_TRANS)

        self.presenter.remove_trans()

        self.assertEquals([UPPER_TRANS, TRIM_PREFIX_SPACES_TRANS], self.viewstub.get_chain_transes())
        self.assertEquals(1, self.viewstub.get_avail_selected_index())
        self.assertEquals(1, self.viewstub.get_chain_selected_index())

    def test_remove_the_last_trans_when_chain_has_more_than_one_transes(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(TRIM_PREFIX_SPACES_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(TRIM_PREFIX_SPACES_TRANS)

        self.presenter.remove_trans()

        self.assertEquals([UPPER_TRANS, LOWER_TRANS], self.viewstub.get_chain_transes())
        self.assertEquals(2, self.viewstub.get_avail_selected_index())
        self.assertEquals(0, self.viewstub.get_chain_selected_index())

    def test_remove_a_trans_when_chain_has_only_one_transes(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(UPPER_TRANS)

        self.presenter.remove_trans()

        self.assertEquals([], self.viewstub.get_chain_transes())
        self.assertEquals(0, self.viewstub.get_avail_selected_index())
        self.assertEquals(NONE_SELECTED_INDEX, self.viewstub.get_chain_selected_index())

    def test_remove_trans_when_chain_is_not_empty_but_chain_trans_not_specified(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(None)

        self.presenter.remove_trans()

        self.assertTrue(self.viewstub.is_chain_trans_not_specified_notified())
        self.assertEquals([UPPER_TRANS], self.viewstub.get_chain_transes())
        self.assertEquals(1, self.viewstub.get_avail_selected_index())
        self.assertEquals(0, self.viewstub.get_chain_selected_index())

    def test_remove_trans_when_chain_is_empty(self):
        self.presenter.remove_trans()

        self.assertTrue(self.viewstub.is_chain_empty_notified())
        self.assertEquals([], self.viewstub.get_chain_transes())
        self.assertEquals(0, self.viewstub.get_avail_selected_index())
        self.assertEquals(NONE_SELECTED_INDEX, self.viewstub.get_chain_selected_index())

    def test_remove_all_transes_when_chain_is_not_empty(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()

        self.presenter.remove_all_transes()

        self.assertEquals([], self.viewstub.get_chain_transes())
        self.assertEquals(0, self.viewstub.get_avail_selected_index())
        self.assertEquals(NONE_SELECTED_INDEX, self.viewstub.get_chain_selected_index())

    def test_remove_all_transes_when_chain_is_empty(self):
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(LOWER_TRANS)
        self.presenter.remove_trans()

        self.presenter.remove_all_transes()

        self.assertTrue(self.viewstub.is_chain_empty_notified())
        self.assertEquals([], self.viewstub.get_chain_transes())
        self.assertEquals(1, self.viewstub.get_avail_selected_index())
        self.assertEquals(NONE_SELECTED_INDEX, self.viewstub.get_chain_selected_index())

    def test_apply_trans_chain(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_source_str('Hello, world.')

        self.presenter.apply_trans_chain()

        self.assertEquals('HELLO, WORLD.', self.viewstub.get_result_str())

    def test_apply_trans_chain_when_source_str_is_empty(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_source_str('')

        self.presenter.apply_trans_chain()

        self.assertTrue(self.viewstub.is_source_str_empty_notified())
        self.assertIsNone(self.viewstub.get_result_str())

    def test_apply_trans_chain_when_source_str_is_illegal(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_source_str('a中文b')

        self.presenter.apply_trans_chain()

        self.assertTrue(self.viewstub.is_source_str_illegal_notified())
        self.assertIsNone(self.viewstub.get_result_str())

    def test_apply_trans_chain_when_chain_is_empty(self):
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(LOWER_TRANS)
        self.presenter.remove_trans()
        self.viewstub.set_source_str('Hello, world.')

        self.presenter.apply_trans_chain()

        self.assertTrue(self.viewstub.is_chain_empty_notified())
        self.assertIsNone(self.viewstub.get_result_str())
        self.assertEquals(0, self.viewstub.get_avail_selected_index())

    def test_add_all_transes(self):
        self.presenter.add_all_transes()

        self.assertEquals([UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS], self.viewstub.get_chain_transes())
        self.assertEquals(0, self.viewstub.get_avail_selected_index())
        self.assertEquals(2, self.viewstub.get_chain_selected_index())


from view import View

class ViewStub(View):
    def __init__(self):
        self.chain_selected_trans = None
        self.result_str = None

    # Override
    def set_presenter(self, presenter): pass

    # Override
    def present_avail_transes(self, transes):
        self.avail_transes = transes

    def get_avail_transes(self):
        return self.avail_transes

    # Override
    def set_avail_selected_index(self, index):
        self.avail_selected_index = index

    def get_avail_selected_index(self):
        return self.avail_selected_index

    # Override
    def get_avail_selected_trans(self):
        return self.avail_selected_trans

    def set_avail_selected_trans(self, trans):
        self.avail_selected_trans = trans

    # Override
    def present_chain_transes(self, transes):
        self.chain_transes = transes

    def get_chain_transes(self):
        return self.chain_transes

    # Override
    def set_chain_selected_index(self, index):
        self.chain_selected_index = index

    def get_chain_selected_index(self):
        return self.chain_selected_index

    # Override
    def notify_add_already_existed_in_chain_trans(self):
        self.add_already_existed_in_chain_trans_notified = True

    def is_add_already_existed_in_chain_trans_notified(self):
        return self.add_already_existed_in_chain_trans_notified

    # Override
    def notify_avail_trans_not_specified(self):
        self.avail_trans_not_specified_notified = True

    def is_avail_trans_not_specified_notified(self):
        return self.avail_trans_not_specified_notified

    # Override
    def get_chain_selected_trans(self):
        return self.chain_selected_trans

    def set_chain_selected_trans(self, trans):
        self.chain_selected_trans = trans

    # Override
    def notify_chain_trans_not_specified(self):
        self.chain_trans_not_specified_notified = True

    def is_chain_trans_not_specified_notified(self):
        return self.chain_trans_not_specified_notified

    # Override
    def notify_chain_empty(self):
        self.chain_empty_notified = True

    def is_chain_empty_notified(self):
        return self.chain_empty_notified

    # Override
    def get_source_str(self):
        return self.source_str

    def set_source_str(self, s):
        self.source_str = s

    # Override
    def present_result_str(self, s):
        self.result_str = s

    def get_result_str(self):
        return self.result_str

    # Override
    def notify_source_str_empty(self):
        self.source_str_empty_notified = True

    def is_source_str_empty_notified(self):
        return self.source_str_empty_notified

    # Override
    def notify_source_str_illegal(self):
        self.source_str_illegal_notified = True

    def is_source_str_illegal_notified(self):
        return self.source_str_illegal_notified


from businesslogic import BusinessLogic

class BusinessLogicStub(BusinessLogic):
    def get_all_transes(self):
        return [UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS]

    def transform(self, source_str, transes):
        return 'HELLO, WORLD.'