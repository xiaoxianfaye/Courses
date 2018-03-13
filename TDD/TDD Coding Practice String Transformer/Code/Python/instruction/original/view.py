class View(object):
    def set_presenter(self, presenter): pass

    def on_init(self, data): pass

    def collect_add_trans_data(self): pass

    def on_add_trans(self, data): pass

    def on_validating_failed(self, data): pass


from Tkinter import *
import tkMessageBox

from interaction import *
from validator import ValidatingResult

class ViewImpl(object):
    VRFR_TIP_MAP = {
        ValidatingResult.VRFR_ADD_ALREADY_EXISTED_IN_CHAIN_TRANS:
            'The transformer to be added has been already existed in the chain.'
    }

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

    def add_transformer(self):
        self.presenter.add_trans()

    def remove_transformer(self): pass

    def remove_all_transformers(self): pass

    def apply_transformer_chain(self): pass

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
    def on_validating_failed(self, data):
        tkMessageBox.showinfo('Information', ViewImpl.VRFR_TIP_MAP[data])


from businesslogic import BusinessLogicImpl
from presenter import Presenter

if __name__ == '__main__':
    viewimpl = ViewImpl()
    businesslogicimpl = BusinessLogicImpl()
    presenter = Presenter(viewimpl, businesslogicimpl)
    presenter.init()

    viewimpl.centershow(560, 400)
    viewimpl.root.mainloop()
