class Presenter(object):
    def __init__(self, view, businesslogic):
        self.view = view
        self.businesslogic = businesslogic
        self.view.set_presenter(self)

        self.chain_transids = []
        self.avail_transids = None

    def init(self):
        self.avail_transids = self.businesslogic.get_all_transids()
        self.view.present_available_transids(self.avail_transids)

    def add_transformer(self):
        selected_transid = self.view.get_selected_available_transid()
        if self.not_exist_in_chain(selected_transid):
            self.chain_transids.append(selected_transid)
        self.view.present_chain_transids(self.chain_transids)

    def remove_transformer(self):
        self.chain_transids.remove(self.view.get_selected_chain_transid())
        self.view.present_chain_transids(self.chain_transids)

    def apply_transformer_chain(self):
        sourcestr = self.view.get_sourcestr()

        if not self.check_input(sourcestr, self.chain_transids):
            return

        self.view.present_resultstr(
            self.businesslogic.transform(sourcestr, self.chain_transids))

    def remove_all_transformers(self):
        del self.chain_transids[:]
        self.view.present_chain_transids(self.chain_transids)

    def add_all_transformers(self):
        del self.chain_transids[:]
        self.chain_transids.extend(self.avail_transids)
        self.view.present_chain_transids(self.chain_transids)

    def not_exist_in_chain(self, transid):
        return transid not in self.chain_transids

    def check_input(self, sourcestr, _chain_transids):
        paramrules = [
            (sourcestr, lambda s: s == '', self.view.on_empty_sourcestr_input),
            (_chain_transids, lambda lst: lst == [], self.view.on_empty_chain_input)
        ]

        def check_param(param, predication, action):
            if predication(param):
                action()
                return False
            return True

        for paramrule in paramrules:
            if not check_param(paramrule[0], paramrule[1], paramrule[2]):
                return False

        return True