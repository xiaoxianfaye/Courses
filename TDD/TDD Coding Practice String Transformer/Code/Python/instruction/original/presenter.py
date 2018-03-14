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
        self.result_str = None

    def init(self):
        self.avail_transes = self.businesslogic.get_all_transes()
        self.view.on_init({AVAIL_TRANSES:self.avail_transes,
                           AVAIL_SELECTED_INDEX:self.avail_selected_index})

    def add_trans(self):
        avail_selected_trans = self.view.collect_add_trans_data()[AVAIL_SELECTED_TRANS]

        validating_result = self.validate(self.build_param_validating_rules_for_add(avail_selected_trans))
        if validating_result.is_succeeded:
            self.chain_transes.append(avail_selected_trans)

        self.update_chain_selected_index_for_add(avail_selected_trans, validating_result.failed_reason)
        self.update_avail_selected_index_for_add(avail_selected_trans, validating_result.failed_reason)

        self.view.on_add_trans({CHAIN_TRANSES:self.chain_transes,
                                CHAIN_SELECTED_INDEX:self.chain_selected_index,
                                AVAIL_SELECTED_INDEX:self.avail_selected_index})

    def build_param_validating_rules_for_add(self, avail_selected_trans):
        return [ParamValidatingRule(avail_selected_trans,
                                    Presenter.trans_not_specified,
                                    ValidatingResult.VRFR_AVAIL_TRANS_NOT_SPECIFIED),
                ParamValidatingRule(avail_selected_trans,
                                    self.already_existed_in_chain,
                                    ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS)]

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

    def remove_trans(self):
        remove_trans_data = self.view.collect_remove_trans_data()
        chain_selected_trans = remove_trans_data[CHAIN_SELECTED_TRANS] if remove_trans_data else None

        validating_result = self.validate(self.build_param_validating_rules_for_remove(chain_selected_trans))

        self.update_chain_selected_index_for_remove(chain_selected_trans, validating_result.failed_reason)
        if validating_result.is_succeeded:
            self.chain_transes.remove(chain_selected_trans)
        self.update_avail_selected_index_for_remove(chain_selected_trans, validating_result.failed_reason)

        self.view.on_remove_trans({CHAIN_TRANSES:self.chain_transes,
                                   AVAIL_SELECTED_INDEX:self.avail_selected_index,
                                   CHAIN_SELECTED_INDEX:self.chain_selected_index})

    def build_param_validating_rules_for_remove(self, chain_selected_trans):
        return [ParamValidatingRule(self.chain_transes,
                                    Presenter.empty_list,
                                    ValidatingResult.VRFR_CHAIN_EMPTY),
                ParamValidatingRule(chain_selected_trans,
                                    Presenter.trans_not_specified,
                                    ValidatingResult.VRFR_CHAIN_TRANS_NOT_SPECIFIED)]

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

    def remove_all_transes(self):
        validating_result = self.validate(self.build_param_validating_rules_for_remove_all())
        if validating_result.is_succeeded:
            del self.chain_transes[:]
        self.update_chain_selected_index_for_remove_all()
        self.update_avail_selected_index_for_remove_all(validating_result.failed_reason)

        self.view.on_remove_all_transes({CHAIN_TRANSES:self.chain_transes,
                                         CHAIN_SELECTED_INDEX:self.chain_selected_index,
                                         AVAIL_SELECTED_INDEX:self.avail_selected_index})

    def build_param_validating_rules_for_remove_all(self):
        return [ParamValidatingRule(self.chain_transes,
                                    Presenter.empty_list,
                                    ValidatingResult.VRFR_CHAIN_EMPTY)]

    def update_chain_selected_index_for_remove_all(self):
        self.chain_selected_index = NONE_SELECTED_INDEX

    def update_avail_selected_index_for_remove_all(self, validating_result_failed_reason):
        if validating_result_failed_reason == ValidatingResult.VRFR_CHAIN_EMPTY:
            return

        self.avail_selected_index = 0

    def apply_trans_chain(self):
        source_str = self.view.collect_apply_trans_chain_data()[SOURCE_STR]

        self.result_str = self.businesslogic.transform(source_str, self.chain_transes)

        self.view.on_apply_trans_chain({RESULT_STR:self.result_str})

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

    @staticmethod
    def trans_not_specified(trans):
        return trans == None

    @staticmethod
    def empty_list(lst):
        return lst == []