import copy
import re

class DoubleStackProcessor(object):
    operator_func_map = {'+':lambda x, y: x + y,
                         '*':lambda x, y: x * y,
                         '-':lambda x, y: x - y,
                         '/':lambda x, y: x / y,
                         '%':lambda x, y: x % y,
                         '**':lambda x, y: x ** y}

    operator_priority_map = {'+':1, '-':1, '*':2, '/':2, '%':2, '**':3}
    
    def __init__(self):
        self.operandstack = []
        self.operatorstack = []

    def process(self, item):
        if self.isint(item):
            self.process_operand(item)
        else:
            self.process_operator(item)

    def process_operand(self, item):
        self.push_operand(int(item))

    def process_operator(self, item):
        while self.not_empty_operatorstack() and self.not_prior_to(item, self.top_operator()):
            self.calc_once()

        self.push_operator(item)

    def result(self):
        self.calc()
        return self.pop_operand()

    def calc(self):
        while self.not_empty_operatorstack():
            self.calc_once()

    def not_empty_operatorstack(self):
        return len(self.operatorstack) != 0

    def not_prior_to(self, operator1, operator2):
        return self.priority(operator1) <= self.priority(operator2)

    def priority(self, operator):
        return DoubleStackProcessor.operator_priority_map[operator]

    def calc_once(self):
        r_operand = self.pop_operand()
        l_operand = self.pop_operand()
        operator = self.pop_operator()
        self.push_operand(self.apply(operator, l_operand, r_operand))

    def apply(self, operator, l_operand, r_operand):
        return DoubleStackProcessor.operator_func_map[operator](l_operand, r_operand)

    def push_operand(self, operand):
        self.operandstack.append(operand)

    def pop_operand(self):
        return self.operandstack.pop()

    def push_operator(self, operator):
        self.operatorstack.append(operator)

    def pop_operator(self):
        return self.operatorstack.pop()

    def top_operator(self):
        return self.operatorstack[-1]

    def isint(self, item):
        return re.match(r'-?\b[0-9]+\b', item)

    def _dump_operandstack(self):
        return copy.copy(self.operandstack)

    def _dump_operatorstack(self):
        return copy.copy(self.operatorstack)
