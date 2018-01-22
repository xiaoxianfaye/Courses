from Tkinter import Button, Entry

import copy

def rectangle(left, top, width, height):
    return {'left':left, 'top':top, 'width':width, 'height':height}

def _AT_IN(cmp):
    def AT(_rectangle):
        def IN(_container):
            cmp.master = _container
            _container.create_window(int(_rectangle['left'] + _rectangle['width'] * 0.5),
                                     int(_rectangle['top'] + _rectangle['height'] * 0.5),
                                     width = _rectangle['width'],
                                     height = _rectangle['height'],
                                     window = cmp)
        return IN
    return AT

def button(text):
    return _AT_IN(Button(text=text))

def entry():
    return _AT_IN(Entry())

def beside(leftcmp, rightcmp, ratio):
    def AT(_rectangle):
        def IN(_container):
            leftcmp(rectangle(_rectangle['left'], _rectangle['top'],
                              int(_rectangle['width'] * ratio), _rectangle['height']))(_container)
            rightcmp(rectangle(_rectangle['left'] + int(_rectangle['width'] * ratio), _rectangle['top'],
                               int(_rectangle['width'] * (1 - ratio)), _rectangle['height']))(_container)
        return IN
    return AT

def above(upcmp, downcmp, ratio):
    def AT(_rectangle):
        def IN(_container):
            upcmp(rectangle(_rectangle['left'], _rectangle['top'],
                            _rectangle['width'], int(_rectangle['height'] * ratio)))(_container)
            downcmp(rectangle(_rectangle['left'], _rectangle['top'] + int(_rectangle['height'] * ratio),
                              _rectangle['width'], int(_rectangle['height'] * (1 - ratio))))(_container)
        return IN
    return AT

def empty():
    def AT(_rectangle):
        def IN(_container):
            pass
        return IN
    return AT

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