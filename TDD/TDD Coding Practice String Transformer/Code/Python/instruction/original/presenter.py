from interaction import *
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
        self.view.on_init({AVAIL_TRANSES:self.avail_transes,
                           AVAIL_SELECTED_INDEX:self.avail_selected_index})

    def add_trans(self):
        avail_selected_trans = self.view.collect_add_trans_data()[AVAIL_SELECTED_TRANS]

        validating_result = self.validate(self.build_param_validating_rules_for_add(avail_selected_trans))
        if validating_result.is_succeeded:
            self.chain_transes.append(avail_selected_trans)

        self.update_chain_selected_index_for_add(avail_selected_trans)
        self.update_avail_selected_index_for_add(avail_selected_trans, validating_result.failed_reason)

        self.view.on_add_trans({CHAIN_TRANSES:self.chain_transes,
                                CHAIN_SELECTED_INDEX:self.chain_selected_index,
                                AVAIL_SELECTED_INDEX:self.avail_selected_index})

    def build_param_validating_rules_for_add(self, avail_selected_trans):
        return [ParamValidatingRule(avail_selected_trans,
                                    self.already_existed_in_chain,
                                    ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS)]

    def update_chain_selected_index_for_add(self, avail_selected_trans):
        self.chain_selected_index = self.chain_transes.index(avail_selected_trans)

    def update_avail_selected_index_for_add(self, avail_selected_trans, validating_result_failed_reason):
        selected_index = self.avail_transes.index(avail_selected_trans)
        if validating_result_failed_reason == ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS:
            self.avail_selected_index = selected_index
        else:
            self.avail_selected_index = \
                0 if Presenter.is_last_index(selected_index, self.avail_transes) else selected_index + 1

    def validate(self, param_validating_rules):
        validating_result = Validator.validate(param_validating_rules)
        if not validating_result.is_succeeded:
            self.view.on_validating_failed(validating_result.failed_reason)
        return validating_result

    def already_existed_in_chain(self, trans):
        return trans in self.chain_transes

    @staticmethod
    def is_last_index(index, lst):
        return index == len(lst) - 1