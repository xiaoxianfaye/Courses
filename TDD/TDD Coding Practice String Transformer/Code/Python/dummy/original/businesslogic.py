class BusinessLogic(object):
    def get_all_transes(self): pass

    def transform(self, source_str, transes): pass


from trans import *

class BusinessLogicImpl(BusinessLogic):
    # Override
    def get_all_transes(self):
        return [UPPER_TRANS, LOWER_TRANS, TRIM_PREFIX_SPACES_TRANS]

    # Override
    def transform(self, source_str, transes):
        return source_str.upper()
