class View(object):
    def set_presenter(self, presenter): pass

    def on_init(self, data): pass

    def collect_add_trans_data(self): pass

    def on_add_trans(self, data): pass

    def collect_remove_trans_data(self): pass

    def on_remove_trans(self, data): pass

    def on_remove_all_transes(self, data): pass

    def collect_apply_trans_chain_data(self): pass

    def on_apply_trans_chain(self, data): pass

    def on_add_all_transes(self, data): pass

    def on_validating_failed(self, data): pass


from Tkinter import *
import tkMessageBox

from interaction import *
from validator import ValidatingResult

class ViewImpl(object):
    def __init__(self):
        self.root = Tk()
        self.root.title('String Transformer')

        self.initui()

    def centershow(self, wndwidth, wndheight):
        scnwidth, scnheight = self.root.maxsize()
        geometrystr = '%dx%d+%d+%d' % (wndwidth, wndheight,
                                       (scnwidth - wndwidth) / 2,
                                       (scnheight - wndheight) / 2)
        self.root.geometry(geometrystr)

    def initui(self):
        self.init_topframe()
        self.init_centerframe()
        self.init_bottomframe()

    def init_topframe(self):
        topframe = Frame(self.root)
        topframe.pack(side=TOP, fill=BOTH, padx=10, pady=10)

        labelsframe = Frame(topframe)
        labelsframe.pack(side=LEFT)
        Label(labelsframe, text="Source String").grid(row=0, sticky=W, pady=5)
        Label(labelsframe, text="Result String").grid(row=1, sticky=W, pady=5)

        txtsframe = Frame(topframe)
        txtsframe.pack(fill=BOTH, padx=5)
        self.txtsourcestr = Entry(txtsframe)
        self.txtsourcestr.pack(side=TOP, fill=BOTH, pady=6)
        self.resultstr = StringVar()
        self.txtresultstr = Entry(txtsframe, textvariable=self.resultstr, state='readonly')
        self.txtresultstr.pack(side=BOTTOM, fill=BOTH, pady=6)

    def init_centerframe(self):
        centerframe = Frame(self.root)
        centerframe.pack(fill=BOTH, padx=10, pady=10)

        self.init_availframe(centerframe)
        self.init_chainframe(centerframe)
        self.init_operbtnsframe(centerframe)

    def init_availframe(self, parent):
        availframe = LabelFrame(parent, text='Available Transformers')
        availframe.pack(side=LEFT, padx=5)
        scrolly = Scrollbar(availframe)
        scrolly.pack(side=RIGHT, fill=Y)
        self.lstavail = Listbox(availframe, width=25, yscrollcommand=scrolly.set)
        self.lstavail.pack(fill=BOTH)
        scrolly.config(command=self.lstavail.yview)

    def init_chainframe(self, parent):
        chainframe = LabelFrame(parent, text='Transformer Chain')
        chainframe.pack(side=RIGHT, padx=5)
        scrolly = Scrollbar(chainframe)
        scrolly.pack(side=RIGHT, fill=Y)
        self.lstchain = Listbox(chainframe, width=25, yscrollcommand=scrolly.set)
        self.lstchain.pack(fill=BOTH)
        scrolly.config(command=self.lstchain.yview)

    def init_operbtnsframe(self, parent):
        operbtnsframe = Frame(parent)
        operbtnsframe.pack(fill=BOTH)

        topemptyframe = Frame(operbtnsframe)
        topemptyframe.pack(side=TOP)
        Label(topemptyframe, text="").pack()

        bottomemptyframe = Frame(operbtnsframe)
        bottomemptyframe.pack(side=BOTTOM)
        Label(bottomemptyframe, text="").pack()

        Button(operbtnsframe, text='Add >>', width=10, command=self.add_transformer).pack(pady=10)
        Button(operbtnsframe, text='Remove <<', width=10, command=self.remove_transformer).pack(pady=10)
        Button(operbtnsframe, text='Remove All', width=10, command=self.remove_all_transformers).pack(pady=10)
        Button(operbtnsframe, text='Add All', width=10, command=self.add_all_transformers).pack(pady=10)

    def init_bottomframe(self):
        bottomframe = Frame(self.root)
        bottomframe.pack(side=BOTTOM, fill=X, padx=10, pady=10)

        btnsframe = Frame(bottomframe)
        btnsframe.pack(side=RIGHT)

        Button(btnsframe, text='Apply', width=10, command=self.apply_transformer_chain).pack(side=LEFT, padx=5)
        Button(btnsframe, text='Exit', width=10, command=self.exit).pack(side=LEFT, padx=5)

    @staticmethod
    def set_list_data(lstbox, items):
        lstbox.delete(0, END)
        for item in items:
            lstbox.insert(END, item)

    @staticmethod
    def set_list_selected_index(lstbox, index):
        lstbox.selection_clear(0, END)
        lstbox.selection_set(index)

    @staticmethod
    def get_list_selected_item(lstbox):
        selected_index = lstbox.curselection()
        return lstbox.get(selected_index) if selected_index else None

    @staticmethod
    def get_entry_txt(entry):
        return entry.get()

    @staticmethod
    def set_entry_txt(strvar, s):
        strvar.set(s)

    @staticmethod
    def focus_entry_and_select_all(entry):
        entry.focus_set()
        entry.select_range(0, END)

    def add_transformer(self):
        self.presenter.add_trans()

    def remove_transformer(self):
        self.presenter.remove_trans()

    def remove_all_transformers(self):
        self.presenter.remove_all_transes()

    def apply_transformer_chain(self):
        self.presenter.apply_trans_chain()

    def add_all_transformers(self):
        self.presenter.add_all_transes()

    def exit(self):
        self.root.destroy()

    # Override
    def set_presenter(self, presenter):
        self.presenter = presenter

    # Override
    def on_init(self, data):
        ViewImpl.set_list_data(self.lstavail, data[AVAIL_TRANSES])
        ViewImpl.set_list_selected_index(self.lstavail, data[AVAIL_SELECTED_INDEX])

    # Override
    def collect_add_trans_data(self):
        return {AVAIL_SELECTED_TRANS:ViewImpl.get_list_selected_item(self.lstavail)}

    # Override
    def on_add_trans(self, data):
        ViewImpl.set_list_data(self.lstchain, data[CHAIN_TRANSES])
        ViewImpl.set_list_selected_index(self.lstchain, data[CHAIN_SELECTED_INDEX])
        ViewImpl.set_list_selected_index(self.lstavail, data[AVAIL_SELECTED_INDEX])

    # Override
    def collect_remove_trans_data(self):
        return {CHAIN_SELECTED_TRANS:ViewImpl.get_list_selected_item(self.lstchain)}

    # Override
    def on_remove_trans(self, data):
        ViewImpl.set_list_data(self.lstchain, data[CHAIN_TRANSES])
        ViewImpl.set_list_selected_index(self.lstavail, data[AVAIL_SELECTED_INDEX])
        ViewImpl.set_list_selected_index(self.lstchain, data[CHAIN_SELECTED_INDEX])

    # Override
    def on_remove_all_transes(self, data):
        ViewImpl.set_list_data(self.lstchain, data[CHAIN_TRANSES])
        ViewImpl.set_list_selected_index(self.lstchain, data[CHAIN_SELECTED_INDEX])
        ViewImpl.set_list_selected_index(self.lstavail, data[AVAIL_SELECTED_INDEX])

    # Override
    def collect_apply_trans_chain_data(self):
        return {SOURCE_STR:ViewImpl.get_entry_txt(self.txtsourcestr)}

    # Override
    def on_apply_trans_chain(self, data):
        ViewImpl.set_entry_txt(self.resultstr, data[RESULT_STR])
        ViewImpl.set_list_selected_index(self.lstavail, data[AVAIL_SELECTED_INDEX])

    # Override
    def on_add_all_transes(self, data):
        ViewImpl.set_list_data(self.lstchain, data[CHAIN_TRANSES])
        ViewImpl.set_list_selected_index(self.lstavail, data[AVAIL_SELECTED_INDEX])
        ViewImpl.set_list_selected_index(self.lstchain, data[CHAIN_SELECTED_INDEX])

    ACTION_SHOW_INFO = 'show_info'
    ACTION_FOCUS_AND_SELECT_ALL_SOURCE_STR = 'focus_and_select_all_source_str'

    VRFR_ACTIONS_MAP = {
        ValidatingResult.VRFR_AVAIL_TRANS_NOT_SPECIFIED:
            [(ACTION_SHOW_INFO, 'Specify an available transformer, please.')],
        ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS:
            [(ACTION_SHOW_INFO, 'The transformer to be added has been already existed in the chain.')],
        ValidatingResult.VRFR_CHAIN_TRANS_NOT_SPECIFIED:
            [(ACTION_SHOW_INFO, 'Specify a transformer from the chain, please.')],
        ValidatingResult.VRFR_CHAIN_EMPTY:
            [(ACTION_SHOW_INFO, 'Specify the transformer chain, please.')],
        ValidatingResult.VRFR_SOURCE_STR_EMPTY:
            [(ACTION_SHOW_INFO, 'Specify the source string, please.'),
             (ACTION_FOCUS_AND_SELECT_ALL_SOURCE_STR, None)],
        ValidatingResult.VRFR_SOURCE_STR_ILLEGAL:
            [(ACTION_SHOW_INFO, 'Specify the legal source string, please.'),
             (ACTION_FOCUS_AND_SELECT_ALL_SOURCE_STR, None)]
    }

    # Override
    def on_validating_failed(self, data):
        actions = ViewImpl.VRFR_ACTIONS_MAP[data[VALIDATING_FAILED_REASON]]
        for (action_type, action_param) in actions:
            if action_type == ViewImpl.ACTION_SHOW_INFO:
                tkMessageBox.showinfo('Information', action_param)
            elif action_type == ViewImpl.ACTION_FOCUS_AND_SELECT_ALL_SOURCE_STR:
                ViewImpl.focus_entry_and_select_all(self.txtsourcestr)


from businesslogic import BusinessLogicImpl
from presenter import Presenter

if __name__ == '__main__':
    import sys
    reload(sys)
    sys.setdefaultencoding('utf-8')

    viewimpl = ViewImpl()
    businesslogicimpl = BusinessLogicImpl()
    presenter = Presenter(viewimpl, businesslogicimpl)
    presenter.init()

    viewimpl.centershow(560, 400)
    viewimpl.root.mainloop()
