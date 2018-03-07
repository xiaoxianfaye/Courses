class BusinessLogic(object):
    def get_all_transes(self): pass

    def transform(self, source_str, transes): pass


from trans import *

class BusinessLogicImpl(BusinessLogic):
    TRANS_FUNC_MAP = {
        UPPER_TRANS:lambda s: BusinessLogicImpl.upper(s),
        LOWER_TRANS:lambda s: BusinessLogicImpl.lower(s),
        TRIM_PREFIX_SPACES_TRANS:lambda s: BusinessLogicImpl.trim_prefix_spaces(s),
        REVERSE_TRANS:lambda s: BusinessLogicImpl.reverse(s)}

    # Override
    def get_all_transes(self):
        return BusinessLogicImpl.TRANS_FUNC_MAP.keys()

    # Override
    def transform(self, source_str, transes):
        def _acc_transform(acc, transid):
            return BusinessLogicImpl.TRANS_FUNC_MAP[transid](acc)

        return reduce(_acc_transform, transes, source_str)

    @staticmethod
    def upper(s):
        return s.upper()

    @staticmethod
    def lower(s):
        return s.lower()

    @staticmethod
    def trim_prefix_spaces(s):
        return s.lstrip()

    @staticmethod
    def reverse(s):
        return s[::-1]