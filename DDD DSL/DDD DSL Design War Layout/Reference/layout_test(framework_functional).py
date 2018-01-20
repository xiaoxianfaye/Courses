from Tkinter import Tk, Canvas

# Definiting Test cases here


def tests(container):
    # Invoking test cases here
    pass

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