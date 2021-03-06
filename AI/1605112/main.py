# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
from CSP import CSP
from MakeBoard import read_file

def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':

    board_vals = read_file("d-10-01.txt.txt")
    obj = CSP(board_vals.copy())
#     obj.print_board()
#     obj.run_ac3()
#     obj.print_board()
    obj.print_board()
    obj.run_backtracking()
    obj.print_board()

#     obj.print_board()
#     obj.run_forward_checking()
#     obj.print_board()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
