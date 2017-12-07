import copy

class DoubleStackProcessor(object):
    def __init__(self):
        self.operandstack = []

    def process(self, c):
        self.operandstack.append(int(c))

    def _dump_operandstack(self):
        return copy.copy(self.operandstack)

# class DoubleStackProcessor(object):
#     def __init__(self):
#         self.operandStack = []
#         self.operatorStack = []

#     def process(self, c):
#         if c.isdigit():
#             self.pushOperand(int(c))
#         else:
#             if self.not_empty_operatorstack():
#                 rOperand = self.operandStack.pop()
#                 lOperand = self.operandStack.pop()
#                 operator = self.operatorStack.pop()
#                 self.pushOperand(lOperand + rOperand)

#             self.pushOperator(c)

#     def not_empty_operatorstack(self):
#         return len(self.operatorStack) != 0

#     def pushOperand(self, operand):
#         self.operandStack.append(operand)

#     def pushOperator(self, operator):
#         self.operatorStack.append(operator)

#     def _dumpOperandStack(self):
#         return copy.copy(self.operandStack)

#     def _dumpOperatorStack(self):
#         return copy.copy(self.operatorStack)