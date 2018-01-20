from Tkinter import Tk, Canvas

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

    def tests(self):
        # Invoking test cases here
        pass


if __name__ == '__main__':
    LayoutTest()