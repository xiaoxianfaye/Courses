class OOPredication(object):
    def predicate(self, n):
        pass

class OOTimes(OOPredication):
    def __init__(self, base):
        self.base = base

    def predicate(self, n):
        return n % self.base == 0

class OOContains(OOPredication):
    def __init__(self, digit):
        self.digit = digit

    def predicate(self, n):
        return str(self.digit) in str(n)

class OOAlwaysTrue(OOPredication):
    def predicate(self, n):
        return True