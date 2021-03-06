class Cell:

    def __init__(self, board, row=0, col=0):
        self.row = row
        self.col = col
        self.board = board

    def __eq__(self, other):

        if isinstance(other, Cell) and other != None:
            return self.row == other.row and self.col == other.col
        else:
            return False

    def __hash__(self):
        return self.row * 10 + self.col

    def next_cell(self):
        row = self.row
        col = self.col

        try:
            while self.board[row][col] != 0:
                col += 1
                if col > len(self.board) - 1:
                    col = 0
                    row += 1
        except IndexError:
            pass
        return Cell(self.board, row, col)