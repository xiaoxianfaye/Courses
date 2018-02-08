from Tkinter import *
import tkMessageBox

class View(object):
    def present_available_transids(self, transids, selected_transid_idx): pass

    def get_selected_available_transid(self): pass

    def present_chain_transids(self, transids,
                               selected_chain_transid_idx, selected_available_transid_idx): pass

    def get_selected_chain_transid(self): pass

    def get_sourcestr(self): pass

    def present_resultstr(self, s): pass

    def on_empty_sourcestr_input(self): pass

    def on_empty_chain_input(self): pass

    def set_presenter(self): pass

class ViewImpl(View):
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

        self.init_availableframe(centerframe)
        self.init_chainframe(centerframe)
        self.init_operbtnsframe(centerframe)

    def init_availableframe(self, parent):
        availableframe = LabelFrame(parent, text='Available Transformers')
        availableframe.pack(side=LEFT, padx=5)
        scrolly = Scrollbar(availableframe)
        scrolly.pack(side=RIGHT, fill=Y)
        self.lstavailable = Listbox(availableframe, width=25, yscrollcommand=scrolly.set)
        self.lstavailable.pack(fill=BOTH)
        scrolly.config(command=self.lstavailable.yview)

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

    def present_available_transids(self, transids, selected_transid_idx):
        self.set_list_data(self.lstavailable, transids)
        self.set_list_selected_index(self.lstavailable, selected_transid_idx)

    def get_selected_available_transid(self):
        return self.get_list_selected_item(self.lstavailable)

    def present_chain_transids(self, transids,
                               selected_chain_transid_idx, selected_available_transid_idx):
        self.set_list_data(self.lstchain, transids)
        self.set_list_selected_index(self.lstchain, selected_chain_transid_idx)
        self.set_list_selected_index(self.lstavailable, selected_available_transid_idx)

    def get_selected_chain_transid(self):
        return self.get_list_selected_item(self.lstchain)

    def get_sourcestr(self):
        return self.txtsourcestr.get()

    def present_resultstr(self, s):
        self.resultstr.set(s)

    def on_empty_sourcestr_input(self):
        self.show_info('Enter a source string, please.')
        self.txtsourcestr.focus_set()

    def on_empty_chain_input(self):
        self.show_info('Specify the transformer chain, please.')

    def set_list_data(self, lstbox, items):
        lstbox.delete(0, END)
        for item in items:
            lstbox.insert(END, item)

    def set_list_selected_index(self, lstbox, index):
        lstbox.selection_clear(0, END)
        if index > -1:
            lstbox.selection_set(index)

    def get_list_selected_item(self, lstbox):
        return lstbox.get(ACTIVE)

    def show_info(self, info):
        tkMessageBox.showinfo('Information', info)

    def set_presenter(self, presenter):
        self.presenter = presenter

    def add_transformer(self):
        self.presenter.add_transformer()

    def remove_transformer(self):
        self.presenter.remove_transformer()

    def remove_all_transformers(self):
        self.presenter.remove_all_transformers()

    def apply_transformer_chain(self):
        self.presenter.apply_transformer_chain()

    def exit(self):
        self.root.destroy()

from businesslogic import BusinessLogicImpl
from presenter import Presenter

if __name__ == '__main__':
    viewimpl = ViewImpl()
    businesslogicimpl = BusinessLogicImpl()
    presenter = Presenter(viewimpl, businesslogicimpl)
    presenter.init()

    viewimpl.centershow(560, 400)
    viewimpl.root.mainloop()
