import copy

class DoubleStackProcessor(object):
    operator_func_map = {'+':lambda x, y: x + y,
                         '-':lambda x, y: x - y,
                         '*':lambda x, y: x * y,
                         '/':lambda x, y: x / y,
                         '%':lambda x, y: x % y,
                         '**':lambda x, y: x ** y}

    operator_priority_map = {'+':1, '-':1, '*':2, '/':2, '%':2, '**':3}
    
    def __init__(self):
        self._operandstack = []
        self._operatorstack = []

    def process(self, item):
        if item.isdigit():
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
        return len(self._operatorstack) != 0

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
        self._operandstack.append(operand)

    def pop_operand(self):
        return self._operandstack.pop()

    def push_operator(self, operator):
        self._operatorstack.append(operator)

    def pop_operator(self):
        return self._operatorstack.pop()

    def top_operator(self):
        return self._operatorstack[-1]

    def _dump_operandstack(self):
        return self._dump_stack(self._operandstack)

    def _dump_operatorstack(self):
        return self._dump_stack(self._operatorstack)

    def _dump_stack(self, stack):
        return copy.copy(stack)
