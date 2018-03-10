from trans import NONE_SELECTED_INDEX
from validator import ValidatingResult, ParamValidatingRule, Validator

import re

class Presenter(object):
    def __init__(self, view, businesslogic):
        self.view = view
        self.businesslogic = businesslogic
        self.view.set_presenter(self)

        self.avail_selected_index = 0
        self.chain_selected_index = NONE_SELECTED_INDEX
        self.avail_transes = None
        self.chain_transes = []
        self.result_str = None

    def init(self):
        self.avail_transes = self.businesslogic.get_all_transes()
        self.view.present_avail_transes(self.avail_transes)
        self.view.set_avail_selected_index(self.avail_selected_index)

    def _oper_trans(self, oper_data):
        if oper_data.collect_view_data and oper_data.build_param_validating_rules:
            view_data = oper_data.collect_view_data()
            validating_result = Presenter.validate(oper_data.build_param_validating_rules(view_data))
            oper_data.update_presenter_data(view_data, validating_result)
        elif not oper_data.collect_view_data and oper_data.build_param_validating_rules:
            validating_result = Presenter.validate(oper_data.build_param_validating_rules())
            oper_data.update_presenter_data(validating_result)
        else:
            oper_data.update_presenter_data()

        oper_data.present_view_data()

    def add_trans(self):
        self._oper_trans(OperData(self.collect_view_data_for_add,
                                  self.build_param_validating_rules_for_add,
                                  self.update_presenter_data_for_add,
                                  self.present_view_data_for_add))

    def collect_view_data_for_add(self):
        return self.view.get_avail_selected_trans()

    def build_param_validating_rules_for_add(self, avail_selected_trans):
        return [ParamValidatingRule(avail_selected_trans,
                                    Presenter.trans_not_specified,
                                    self.view.notify_avail_trans_not_specified,
                                    ValidatingResult.VRFR_AVAIL_TRANS_NOT_SPECIFIED),
                ParamValidatingRule(avail_selected_trans,
                                    self.already_existed_in_chain,
                                    self.view.notify_add_already_existed_in_chain_trans,
                                    ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS)]

    def update_presenter_data_for_add(self, avail_selected_trans, validating_result):
        if validating_result.is_succeeded:
            self.chain_transes.append(avail_selected_trans)
        self.update_chain_selected_index_for_add(avail_selected_trans, validating_result.failed_reason)
        self.update_avail_selected_index_for_add(avail_selected_trans, validating_result.failed_reason)

    def update_chain_selected_index_for_add(self, avail_selected_trans, validating_result_failed_reason):
        if validating_result_failed_reason == ValidatingResult.VRFR_AVAIL_TRANS_NOT_SPECIFIED:
            return

        self.chain_selected_index = self.chain_transes.index(avail_selected_trans)

    def update_avail_selected_index_for_add(self, avail_selected_trans, validating_result_failed_reason):
        if validating_result_failed_reason == ValidatingResult.VRFR_AVAIL_TRANS_NOT_SPECIFIED:
            self.avail_selected_index = 0
            return

        selected_index = self.avail_transes.index(avail_selected_trans)
        if validating_result_failed_reason == ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS:
            self.avail_selected_index = selected_index
        else:
            self.avail_selected_index = \
                0 if Presenter.is_last_index(selected_index, self.avail_transes) else selected_index + 1

    def present_view_data_for_add(self):
        self.view.present_chain_transes(self.chain_transes)
        self.view.set_chain_selected_index(self.chain_selected_index)
        self.view.set_avail_selected_index(self.avail_selected_index)

    def remove_trans(self):
        self._oper_trans(OperData(self.collect_view_data_for_remove,
                                  self.build_param_validating_rules_for_remove,
                                  self.update_presenter_data_for_remove,
                                  self.present_view_data_for_remove))

    def collect_view_data_for_remove(self):
        return self.view.get_chain_selected_trans()

    def build_param_validating_rules_for_remove(self, chain_selected_trans):
        return [ParamValidatingRule(self.chain_transes,
                                    Presenter.empty_list,
                                    self.view.notify_chain_empty,
                                    ValidatingResult.VRFR_CHAIN_EMPTY),
                ParamValidatingRule(chain_selected_trans,
                                    Presenter.trans_not_specified,
                                    self.view.notify_chain_trans_not_specified,
                                    ValidatingResult.VRFR_CHAIN_TRANS_NOT_SPECIFIED)]

    def update_presenter_data_for_remove(self, chain_selected_trans, validating_result):
        self.update_chain_selected_index_for_remove(chain_selected_trans, validating_result.failed_reason)
        if validating_result.is_succeeded:
            self.chain_transes.remove(chain_selected_trans)
        self.update_avail_selected_index_for_remove(chain_selected_trans, validating_result.failed_reason)

    def update_chain_selected_index_for_remove(self, chain_selected_trans, validating_result_failed_reason):
        if validating_result_failed_reason == ValidatingResult.VRFR_CHAIN_EMPTY:
            self.chain_selected_index = NONE_SELECTED_INDEX
            return

        if validating_result_failed_reason == ValidatingResult.VRFR_CHAIN_TRANS_NOT_SPECIFIED:
            self.chain_selected_index = 0
            return

        if len(self.chain_transes) == 1:
            self.chain_selected_index = NONE_SELECTED_INDEX
            return

        selected_index = self.chain_transes.index(chain_selected_trans)
        self.chain_selected_index = \
            0 if Presenter.is_last_index(selected_index, self.chain_transes) else selected_index

    def update_avail_selected_index_for_remove(self, chain_selected_trans, validating_result_failed_reason):
        if validating_result_failed_reason == ValidatingResult.VRFR_CHAIN_EMPTY:
            self.avail_selected_index = 0
            return

        if validating_result_failed_reason == ValidatingResult.VRFR_CHAIN_TRANS_NOT_SPECIFIED:
            return

        self.avail_selected_index = self.avail_transes.index(chain_selected_trans)

    def present_view_data_for_remove(self):
        self.view.present_chain_transes(self.chain_transes)
        self.view.set_avail_selected_index(self.avail_selected_index)
        self.view.set_chain_selected_index(self.chain_selected_index)

    def remove_all_transes(self):
        self._oper_trans(OperData(None,
                                  self.build_param_validating_rules_for_remove_all,
                                  self.update_presenter_data_for_remove_all,
                                  self.present_view_data_for_remove_all))

    def build_param_validating_rules_for_remove_all(self):
        return [ParamValidatingRule(self.chain_transes,
                                    Presenter.empty_list,
                                    self.view.notify_chain_empty,
                                    ValidatingResult.VRFR_CHAIN_EMPTY)]

    def update_presenter_data_for_remove_all(self, validating_result):
        if validating_result.is_succeeded:
            self.clear_chain_transes()

        self.update_chain_selected_index_for_remove_all()
        self.update_avail_selected_index_for_remove_all(validating_result.failed_reason)

    def update_chain_selected_index_for_remove_all(self):
        self.chain_selected_index = NONE_SELECTED_INDEX

    def update_avail_selected_index_for_remove_all(self, validating_result_failed_reason):
        if validating_result_failed_reason == ValidatingResult.VRFR_CHAIN_EMPTY:
            return

        self.avail_selected_index = 0

    def present_view_data_for_remove_all(self):
        self.view.present_chain_transes(self.chain_transes)
        self.view.set_chain_selected_index(self.chain_selected_index)
        self.view.set_avail_selected_index(self.avail_selected_index)

    def apply_trans_chain(self):
        self._oper_trans(OperData(self.collect_view_data_for_apply,
                                  self.build_param_validating_rules_for_apply,
                                  self.update_presenter_data_for_apply,
                                  self.present_view_data_for_apply))

    def collect_view_data_for_apply(self):
        return self.view.get_source_str()

    def build_param_validating_rules_for_apply(self, source_str):
        return [ParamValidatingRule(source_str,
                                    Presenter.empty_str,
                                    self.view.notify_source_str_empty,
                                    ValidatingResult.VRFR_SOURCE_STR_EMPTY),
                ParamValidatingRule(source_str,
                                    Presenter.illegal_source_str,
                                    self.view.notify_source_str_illegal,
                                    ValidatingResult.VRFR_SOURCE_STR_ILLEGAL),
                ParamValidatingRule(self.chain_transes,
                                    Presenter.empty_list,
                                    self.view.notify_chain_empty,
                                    ValidatingResult.VRFR_CHAIN_EMPTY)]

    def update_presenter_data_for_apply(self, source_str, validating_result):
        if validating_result.is_succeeded:
            self.result_str = self.businesslogic.transform(source_str, self.chain_transes)

        self.update_avail_selected_index_for_apply(validating_result.failed_reason)

    def update_avail_selected_index_for_apply(self, validating_result_failed_reason):
        if validating_result_failed_reason == ValidatingResult.VRFR_CHAIN_EMPTY:
            self.avail_selected_index = 0

    def present_view_data_for_apply(self):
        self.view.present_result_str(self.result_str)
        self.view.set_avail_selected_index(self.avail_selected_index)

    def add_all_transes(self):
        self._oper_trans(OperData(None,
                                  None,
                                  self.update_presenter_data_for_add_all,
                                  self.present_view_data_for_add_all))

    def update_presenter_data_for_add_all(self):
        self.clear_chain_transes()
        self.chain_transes.extend(self.avail_transes)

        self.update_chain_selected_index_for_add_all()
        self.update_avail_selected_index_for_add_all()

    def update_chain_selected_index_for_add_all(self):
        self.chain_selected_index = len(self.chain_transes) - 1

    def update_avail_selected_index_for_add_all(self):
        self.avail_selected_index = 0

    def present_view_data_for_add_all(self):
        self.view.present_chain_transes(self.chain_transes)
        self.view.set_avail_selected_index(self.avail_selected_index)
        self.view.set_chain_selected_index(self.chain_selected_index)

    def already_existed_in_chain(self, trans):
        return trans in self.chain_transes

    def clear_chain_transes(self):
        del self.chain_transes[:]

    @staticmethod
    def is_last_index(index, lst):
        return index == len(lst) - 1

    @staticmethod
    def trans_not_specified(trans):
        return trans == None

    @staticmethod
    def empty_list(lst):
        return lst == []

    @staticmethod
    def empty_str(s):
        return s == ''

    @staticmethod
    def illegal_source_str(s):
        pattern = re.compile(u'[\u4e00-\u9fa5]+')
        return pattern.search(s.decode('utf-8')) is not None

    @staticmethod
    def validate(param_validating_rules):
        return Validator.validate(param_validating_rules)


class OperData(object):
    def __init__(self, collect_view_data, build_param_validating_rules,
                 update_presenter_data, present_view_data):
        self.collect_view_data = collect_view_data
        self.build_param_validating_rules = build_param_validating_rules
        self.update_presenter_data = update_presenter_data
        self.present_view_data = present_view_data