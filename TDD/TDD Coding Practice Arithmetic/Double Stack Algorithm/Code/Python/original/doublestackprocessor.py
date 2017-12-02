class DoubleStackProcessor:
    def __init__(self):
        self.operandStack = []

    def process(self, c):
        self.operandStack.append(int(c))

    def _dumpOperandStack(self):
        return self.operandStack
