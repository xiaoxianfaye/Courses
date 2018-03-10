class View(object):
    def set_presenter(self, presenter): pass

    def present_avail_transes(self, transes): pass

    def set_avail_selected_index(self, index): pass

    def get_avail_selected_trans(self): pass

    def present_chain_transes(self, transes): pass

    def set_chain_selected_index(self, index): pass

    def notify_add_already_existed_in_chain_trans(self): pass

    def notify_avail_trans_not_specified(self): pass

    def get_chain_selected_trans(self): pass

    def notify_chain_trans_not_specified(self): pass

    def notify_chain_empty(self): pass

    def get_source_str(self): pass

    def present_result_str(self, s): pass

    def notify_source_str_empty(self): pass

    def notify_source_str_illegal(self): pass


from Tkinter import *
import tkMessageBox

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
    def show_info(info):
        tkMessageBox.showinfo('Information', info)

    @staticmethod
    def get_entry_txt(entry):
        return entry.get()

    @staticmethod
    def set_entry_txt(strvar, s):
        strvar.set(s)

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
    def present_avail_transes(self, transes):
        ViewImpl.set_list_data(self.lstavail, transes)

    # Override
    def set_avail_selected_index(self, index):
        ViewImpl.set_list_selected_index(self.lstavail, index)

    # Override
    def get_avail_selected_trans(self):
        return ViewImpl.get_list_selected_item(self.lstavail)

    # Override
    def present_chain_transes(self, transes):
        ViewImpl.set_list_data(self.lstchain, transes)

    # Override
    def set_chain_selected_index(self, index):
        ViewImpl.set_list_selected_index(self.lstchain, index)

    # Override
    def notify_add_already_existed_in_chain_trans(self):
        ViewImpl.show_info('The transformer to be added has been already existed in the chain.')

    # Override
    def notify_avail_trans_not_specified(self):
        ViewImpl.show_info('Specify an available transformer, please.')

    # Override
    def get_chain_selected_trans(self):
        return ViewImpl.get_list_selected_item(self.lstchain)

    # Override
    def notify_chain_trans_not_specified(self):
        ViewImpl.show_info('Specify a transformer from the chain, please.')

    # Override
    def notify_chain_empty(self):
        ViewImpl.show_info('Specify the transformer chain, please.')

    # Override
    def get_source_str(self):
        return ViewImpl.get_entry_txt(self.txtsourcestr)

    # Override
    def present_result_str(self, s):
        ViewImpl.set_entry_txt(self.resultstr, s)

    # Override
    def notify_source_str_empty(self):
        ViewImpl.show_info('Specify the source string, please.')
        self.txtsourcestr.focus_set()

    # Override
    def notify_source_str_illegal(self):
        ViewImpl.show_info('Specify the legal source string, please.')
        self.txtsourcestr.focus_set()
        self.txtsourcestr.select_range(0, END)


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