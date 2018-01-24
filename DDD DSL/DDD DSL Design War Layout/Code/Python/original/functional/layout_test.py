from Tkinter import Tk, Canvas

from layout import *

# Definiting Test cases here
def test_component(container):
    button('Button')(rectangle(0, 0, 200, 60))(container)

def test_beside(container):
    beside(entry(), button('Btn'), 0.8)(rectangle(0, 0, 300, 60))(container)

def test_above(container):
    above(entry(), button('Button'), 0.5)(rectangle(0, 0, 300, 60))(container)

def test_beside_above(container):
    above(beside(entry(), button('Btn1'), 0.8), button('Btn2'), 0.5)(rectangle(0, 0, 300, 60))(container)

def test_empty(container):
    beside(empty(), button('Button'), 0.5)(rectangle(0, 0, 300, 60))(container)

def test_hcenter(container):
    hcenter(button('Button'), 0.1)(rectangle(0, 0, 600, 400))(container)

def test_vcenter(container):
    vcenter(button('Button'), 0.1)(rectangle(0, 0, 600, 400))(container)

def test_center(container):
    center(button('Center'), 0.2, 0.1)(rectangle(0, 0, 600, 400))(container)

def test_hseq(container):
    hseq([button('1'), button('2'), button('3')])(rectangle(0, 0, 300, 60))(container)

def test_vseq(container):
    vseq([button('1'), button('2'), button('3')])(rectangle(0, 0, 150, 200))(container)

def test_block(container):
    cmps = [button(str(i)) for i in range(1, 12)]
    block(cmps, 4, 3)(rectangle(0, 0, 600, 400))(container)

def test_blockm(container):
    cmps = [button(str(i)) for i in range(1, 12)]
    blockm(cmps, 4, 3, 0.1, 0.1)(rectangle(0, 0, 600, 400))(container)

def test_minicalc(container):
    texts = ['0', '1', '2', '+',
             '3', '4', '5', '-',
             '6', '7', '8', '*',
             '9', '=', '%', '/']
    btns = [button(text) for text in texts]

    above(above(entry(),
                beside(button('Backspace'), button('C'), 0.5), 0.5),
          block(btns, 4, 4), 0.3)(rectangle(0, 0, 600, 400))(container)

def test_minicalc_margin(container):
    texts = ['0', '1', '2', '+',
             '3', '4', '5', '-',
             '6', '7', '8', '*',
             '9', '=', '%', '/']
    btns = [button(text) for text in texts]

    above(above(entry(),
                beside(button('Backspace'), button('C'), 0.5), 0.5),
          blockm(btns, 4, 4, 0.02, 0.02), 0.3)(rectangle(0, 0, 600, 400))(container)

def tests(container):
    # Invoking test cases here
    # test_component(container)
    # test_beside(container)
    # test_above(container)
    # test_beside_above(container)
    # test_empty(container)
    # test_hcenter(container)
    # test_vcenter(container)
    # test_center(container)
    # test_hseq(container)
    # test_vseq(container)
    # test_block(container)
    # test_blockm(container)
    # test_minicalc(container)
    test_minicalc_margin(container)

def main():
    root = Tk()
    root.title('Layout Test')

    container = Canvas(root, width=600, height=400)
    container.create_rectangle(0, 0, 600, 400, fill='light grey')
    container.pack()

    tests(container)

    root.mainloop()

if __name__ == '__main__':
    main()