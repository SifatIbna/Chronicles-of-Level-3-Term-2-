from collections import deque, defaultdict
import random
import numpy as np
import csv

from check import check_result
from Penalty import Penalty

class Kempe_Chain:
    
    def __init__(self,colored_vertices,graph,stu_to_sub):
        self.colored_vertices = colored_vertices
        self.graph = graph
        self.stu_to_sub = stu_to_sub
    
    def bfs(self,allowed_color,node):
        visited = []
        queue = []
        
        visited.append(node)
        queue.append(node)

        while queue:
            s = queue.pop(0)
    #    

            for neighbour in self.graph[s]:
    #             print(neighbour,test_color[neighbour])
                if neighbour not in visited and self.colored_vertices[neighbour] in allowed_color:
    #                 print("neighbour",neighbour)
    #                 print("color",test_color[neighbour])

                    visited.append(neighbour)
                    queue.append(neighbour)
        return visited
    
    def set_random_node(self):
        allowed_colors = []
        
        while True:
            node1 = random.choice(list(self.graph.keys()))
            
            if len(self.graph[node1])>0:
                node2 = random.choice(self.graph[node1])
                break
            
        
        allowed_colors.append(self.colored_vertices[node1])
        allowed_colors.append(self.colored_vertices[node2])
        
        return node1,allowed_colors
    
    def kempe_chain(self):
        allowed_colors = list()
        best_chain = list()
        best_penalty = 100000000
        best_colored_graph = self.colored_vertices.copy()
#         test_color= self.colored_vertices.copy()

        for key,value in self.graph.items():
            
            if len(self.graph[key]) == 0:
                continue
#             node,allowed_colors = self.set_random_node()
            else:
                node = random.choice(self.graph[key])
            
                allowed_colors.append(self.colored_vertices[key])
                allowed_colors.append(self.colored_vertices[node])
                
                visited = self.bfs(allowed_colors,key)

                color1 = 0
                color2 = 0

                test_color= self.colored_vertices.copy()
                for val in visited:
                #     print(val)
                    if self.colored_vertices[val] == allowed_colors[0]:
                        test_color[val] = allowed_colors[1]
        #                 color1 += 1
                    elif self.colored_vertices[val]==allowed_colors[1]:
                        test_color[val] = allowed_colors[0]
        #                 color2 += 1
                obj = Penalty(self.stu_to_sub,test_color)
                
                if obj.find_penalty() < best_penalty and check_result(test_color,self.graph) == 0:
                    
                    best_penalty = obj.find_penalty()
                    best_colored_graph = test_color.copy()
                    best_chain = visited
                    
        if check_result(test_color,self.graph) == 0:
            return test_color,visited
        return None