class Action(object):
    def act(self, n):
        pass

class ToFizz(Action):
    def act(self, n):
        return 'Fizz'

class ToBuzz(Action):
    def act(self, n):
        return 'Buzz'

class ToWhizz(Action):
    def act(self, n):
        return 'Whizz'

class ToStr(Action):
    def act(self, n):
        return str(n)