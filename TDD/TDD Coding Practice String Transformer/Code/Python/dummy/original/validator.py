class ValidatingResult(object):
    VRFR_AVAIL_TRANS_NOT_SPECIFIED = 'validating_result_failed_reason_avail_trans_not_specified'
    VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS = 'validating_result_failed_reason_add_already_existed_in_chain_trans'
    VRFR_CHAIN_TRANS_NOT_SPECIFIED = 'validating_result_failed_reason_chain_trans_not_specified'
    VRFR_CHAIN_EMPTY = 'validating_result_failed_reason_chain_empty'

    def __init__(self, is_succeeded, failed_reason=None):
        self.is_succeeded = is_succeeded
        self.failed_reason = failed_reason

    @staticmethod
    def succeeded_result():
        return ValidatingResult(True)

    @staticmethod
    def failed_result(failed_reason):
        return ValidatingResult(False, failed_reason)


class ParamValidatingRule(object):
    def __init__(self, param, failed_pred, failed_action, failed_reason):
        self.param = param
        self.failed_pred = failed_pred
        self.failed_action = failed_action
        self.failed_reason = failed_reason

class Validator(object):
    @staticmethod
    def validate_param(param_validating_rule):
        if param_validating_rule.failed_pred(param_validating_rule.param):
            param_validating_rule.failed_action()
            return ValidatingResult.failed_result(param_validating_rule.failed_reason)

        return ValidatingResult.succeeded_result()

    @staticmethod
    def validate(param_validating_rules):
        for rule in param_validating_rules:
            validating_result = Validator.validate_param(rule)
            if not validating_result.is_succeeded:
                return validating_result

        return ValidatingResult.succeeded_result()
