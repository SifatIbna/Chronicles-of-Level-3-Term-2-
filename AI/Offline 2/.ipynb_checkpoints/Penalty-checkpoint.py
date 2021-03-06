from collections import deque, defaultdict
import random
import numpy as np
import csv

class Penalty:
    
    def __init__(self,stu_to_sub,colored_vertices):
        self.stu_to_sub = stu_to_sub
        self.colored_vertices = colored_vertices
        
    def set_penalty_var(self):
        penalty_node = []
        penalty_node.append(16)
        penalty_node.append(8)
        penalty_node.append(4)
        penalty_node.append(2)
        penalty_node.append(1)
#         print(penalty_node)
        return penalty_node

    def find_penalty(self):
        penalty_node = self.set_penalty_var()
        
        penalty = 0
        error = 0
        for index,sub in self.stu_to_sub.items():
            slot = []
            for val in sub:
                slot.append(self.colored_vertices[val])
                slot.sort()
            for i in range(0,len(slot)):
                if i<len(slot)-2:
                    diff = slot[i+1] - slot[i]
        #             print(diff)
                    if diff == 0:
                        error += 1
                    elif diff <=5:
                        penalty += penalty_node[diff-1]
        if error == 0:
            return penalty/len(self.stu_to_sub)
        return None