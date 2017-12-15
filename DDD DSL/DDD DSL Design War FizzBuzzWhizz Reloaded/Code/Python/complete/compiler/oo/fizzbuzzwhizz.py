import sys

from fbwparser.parser import *
from compiler import *

def run(prog_file_name):
    prog_file_full_name = ''.join([sys.path[0], '/scripts/', prog_file_name])
    _output(_run_spec(parse(prog_file_full_name)))

def _run_spec(s):
    return [(n, compile(s).apply(n)) for n in range(1, 101)]

def _output(results):
    for result in results:
        print ' -> '.join([str(result[0]), result[1][1]])

if __name__ == '__main__':
    run('prog_1.fbw')