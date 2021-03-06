import re
def read_file(file_name):

    temp_vals = []
    temp = []
    board_values = []
    symbols = [",","|","\n","]",";","",'']

    file = open(file_name,"r")
    for line in file:
        temp.append(line)

    temp.pop(0)
    temp.pop(0)
    temp.pop(0)

    for lines in temp:
        line = re.sub('[!@#$,|\n" "\;]', ' ',lines)
        line = line.split(" ")
#         print(line)
        for val in line:
            if val in symbols:
                continue
            temp_vals.append(int(val))
#             print(temp_vals)
        board_values.append(temp_vals.copy())
        temp_vals.clear()
        
    return board_values