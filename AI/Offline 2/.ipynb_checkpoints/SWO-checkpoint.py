from collections import deque, defaultdict
import random
import numpy as np
import csv

from Dsatur import Dsatur
class SWO:
    def __init__(self,graph):
        self.graph = graph
    
    def squeaky_wheel_opt(self):
        graph = self.graph.copy()
        max_value = 10000000
        blame = defaultdict(list)
        alpha = 0.9
        b = 2
        best_solution = defaultdict(list)
        
        for i in range(0,100):
            print("iteration :",i)
#     print("iteration :",i)
            obj = Dsatur(graph)
            colored_graph,cur_value = obj.dsatur()
        #     pen_val = Penalty(stu_to_sub,colored_graph).find_penalty()
        #     print("max_value:",max_value)
        #     print("penalty:",pen_val)
            if cur_value <= max_value:
                max_value = cur_value
                best_solution = colored_graph.copy()
            for j in range(0,len(graph)):
                blame[j] = j
                if colored_graph[j] > alpha*cur_value:
                    blame[j] = blame[j] + b
            blame = {k: v for k, v in sorted(blame.items(), key=lambda item: item[1],reverse = True)}
        #     print(blame)
            temp = defaultdict(list)
            for key,value in blame.items():
                temp[key] = graph[key].copy()
            graph = temp.copy()
        
        return max_value,best_solution