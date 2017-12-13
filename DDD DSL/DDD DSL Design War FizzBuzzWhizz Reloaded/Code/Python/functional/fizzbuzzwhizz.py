def times(base):
    def predicate(n):
        return n % base == 0
    return predicate

def contains(digit):
    def predicate(n):
        return str(digit) in str(n)
    return predicate

def alwaystrue():
    def predicate(n):
        return True
    return predicate

def tofizz():
    def act(n):
        return 'Fizz'
    return act

def tobuzz():
    def act(n):
        return 'Buzz'
    return act

def towhizz():
    def act(n):
        return 'Whizz'
    return act

def tostr():
    def act(n):
        return str(n)
    return act

def atom(predication, action):
    def apply(n):
        if predication(n):
            return True, action(n)

        return False, ''
    return apply

def OR(rule1, rule2):
    def apply(n):
        result1 = rule1(n)
        if result1[0]:
            return result1
        return rule2(n)
    return apply

def OR3(rule1, rule2, rule3):
    return OR(rule1, OR(rule2, rule3))

def OR4(rule1, rule2, rule3, rule4):
    return OR(rule1, OR3(rule2, rule3, rule4))

def AND(rule1, rule2):
    def apply(n):
        result1 = rule1(n)
        if not result1[0]:
            return False, ''
        result2 = rule2(n)
        if not result2[0]:
            return False, ''
        return True, ''.join([result1[1], result2[1]])
    return apply

def AND3(rule1, rule2, rule3):
    return AND(rule1, AND(rule2, rule3))

def spec():
    r1_3 = atom(times(3), tofizz())
    r1_5 = atom(times(5), tobuzz())
    r1_7 = atom(times(7), towhizz())

    r1 = OR3(r1_3, r1_5, r1_7)
    r2 = OR4(AND3(r1_3, r1_5, r1_7),
             AND(r1_3, r1_5),
             AND(r1_3, r1_7),
             AND(r1_5, r1_7))
    r3 = atom(contains(3), tofizz())
    rd = atom(alwaystrue(), tostr())
    
    return OR4(r3, r2, r1, rd)

def run():
    s = spec()
    results = [s(n) for n in range(1, 101)]
    output(results)

def output(results):
    print [result[1] for result in results]

if __name__ == '__main__':
    run()    