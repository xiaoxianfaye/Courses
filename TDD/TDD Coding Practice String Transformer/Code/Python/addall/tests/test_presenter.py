import unittest

from presenter import Presenter
from view import View
from businesslogic import BusinessLogic

class TestPresenter(unittest.TestCase):
    def setUp(self):
        self.viewstub = ViewStub()
        self.businesslogicstub = BusinessLogicStub()
        self.presenter = Presenter(self.viewstub, self.businesslogicstub)

        self.presenter.init()

    def test_init(self):
        self.assertEquals(['Upper', 'Lower', 'TrimPrefixSpaces'], self.viewstub.get_available_transids())
        self.assertEquals(0, self.viewstub.get_selected_available_transid_idx())

    def test_add_a_transformer(self):
        self.viewstub.set_selected_available_transid('Upper')

        self.presenter.add_transformer()

        self.assertEquals(['Upper'], self.viewstub.get_chain_transids())
        self.assertEquals(0, self.viewstub.get_selected_chain_transid_idx())
        self.assertEquals(-1, self.viewstub.get_selected_available_transid_idx())

    def test_remove_a_transformer(self):
        self.viewstub.set_selected_available_transid('Upper')
        self.presenter.add_transformer()
        self.viewstub.set_selected_chain_transid('Upper')

        self.presenter.remove_transformer()

        self.assertEquals([], self.viewstub.get_chain_transids())
        self.assertEquals(-1, self.viewstub.get_selected_chain_transid_idx())
        self.assertEquals(0, self.viewstub.get_selected_available_transid_idx())

    def test_apply_transformer_chain(self):
        self.viewstub.set_selected_available_transid('Upper')
        self.presenter.add_transformer()
        self.viewstub.set_sourcestr('Hello, world!')

        self.presenter.apply_transformer_chain()

        self.assertEquals('HELLO, WORLD!', self.viewstub.get_resultstr())

    def test_remove_all_transformers(self):
        self.viewstub.set_selected_available_transid('Upper')
        self.presenter.add_transformer()
        self.viewstub.set_selected_available_transid('Lower')
        self.presenter.add_transformer()

        self.presenter.remove_all_transformers()

        self.assertEquals([], self.viewstub.get_chain_transids())
        self.assertEquals(-1, self.viewstub.get_selected_chain_transid_idx())
        self.assertEquals(0, self.viewstub.get_selected_available_transid_idx())

    def test_add_two_same_transformers(self):
        self.viewstub.set_selected_available_transid('Upper')
        self.presenter.add_transformer()
        self.viewstub.set_selected_available_transid('Upper')

        self.presenter.add_transformer()

        self.assertEquals(['Upper'], self.viewstub.get_chain_transids())

    def test_apply_transformer_chain_when_source_string_is_empty(self):
        self.viewstub.set_sourcestr('')

        self.presenter.apply_transformer_chain()

        self.assertTrue(self.viewstub.is_empty_sourcestr_input_notified())
        self.assertIsNone(self.viewstub.get_resultstr())

    def test_apply_transformer_chain_when_chain_is_empty(self):
        self.viewstub.set_sourcestr('Hello, world!')

        self.presenter.apply_transformer_chain()

        self.assertTrue(self.viewstub.is_empty_chain_input_notified())
        self.assertIsNone(self.viewstub.get_resultstr())

    def test_remove_when_transformer_chain_is_empty(self):
        self.presenter.remove_transformer()

        self.assertEquals([], self.viewstub.get_chain_transids())
        self.assertEquals(-1, self.viewstub.get_selected_chain_transid_idx())
        self.assertEquals(0, self.viewstub.get_selected_available_transid_idx())

    def test_add_all_transformers_when_chain_is_empty(self):
        self.presenter.add_all_transformers()

        self.assertEquals(['Upper', 'Lower', 'TrimPrefixSpaces'], self.viewstub.get_chain_transids())
        self.assertEquals(2, self.viewstub.get_selected_chain_transid_idx())
        self.assertEquals(-1, self.viewstub.get_selected_available_transid_idx())

    def test_add_all_transformers_when_chain_is_not_empty(self):
        self.viewstub.set_selected_available_transid('Upper')
        self.presenter.add_transformer()

        self.presenter.add_all_transformers()

        self.assertEquals(['Upper', 'Lower', 'TrimPrefixSpaces'], self.viewstub.get_chain_transids())
        self.assertEquals(2, self.viewstub.get_selected_chain_transid_idx())
        self.assertEquals(-1, self.viewstub.get_selected_available_transid_idx())

class ViewStub(View):
    def __init__(self):
        self.available_transids = None
        self.selected_available_transid_idx = None
        self.selected_available_transid = None
        self.chain_transids = None
        self.selected_chain_transid_idx = None
        self.selected_chain_transid = None
        self.sourcestr = None
        self.resultstr = None
        self._is_empty_sourcestr_input_notified = None
        self._is_empty_chain_input_notified = None

    # Override
    def present_available_transids(self, transids, selected_transid_idx):
        self.available_transids = transids
        self.selected_available_transid_idx = selected_transid_idx

    def get_available_transids(self):
        return self.available_transids

    def get_selected_available_transid_idx(self):
        return self.selected_available_transid_idx

    # Override
    def get_selected_available_transid(self):
        return self.selected_available_transid

    def set_selected_available_transid(self, transid):
        self.selected_available_transid = transid

    # Override
    def present_chain_transids(self, transids,
                               selected_chain_transid_idx, selected_available_transid_idx):
        self.chain_transids = transids
        self.selected_chain_transid_idx = selected_chain_transid_idx
        self.selected_available_transid_idx = selected_available_transid_idx

    def get_chain_transids(self):
        return self.chain_transids

    def get_selected_chain_transid_idx(self):
        return self.selected_chain_transid_idx

    # Override
    def get_selected_chain_transid(self):
        return self.selected_chain_transid

    def set_selected_chain_transid(self, transid):
        self.selected_chain_transid = transid

    # Override
    def get_sourcestr(self):
        return self.sourcestr

    def set_sourcestr(self, s):
        self.sourcestr = s

    # Override
    def present_resultstr(self, s):
        self.resultstr = s

    def get_resultstr(self):
        return self.resultstr

    # Override
    def on_empty_sourcestr_input(self):
        self._is_empty_sourcestr_input_notified = True

    def is_empty_sourcestr_input_notified(self):
        return self._is_empty_sourcestr_input_notified

    # Override
    def on_empty_chain_input(self):
        self._is_empty_chain_input_notified = True

    def is_empty_chain_input_notified(self):
        return self._is_empty_chain_input_notified

    # Override
    def set_presenter(self, presenter): pass

class BusinessLogicStub(BusinessLogic):
    def get_all_transids(self):
        return ['Upper', 'Lower', 'TrimPrefixSpaces']

    def transform(self, sourcestr, transids):
        return 'HELLO, WORLD!'