class BusinessLogic(object):
    def get_all_transIds(self): pass

    def transform(self, sourcestr, transids): pass

class BusinessLogicImpl(BusinessLogic):
    transid_func_map = {
        'Upper':lambda s: BusinessLogicImpl.upper(s),
        'Lower':lambda s: BusinessLogicImpl.lower(s),
        'TrimPrefixSpaces':lambda s: BusinessLogicImpl.trim_prefix_spaces(s),
        'Reverse':lambda s: BusinessLogicImpl.reverse(s)
    }

    def transform(self, sourcestr, transids):
        def _acc_transform(acc, transid):
            return BusinessLogicImpl.transid_func_map[transid](acc)

        return reduce(_acc_transform, transids, sourcestr)

    def get_all_transids(self):
        return BusinessLogicImpl.transid_func_map.keys()

    @staticmethod
    def upper(s):
        return s.upper()

    @staticmethod
    def lower(s):
        return s.lower()

    @staticmethod
    def trim_prefix_spaces(s):
        first_nonspacechar_idx = BusinessLogicImpl.find_first_nonspace_char_idx(s)
        return s[first_nonspacechar_idx:] if (first_nonspacechar_idx is not None) else ''

    @staticmethod
    def find_first_nonspace_char_idx(s):
        first_nonspacechar_idx = None
        for idx, c in enumerate(s):
            if not c is ' ':
                first_nonspacechar_idx = idx
                break
        return first_nonspacechar_idx

    @staticmethod
    def reverse(s):
        return s[::-1]