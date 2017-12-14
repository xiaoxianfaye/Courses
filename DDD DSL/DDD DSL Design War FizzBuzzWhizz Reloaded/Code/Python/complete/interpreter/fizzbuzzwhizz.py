import sys

from fbwparser.parser import *
from interpreter import *

def run(prog_file_name):
    prog_file_full_name = ''.join([sys.path[0], '/scripts/', prog_file_name])
    _output(_run_spec(parse(prog_file_full_name)))

def _run_spec(s):
    return [apply_rule(s, n) for n in range(1, 101)]

def _output(results):
    print [result[1] for result in results]

if __name__ == '__main__':
    run('prog_1.fbw')