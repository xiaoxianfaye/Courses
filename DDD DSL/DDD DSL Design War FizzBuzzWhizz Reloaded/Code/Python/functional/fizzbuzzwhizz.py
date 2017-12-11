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