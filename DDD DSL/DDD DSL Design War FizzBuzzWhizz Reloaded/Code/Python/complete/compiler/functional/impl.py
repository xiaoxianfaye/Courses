def i_times(base):
    def predicate(n):
        return n % base == 0
    return predicate

def i_contains(digit):
    def predicate(n):
        return str(digit) in str(n)
    return predicate

def i_alwaystrue(param):
    def predicate(n):
        return True
    return predicate

def i_tofizz():
    def act(n):
        return 'Fizz'
    return act

def i_tobuzz():
    def act(n):
        return 'Buzz'
    return act

def i_towhizz():
    def act(n):
        return 'Whizz'
    return act

def i_tostr():
    def act(n):
        return str(n)
    return act

def i_atom(predication, action):
    def apply(n):
        if predication(n):
            return True, action(n)

        return False, ''
    return apply

def i_or(rule1, rule2):
    def apply(n):
        result1 = rule1(n)
        if result1[0]:
            return result1
        return rule2(n)
    return apply

def i_and(rule1, rule2):
    def apply(n):
        result1 = rule1(n)
        if not result1[0]:
            return False, ''
        result2 = rule2(n)
        if not result2[0]:
            return False, ''
        return True, ''.join([result1[1], result2[1]])
    return apply    
