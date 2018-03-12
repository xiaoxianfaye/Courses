from interaction import *

class Presenter(object):
    def __init__(self, view, businesslogic):
        self.view = view
        self.businesslogic = businesslogic

        self.avail_selected_index = 0

    def init(self):
        self.view.on_init({AVAIL_TRANSES:self.businesslogic.get_all_transes(),
                           AVAIL_SELECTED_INDEX:self.avail_selected_index})