from rule import *
from interpreter import *

def run():
    s = spec()
    results = [apply_rule(s, n) for n in range(1, 101)]
    output(results)

def output(results):
    print [result[1] for result in results]

if __name__ == '__main__':
    run()