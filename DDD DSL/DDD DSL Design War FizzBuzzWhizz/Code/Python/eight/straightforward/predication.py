class Predication(object):
    def predicate(self, n):
        pass

class Times(Predication):
    def __init__(self, base):
        self.base = base

    def predicate(self, n):
        return n % self.base == 0

class Contains(Predication):
    def __init__(self, digit):
        self.digit = digit

    def predicate(self, n):
        return str(self.digit) in str(n)

class AlwaysTrue(Predication):
    def predicate(self, n):
        return True