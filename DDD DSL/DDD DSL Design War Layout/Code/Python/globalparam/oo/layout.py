from Tkinter import Button, Entry, Label

import copy

class Rectangle(object):
    def __init__(self, left, top, width, height):
        self.left = left
        self.top = top
        self.width = width
        self.height = height

class Component(object):
    def AT(self, left, top, width, height):
        pass

    def IN(self, container):
        pass

class BaseCmp(Component):
    def AT(self, left, top, width, height):
        self.rectangle = Rectangle(left, top, width, height)
        return self

    def IN(self, container):
        self.cmp.master = container
        container.create_window(int(self.rectangle.left + self.rectangle.width * 0.5),
                                int(self.rectangle.top + self.rectangle.height * 0.5),
                                width=self.rectangle.width,
                                height=self.rectangle.height,
                                window=self.cmp)
        return self

class ButtonCmp(BaseCmp):
    def __init__(self, text):
        self.cmp = Button(text=text)

class EntryCmp(BaseCmp):
    def __init__(self):
        self.cmp = Entry()

class LabelCmp(BaseCmp):
    def __init__(self, text):
        self.cmp = Label(text=text)

class Beside(Component):
    def __init__(self, leftcmp, rightcmp, ratio):
        self.leftcmp = leftcmp
        self.rightcmp = rightcmp
        self.ratio = ratio

    def AT(self, left, top, width, height):
        self.leftcmp.AT(left, top, int(width * self.ratio), height)
        self.rightcmp.AT(left + int(width * self.ratio), top, int(width * (1 - self.ratio)), height)
        return self

    def IN(self, container):
        self.leftcmp.IN(container)
        self.rightcmp.IN(container)
        return self

class Above(Component):
    def __init__(self, upcmp, downcmp, ratio):
        self.upcmp = upcmp
        self.downcmp = downcmp
        self.ratio = ratio

    def AT(self, left, top, width, height):
        self.upcmp.AT(left, top, width, int(height * self.ratio))
        self.downcmp.AT(left, top + int(height * self.ratio), width, int(height * (1 - self.ratio)))
        return self

    def IN(self, container):
        self.upcmp.IN(container)
        self.downcmp.IN(container)
        return self

class Empty(Component):
    def AT(self, left, top, width, height):
        return self

    def IN(self, container):
        return self

def button(text):
    return ButtonCmp(text)

def entry():
    return EntryCmp()

def beside(leftcmp, rightcmp, ratio):
    return Beside(leftcmp, rightcmp, ratio)

def above(upcmp, downcmp, ratio):
    return Above(upcmp, downcmp, ratio)

def empty():
    return Empty()

def hcenter(cmp, ratio):
    return _center(beside, cmp, ratio)

def vcenter(cmp, ratio):
    return _center(above, cmp, ratio)

def _center(poslayout, cmp, ratio):
    return poslayout(empty(), poslayout(cmp, empty(), (1.0 - 2.0 * ratio) / (1.0 - ratio)), ratio)

def center(cmp, hratio, vratio):
    return vcenter(hcenter(cmp, hratio), vratio)

def hseq(cmps):
    return _seq(beside, cmps)

def vseq(cmps):
    return _seq(above, cmps)

def _seq(poslayout, cmps):
    if len(cmps) == 1:
        return cmps[0]

    return poslayout(cmps[0], _seq(poslayout, cmps[1:]), 1.0 / len(cmps))

def block(cmps, rownum, colnum):
    paddedcmps = _padding(cmps, rownum * colnum)
    return vseq([hseq(paddedcmps[idx:idx + colnum]) for idx in range(0, rownum * colnum, colnum)])

def _padding(cmps, total):
    copiedcmps = copy.copy(cmps)
    return copiedcmps + [empty() for idx in range(0, total - len(cmps))]

def blockm(cmps, rownum, colnum, hratio, vratio):
    return block([center(cmp, hratio, vratio) for cmp in cmps], rownum, colnum)