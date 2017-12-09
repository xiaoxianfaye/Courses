from predication import Predication

class Times(Predication):
    def __init__(self, base):
        self.base = base

    def predicate(self, n):
        return n % self.base == 0