

class Pair:

    def __init__(self, c1, c2):
        self.c1 = c1
        self.c2 = c2

    def __eq__(self, other):

        if other == None:
            return False
        if not isinstance(other, Pair):
            return False

        return other.left() == self.c1 and other.right() == self.c2

    def right(self):
        return self.c2

    def left(self):
        return self.c1