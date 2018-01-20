from Tkinter import Tk, Canvas

from layout import *

class LayoutTest(object):
    def __init__(self):
        root = Tk()
        root.title('Layout Test')

        self.container = Canvas(root, width=600, height=400)
        self.container.create_rectangle(0, 0, 600, 400, fill='light grey')
        self.container.pack()

        self.tests()

        root.mainloop()

    # Definiting Test cases here
    def test_component(self):
        button('Button').AT(0, 0, 200, 60).IN(self.container)

    def test_beside(self):
        beside(entry(), button('Btn'), 0.8).AT(0, 0, 300, 60).IN(self.container)

    def test_above(self):
        above(entry(), button('Button'), 0.5).AT(0, 0, 300, 60).IN(self.container)

    def test_beside_above(self):
        above(beside(entry(), button('Btn1'), 0.8), button('Btn2'), 0.5).AT(0, 0, 300, 60).IN(self.container)

    def test_empty(self):
        beside(empty(), button('Button'), 0.5).AT(0, 0, 300, 60).IN(self.container)

    def test_hcenter(self):
        hcenter(button('Button'), 0.1).AT(0, 0, 600, 400).IN(self.container)

    def test_vcenter(self):
        vcenter(button('Button'), 0.1).AT(0, 0, 600, 400).IN(self.container)

    def test_center(self):
        center(button('Center'), 0.2, 0.1).AT(0, 0, 600, 400).IN(self.container)

    def test_hseq(self):
        hseq([button('1'), button('2'), button('3')]).AT(0, 0, 300, 60).IN(self.container)

    def test_vseq(self):
        vseq([button('1'), button('2'), button('3')]).AT(0, 0, 150, 200).IN(self.container)

    def test_block(self):
        cmps = [button(str(i)) for i in range(1, 12)]
        block(cmps, 4, 3).AT(0, 0, 600, 400).IN(self.container)

    def test_blockm(self):
        cmps = [button(str(i)) for i in range(1, 12)]
        blockm(cmps, 4, 3, 0.1, 0.1).AT(0, 0, 600, 400).IN(self.container)

    def test_minicalc(self):
        texts = ['0', '1', '2', '+',
                 '3', '4', '5', '-',
                 '6', '7', '8', '*',
                 '9', '=', '%', '/']
        btns = [button(text) for text in texts]

        above(above(entry(),
                    beside(button('BackSpace'), button('C'), 0.5), 0.5),
              block(btns, 4, 4), 0.3).AT(0, 0, 600, 400).IN(self.container)

    def test_minicalc_margin(self):
        texts = ['0', '1', '2', '+',
                 '3', '4', '5', '-',
                 '6', '7', '8', '*',
                 '9', '=', '%', '/']
        btns = [button(text) for text in texts]

        above(above(entry(),
                    beside(button('BackSpace'), button('C'), 0.5), 0.5),
              blockm(btns, 4, 4, 0.02, 0.02), 0.3).AT(0, 0, 600, 400).IN(self.container)

    def test_globalparam(self):
        param1 = center(beside(LabelCmp('Parameter 1'), beside(empty(), EntryCmp(), 0.1), 0.3), 0.05, 0.3)
        param2 = center(beside(LabelCmp('Parameter 2'), beside(empty(), EntryCmp(), 0.1), 0.3), 0.05, 0.3)
        param3 = center(beside(LabelCmp('Parameter 3'), beside(empty(), EntryCmp(), 0.1), 0.3), 0.05, 0.3)
        params = vseq([param1, param2, param3])

        btns = beside(empty(), center(beside(button('Set'), beside(empty(), button('Close'), 0.1), 0.5), 0.06, 0.2), 0.2)

        above(params, btns, 0.8).AT(0, 0, 600, 400).IN(self.container)

    def tests(self):
        # Invoking test cases here
        # self.test_component()
        # self.test_beside()
        # self.test_above()
        # self.test_beside_above()
        # self.test_empty()
        # self.test_hcenter()
        # self.test_vcenter()
        # self.test_center()
        # self.test_hseq()
        # self.test_vseq()
        # self.test_block()
        # self.test_blockm()
        # self.test_minicalc()
        # self.test_minicalc_margin()
        self.test_globalparam()

if __name__ == '__main__':
    LayoutTest()