from trans import NONE_SELECTED_INDEX
from validator import ValidatingResult, ParamValidatingRule, Validator

class Presenter(object):
    def __init__(self, view, businesslogic):
        self.view = view
        self.businesslogic = businesslogic
        self.view.set_presenter(self)

        self.avail_selected_index = 0
        self.chain_selected_index = NONE_SELECTED_INDEX
        self.avail_transes = None
        self.chain_transes = []

    def init(self):
        self.avail_transes = self.businesslogic.get_all_transes()
        self.view.present_avail_transes(self.avail_transes)
        self.view.set_avail_selected_index(self.avail_selected_index)

    def add_trans(self):
        avail_selected_trans = self.view.get_avail_selected_trans()

        validating_result = self.validate_add_trans(avail_selected_trans)
        if validating_result.is_succeeded:
            self.chain_transes.append(avail_selected_trans)

        self.update_chain_selected_index_for_add(avail_selected_trans, validating_result.failed_reason)
        self.update_avail_selected_index_for_add(avail_selected_trans, validating_result.failed_reason)

        self.view.present_chain_transes(self.chain_transes)
        self.view.set_chain_selected_index(self.chain_selected_index)
        self.view.set_avail_selected_index(self.avail_selected_index)

    def remove_trans(self):
        chain_selected_trans = self.view.get_chain_selected_trans()

        validating_result = self.validate_remove_trans(chain_selected_trans)
        self.update_chain_selected_index_for_remove(chain_selected_trans, validating_result.failed_reason)
        if validating_result.is_succeeded:
            self.chain_transes.remove(chain_selected_trans)
        self.update_avail_selected_index_for_remove(chain_selected_trans, validating_result.failed_reason)

        self.view.present_chain_transes(self.chain_transes)
        self.view.set_avail_selected_index(self.avail_selected_index)
        self.view.set_chain_selected_index(self.chain_selected_index)

    def remove_all_transes(self):
        validating_result = self.validate_remove_all_transes()
        if validating_result.is_succeeded:
            del self.chain_transes[:]

        self.update_chain_selected_index_for_remove_all()
        self.update_avail_selected_index_for_remove_all(validating_result.failed_reason)

        self.view.present_chain_transes(self.chain_transes)
        self.view.set_chain_selected_index(self.chain_selected_index)
        self.view.set_avail_selected_index(self.avail_selected_index)

    def apply_trans_chain(self):
        source_str = self.view.get_source_str()

        validating_result = self.validate_apply_trans_chain(source_str)
        if validating_result.is_succeeded:
            self.view.present_result_str(self.businesslogic.transform(source_str, self.chain_transes))

    def validate_add_trans(self, avail_selected_trans):
        param_validating_rules = [ParamValidatingRule(avail_selected_trans,
                                                      Presenter.trans_not_specified,
                                                      self.view.notify_avail_trans_not_specified,
                                                      ValidatingResult.VRFR_AVAIL_TRANS_NOT_SPECIFIED),
                                  ParamValidatingRule(avail_selected_trans,
                                                      self.already_existed_in_chain,
                                                      self.view.notify_add_already_existed_in_chain_trans,
                                                      ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS)]
        return Validator.validate(param_validating_rules)

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

    def validate_remove_trans(self, chain_selected_trans):
        param_validating_rules = [ParamValidatingRule(self.chain_transes,
                                                      Presenter.empty_list,
                                                      self.view.notify_chain_empty,
                                                      ValidatingResult.VRFR_CHAIN_EMPTY),
                                  ParamValidatingRule(chain_selected_trans,
                                                      Presenter.trans_not_specified,
                                                      self.view.notify_chain_trans_not_specified,
                                                      ValidatingResult.VRFR_CHAIN_TRANS_NOT_SPECIFIED)]
        return Validator.validate(param_validating_rules)

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

    def validate_remove_all_transes(self):
        param_validating_rules = [ParamValidatingRule(self.chain_transes,
                                                      Presenter.empty_list,
                                                      self.view.notify_chain_empty,
                                                      ValidatingResult.VRFR_CHAIN_EMPTY)]
        return Validator.validate(param_validating_rules)

    def update_chain_selected_index_for_remove_all(self):
        self.chain_selected_index = NONE_SELECTED_INDEX

    def update_avail_selected_index_for_remove_all(self, validating_result_failed_reason):
        if validating_result_failed_reason == ValidatingResult.VRFR_CHAIN_EMPTY:
            return

        self.avail_selected_index = 0

    def validate_apply_trans_chain(self, source_str):
        param_validating_rules = [ParamValidatingRule(source_str,
                                                      Presenter.empty_str,
                                                      self.view.notify_source_str_empty,
                                                      ValidatingResult.VRFR_CHAIN_EMPTY)]
        return Validator.validate(param_validating_rules)

    def already_existed_in_chain(self, trans):
        return trans in self.chain_transes

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