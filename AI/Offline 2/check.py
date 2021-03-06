import collections
import random
import csv

def check_result(colored_Vertices,new_dict):
    error_count = 0
    error_nodes = collections.defaultdict(list)
    # check_nodes = defaultdict(list)

    for index,val in new_dict.items():

        for nei in val:
            if colored_Vertices[index] == colored_Vertices[nei]:
#                 print(index,nei)
                
                error_count += 1
                error_nodes[index].append(nei)
                # check_nodes[index] = new_dict[index].copy()
    return error_count
#     print(error_count)