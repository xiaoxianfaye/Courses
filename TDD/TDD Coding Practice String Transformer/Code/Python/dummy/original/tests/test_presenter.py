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

        self.assertEqual([UPPER_TRANS], self.viewstub.get_chain_transes())
        self.assertEqual(0, self.viewstub.get_chain_selected_index())
        self.assertEqual(1, self.viewstub.get_avail_selected_index())

    def test_add_the_last_trans(self):
        self.viewstub.set_avail_selected_trans(TRIM_PREFIX_SPACES_TRANS)

        self.presenter.add_trans()

        self.assertEqual([TRIM_PREFIX_SPACES_TRANS], self.viewstub.get_chain_transes())
        self.assertEqual(0, self.viewstub.get_chain_selected_index())
        self.assertEqual(0, self.viewstub.get_avail_selected_index())

    def test_add_already_existed_in_chain_trans(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)

        self.presenter.add_trans()

        self.assertTrue(self.viewstub.is_add_already_existed_in_chain_trans_notified())
        self.assertEqual([UPPER_TRANS, LOWER_TRANS], self.viewstub.get_chain_transes())
        self.assertEqual(1, self.viewstub.get_chain_selected_index())
        self.assertEqual(1, self.viewstub.get_avail_selected_index())

    def test_add_trans_but_avail_trans_not_specified(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(None)

        self.presenter.add_trans()

        self.assertTrue(self.viewstub.is_avail_trans_not_specified_notified())
        self.assertEqual([UPPER_TRANS, LOWER_TRANS], self.viewstub.get_chain_transes())
        self.assertEqual(1, self.viewstub.get_chain_selected_index())
        self.assertEqual(0, self.viewstub.get_avail_selected_index())

    def test_remove_not_the_last_trans_when_chain_has_more_than_one_transes(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(TRIM_PREFIX_SPACES_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(LOWER_TRANS)

        self.presenter.remove_trans()

        self.assertEqual([UPPER_TRANS, TRIM_PREFIX_SPACES_TRANS], self.viewstub.get_chain_transes())
        self.assertEqual(1, self.viewstub.get_avail_selected_index())
        self.assertEqual(1, self.viewstub.get_chain_selected_index())

    def test_remove_the_last_trans_when_chain_has_more_than_one_transes(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(LOWER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_avail_selected_trans(TRIM_PREFIX_SPACES_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(TRIM_PREFIX_SPACES_TRANS)

        self.presenter.remove_trans()

        self.assertEqual([UPPER_TRANS, LOWER_TRANS], self.viewstub.get_chain_transes())
        self.assertEqual(2, self.viewstub.get_avail_selected_index())
        self.assertEqual(0, self.viewstub.get_chain_selected_index())

    def test_remove_a_trans_when_chain_has_only_one_transes(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(UPPER_TRANS)

        self.presenter.remove_trans()

        self.assertEqual([], self.viewstub.get_chain_transes())
        self.assertEqual(0, self.viewstub.get_avail_selected_index())
        self.assertEqual(NONE_SELECTED_INDEX, self.viewstub.get_chain_selected_index())

    def test_remove_trans_when_chain_is_not_empty_but_chain_trans_not_specified(self):
        self.viewstub.set_avail_selected_trans(UPPER_TRANS)
        self.presenter.add_trans()
        self.viewstub.set_chain_selected_trans(None)

        self.presenter.remove_trans()

        self.assertTrue(self.viewstub.is_chain_trans_not_specified_notified())
        self.assertEqual([UPPER_TRANS], self.viewstub.get_chain_transes())
        self.assertEqual(1, self.viewstub.get_avail_selected_index())
        self.assertEqual(0, self.viewstub.get_chain_selected_index())

    def test_remove_trans_when_chain_is_empty(self):
        self.presenter.remove_trans()

        self.assertTrue(self.viewstub.is_chain_empty_notified())
        self.assertEqual([], self.viewstub.get_chain_transes())
        self.assertEqual(0, self.viewstub.get_avail_selected_index())
        self.assertEqual(NONE_SELECTED_INDEX, self.viewstub.get_chain_selected_index())


from view import View

class ViewStub(View):
    def __init__(self):
        self.chain_selected_trans = None

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


from businesslogic import BusinessLogic

class BusinessLogicStub(BusinessLogic):
    def get_all_transes(self):
        return [UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS]