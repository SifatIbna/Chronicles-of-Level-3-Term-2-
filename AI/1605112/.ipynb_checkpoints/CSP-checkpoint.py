from collections import defaultdict
import time
from Cell import Cell
from Pair import Pair
from DeleteQueue import DeleteQueue

class CSP:
    def __init__(self, board):

        self.cell_map = defaultdict(list)
        self.queue = []
        self.ac3 = False
        self.board = board
        self.num_nodes_ac3 = 0
        self.num_recur_ac3 = 0
        self.num_nodes_forward = 0
        self.num_recur_forward = 0
        self.num_nodes_backtrack = 0
        self.num_recur_backtrack = 0

    def set_board(self, board):
        self.board = board

    def fill_cell_map(self):
        for i in range(len(self.board)):
            for j in range(len(self.board)):
                if self.board[i][j] == 0:
                    new_cell = Cell(self.board, i, j)
                    elements = self.fill_domain()
                    self.cell_map[new_cell] = elements
                #                     print(self.cell_map)
                else:
                    new_cell = Cell(self.board, i, j)
                    vals = [self.board[i][j]]
                    self.cell_map[new_cell] = vals

    def fill_domain(self):
        elements = []
        for i in range(1, len(self.board) + 1):
            elements.append(i)
        return elements

    def print_domains(self):
        for i in range(len(self.board)):
            for j in range(len(self.board)):
                print("Domain cell (" + str(i) + "," + str(j) + "):", end=" ")
                cell = Cell(self.board, i, j);
                domain = self.cell_map[cell]
                # print(self.cell_map)
                # print(domain)
                for z in range(len(domain)):
                    print(str(domain[z]) + " ", end=" ")
                print("\n")

    def fill_Q(self):
        for i in range(len(self.board)):
            for j in range(len(self.board)):
                new_cell = Cell(self.board, i, j)
                self.add_to_queue(new_cell, True)

    def exists_in_queue(self, pair):
        for p in self.queue:

            if p == pair:
                return True
        return False

    def add_to_queue(self, cell, bo):
        self.add_row(cell, bo)

        self.add_col(cell, bo)

    def add_row(self, cell, bo):
        for i in range(len(self.board)):

            if i != cell.col:
                new_cell = Cell(self.board, cell.row, i)
                if bo:
                    pair = Pair(cell, new_cell)
                else:
                    pair = Pair(new_cell, cell)
                if not self.exists_in_queue(pair):
                    self.queue.append(pair)

    def add_col(self, cell, bo):
        for i in range(len(self.board)):
            if i != cell.row:
                new_cell = Cell(self.board, i, cell.col)
                if bo:
                    pair = Pair(cell, new_cell)
                else:
                    pair = Pair(new_cell, cell)
                if not self.exists_in_queue(pair):
                    self.queue.append(pair)

    def end_of_grid(self, board):
        for x in range(len(self.board)):
            for y in range(len(self.board)):
                if board[x][y] == 0:
                    return False
        return True

    def backtracking(self, cell):
        self.num_recur_backtrack += 1

        if self.end_of_grid(self.board):
            return True

        valuescell = self.cell_map[cell]
        value = 0

        for index in range(len(valuescell)):
            value = valuescell[index]
            self.board[cell.row][cell.col] = value
            if self.is_valid(cell, value):
                self.num_nodes_backtrack += 1
                if self.backtracking(cell.next_cell()):
                    return True
        self.board[cell.row][cell.col] = 0
        return False

    def is_valid(self, cell, value):

        for y in range(len(self.board)):
            if y != cell.col:
                if self.board[cell.row][y] == value:
                    return False

        for x in range(len(self.board)):
            if x != cell.row:
                if self.board[x][cell.col] == value:
                    return False
        return True

    def forward_checking(self, cell):
        self.num_recur_forward += 1

        if self.end_of_grid(self.board):
            return True

        del_queue = DeleteQueue(self.board)
        del_queue.add_delete(cell)
        values_cell = self.cell_map[cell]

        value = 0

        for index in range(len(values_cell)):
            value = values_cell[index]
            del_queue.update_all_domain(value, self.cell_map)

            if del_queue.check_empty_domain(self.cell_map):
                del_queue.restore_domain(value, self.cell_map)

            else:
                new_domain = [value]
                self.cell_map[cell] = new_domain
                self.board[cell.row][cell.col] = value

                self.num_nodes_forward += 1

                if self.forward_checking(cell.next_cell()):
                    return True
                else:
                    del_queue.restore_domain(value, self.cell_map)
                    self.cell_map[cell] = values_cell
        self.board[cell.row][cell.col] = 0

        return False

    def print_board(self):
        for x in range(len(self.board)):
            for y in range(len(self.board)):
                print(self.board[x][y], end=" ")
            print("\n")

    def Ac3(self):
        self.ac3 = True
        solution_exists = True
        changed = True
        self.fill_cell_map()
        self.fill_Q()
        self.print_domains()
        while len(self.queue) > 0 and solution_exists:
            self.num_recur_ac3 += 1
            pair = self.queue.pop(0)

            changed = False
            value_cell1 = self.cell_map[pair.left()]

            value_cell2 = self.cell_map[pair.right()]

            for i in reversed(range(len(value_cell1))):

                if self.delete_value(value_cell2, value_cell1[i]):
                    value_cell1.remove(value_cell1[i])
                    changed = True

            if len(value_cell1) == 0:
                solution_exists = False

            if changed:
                self.cell_map[pair.left()] = value_cell1
                self.add_to_queue(pair.left(), False)
                self.num_nodes_ac3 += 1

        self.print_domains()
        if not solution_exists:
            print("There is no solution for this problem\n")
            return False
        return True

    def delete_value(self, values, value):
        for val in values:
            if val != value:
                return False
        return True

    def run_ac3(self):
        time1 = time.time()
        if self.Ac3():
            print("Solution Exists!!! :D")
        time2 = time.time()
        print("Number of Nodes {}".format(self.num_nodes_ac3))
        print("Number of Backtrack {}".format(self.num_recur_ac3))
        print("Time Elapsed " + str(time2 - time1))

    def run_forward_checking(self):
        if not self.ac3:
            self.fill_cell_map()
        cell = Cell(self.board)
        time1 = time.time()
        self.forward_checking(cell.next_cell())
        time2 = time.time()
        print("Number of Nodes {}".format(self.num_nodes_forward))
        print("Number of Backtrack {}".format(self.num_recur_forward))
        print("Time Taken for Forward Checking {}".format(time2 - time1))

    def run_backtracking(self):
        if not self.ac3:
            self.fill_cell_map()
        cell = Cell(self.board)
        time1 = time.time()
        self.backtracking(cell.next_cell())
        time2 = time.time()
        print("Number of Nodes {}".format(self.num_nodes_backtrack))
        print("Number of Backtrack {}".format(self.num_recur_backtrack))
        print("Time Taken for Backtrack Checking {}".format(time2 - time1))