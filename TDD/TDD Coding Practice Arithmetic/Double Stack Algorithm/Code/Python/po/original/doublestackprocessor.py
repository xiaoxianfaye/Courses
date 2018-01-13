import copy

operator_func_map = {'+':lambda x, y: x + y,
                     '-':lambda x, y: x - y,
                     '*':lambda x, y: x * y,
                     '/':lambda x, y: x / y}

operator_priority_map = {'+':1, '-':1, '*':2, '/':2}

def process(c, operandstack, operatorstack):
    if c.isdigit():
        process_operand(c, operandstack)
    else:
        process_operator(c, operandstack, operatorstack)

def result(operandstack, operatorstack):
    calc(operandstack, operatorstack)
    return pop_operand(operandstack)

def calc(operandstack, operatorstack):
    while not_empty(operatorstack):
        calc_once(operandstack, operatorstack)

def process_operand(c, operandstack):
    push_operand(int(c), operandstack)

def process_operator(c, operandstack, operatorstack):
    while not_empty(operatorstack) and not_prior_to(c, top_operator(operatorstack)):
        calc_once(operandstack, operatorstack)

    push_operator(c, operatorstack)

def not_empty(operatorstack):
    return len(operatorstack) != 0

def not_prior_to(operator1, operator2):
    return priority(operator1) <= priority(operator2)

def priority(operator):
    return operator_priority_map[operator]

def calc_once(operandstack, operatorstack):
    r_operand = pop_operand(operandstack)
    l_operand = pop_operand(operandstack)
    operator = pop_operator(operatorstack)
    push_operand(apply(operator, l_operand, r_operand), operandstack)

def apply(operator, l_operand, r_operand):
    return operator_func_map[operator](l_operand, r_operand)

def push_operand(operand, operandstack):
    operandstack.append(operand)

def pop_operand(operandstack):
    return operandstack.pop()

def push_operator(operator, operatorstack):
    operatorstack.append(operator)

def pop_operator(operatorstack):
    return operatorstack.pop()

def top_operator(operatorstack):
    return operatorstack[-1]

def _dump(stack):
    return copy.copy(stack)