from Cell import Cell


class DeleteQueue:

    def __init__(self, board):
        self.check_list = []
        self.delete_list = []
        self.board = board

    def add_delete(self, cell):
        self.add_row(cell)
        self.add_col(cell)

    def add_row(self, cell):
        for y in range(len(self.board)):
            if y != cell.col:
                new_cell = Cell(self.board, cell.row, y)
                self.check_list.append(new_cell)

    def add_col(self, cell):
        for x in range(len(self.board)):
            if x != cell.row:
                new_cell = Cell(self.board, x, cell.col)
                self.check_list.append(new_cell)

    def update_all_domain(self, value, map_):
        for cell in self.check_list:
            self.update_domain(cell, value, map_)

    def update_domain(self, cell, value, map_):
        domains = map_[cell]

        if value in domains:
            self.delete_list.append(cell)
            domains.remove(value)
            map_[cell] = domains

    def check_empty_domain(self, map_):

        for cell in self.delete_list:
            if len(map_[cell]) == 0:
                return True
        return False

    def restore_domain(self, value, map_):
        for cell in self.delete_list:
            domain = map_[cell]
            domain.append(value)
            map_[cell] = domain
        self.delete_list.clear()