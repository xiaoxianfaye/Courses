class OOAction(object):
    def act(self, n):
        pass

class OOToFizz(OOAction):
    def act(self, n):
        return 'Fizz'

class OOToBuzz(OOAction):
    def act(self, n):
        return 'Buzz'

class OOToWhizz(OOAction):
    def act(self, n):
        return 'Whizz'

class OOToStr(OOAction):
    def act(self, n):
        return str(n)