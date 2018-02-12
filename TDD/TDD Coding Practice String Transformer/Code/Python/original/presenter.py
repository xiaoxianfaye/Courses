class Presenter(object):
    def __init__(self, view, businesslogic):
        self.view = view
        self.businesslogic = businesslogic
        self.view.set_presenter(self)

        self.available_transformers = None
        self.chain_transformers = []

    def init(self):
        self.available_transformers = self.businesslogic.get_all_transformers()
        self.view.on_init({ParamEnum.AVAILABLE_TRANSNAMES:TransEnum.names(self.available_transformers),
                           ParamEnum.AVAILABLE_SELECTED_INDEX:0})

    # def add_transformer(self):
    #     add_data = self.view.collect_add_data()
    #     available_selected_transformer = TE.transformer(add_data.get_available_selected_transname())
    #     self.chain_transformers.append(available_selected_transformer)
    #
    #     chain_transnames = TE.names(self.chain_transformers);
    #     chain_selected_Index = self.chain_transformers.index(available_selected_transformer)
    #     available_selected_index = self.available_transformers.index(available_selected_transformer) + 1;
    #     self.view.on_add_transformer(OnAddData(chain_transnames, chain_selected_Index, available_selected_index))

        # selected_available_transid = self.view.get_selected_available_transid()
        # if self.not_exist_in_chain(selected_available_transid):
        #     self.chain_transids.append(selected_available_transid)
        # self.view.present_chain_transids(self.chain_transids,
        #                                  self.chain_transids.index(selected_available_transid), -1)

    # def remove_transformer(self):
    #     if self.chain_transids == []:
    #         self.view.present_chain_transids(self.chain_transids, -1, 0)
    #         return
    #
    #     selected_chain_transid = self.view.get_selected_chain_transid()
    #     self.chain_transids.remove(selected_chain_transid)
    #     self.view.present_chain_transids(self.chain_transids,
    #                                      -1, self.available_transids.index(selected_chain_transid))
    #
    # def apply_transformer_chain(self):
    #     sourcestr = self.view.get_sourcestr()
    #
    #     if not self.check_input(sourcestr, self.chain_transids):
    #         return
    #
    #     self.view.present_resultstr(
    #         self.businesslogic.transform(sourcestr, self.chain_transids))
    #
    # def remove_all_transformers(self):
    #     del self.chain_transids[:]
    #     self.view.present_chain_transids(self.chain_transids, -1, 0)
    #
    # def not_exist_in_chain(self, transid):
    #     return transid not in self.chain_transids
    #
    # def check_input(self, sourcestr, _chain_transids):
    #     paramrules = [
    #         (sourcestr, lambda s: s == '', self.view.on_empty_sourcestr_input),
    #         (_chain_transids, lambda lst: lst == [], self.view.on_empty_chain_input)
    #     ]
    #
    #     def check_param(param, predication, action):
    #         if predication(param):
    #             action()
    #             return False
    #         return True
    #
    #     for paramrule in paramrules:
    #         if not check_param(paramrule[0], paramrule[1], paramrule[2]):
    #             return False
    #
    #     return True


class Transformer(object):
    def __init__(self, name):
        self.name = name

    def getname(self):
        return self.name

class TransEnum(object):
    UPPER = Transformer('Upper')
    LOWER = Transformer('Lower')
    TRIM_PREFIX_SPACES = Transformer('TrimPrefixSpaces')

    @staticmethod
    def values():
        return [TransEnum.UPPER, TransEnum.LOWER, TransEnum.TRIM_PREFIX_SPACES]

    @staticmethod
    def names(transformers):
        return [transformer.getname() for transformer in transformers]

    @staticmethod
    def transformer(name):
        for transformer in TransEnum.values():
            if transformer.getname() == name:
                return transformer
        return None

class ParamEnum(object):
    AVAILABLE_TRANSNAMES = 'available_transnames'
    AVAILABLE_SELECTED_INDEX = 'available_selected_index'