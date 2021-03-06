from collections import deque, defaultdict
import random
import numpy as np
import csv
# from numba import vectorize,jit, cuda

class Dsatur:

    def __init__(self,graph):

        self.graph = graph
        self.mapSize = len(graph)
        self.degreesArray = []
#         print(self.mapSize)

    # def getMapSize(self):
    #     return len(self.graph)

    def calculateVerticesDegree(self):

        degree = []
        for keys,value in self.graph.items():
            degree.append(len(value))
        return degree
#     @vectorize(['float32(f'], target='cuda')
    def getHighestDegrees(self):
        degree = self.calculateVerticesDegree()

        highestDegVertexIndex = 0
        for i in range(len(degree)):
            if degree[i] > degree[highestDegVertexIndex]:
                highestDegVertexIndex = i
        return highestDegVertexIndex

    def getHighestSaturation(self,coloring,degrees):
        maxSaturation =0
        vertexWithMaxSaturation = -1
        # length = len(self.graph)
        # print(length)

        for i in range(0,self.mapSize):
            if coloring[i] == -1 :
                colors  = []

                for j in range(0,self.mapSize):
                    # print(j)
                    if self.areAdjacent(i,j) and coloring[j] != -1:
                        colors.append(coloring[j])

                tempSaturation = len(colors)
                if tempSaturation > maxSaturation:
                    maxSaturation = tempSaturation
                    vertexWithMaxSaturation = i
                elif tempSaturation == maxSaturation and degrees[i] >= degrees[vertexWithMaxSaturation]:
                    vertexWithMaxSaturation = i
        return vertexWithMaxSaturation

    def areAdjacent(self,val1,val2):
        graph = self.graph
        # print(val1,val2)
        # print(graph[val1])
        if len(graph[val1])==0:
            return False
        if val2 in graph[val1]:
            return True
        return False

#     @jit(target ="cuda")
    def dsatur(self):

        used_color = 0
        degrees = self.calculateVerticesDegree()
        resultingColor = {}
        coloredVertices = []
        singleNode = []

        coloring = []
        for i in range(0,self.mapSize):
            coloring.append(-1)

        notColored = []

        for i in range(0,self.mapSize):
            notColored.append(i)

        highestDegreeVertex = self.getHighestDegrees()

        coloring[highestDegreeVertex] = 0

        coloredVertices.append(highestDegreeVertex)

        resultingColor[highestDegreeVertex] = 0

        notColored.remove(highestDegreeVertex)


        while len(notColored) > 0:
#             print(len(notColored))
            lastColor = 0
            availableColors = []
#             print(len(notColored))

            vertices = self.getHighestSaturation(coloring,degrees)

            # print(vertices)
            while vertices in resultingColor.keys():
                vertices = self.getHighestSaturation(coloring,degrees)
            # print(vertices)

            if vertices == -1:
                # for j in range(0, len(availableColors)):
                #     if availableColors[j]:
                #         lastColor = j
                #         break
                # print("avaiable colors",lastColor)
#                 print(notColored)
                singleNode = notColored.copy()
                # print(singleNode)
                for val in singleNode:
                    # print(len(notColored))
#                     print(val," Value Color ",degrees[val])
                    resultingColor[val] = -1
                    notColored.remove(val)
                    coloredVertices.append(val)
                    coloring[vertices] = -1
                used_color += 1
                continue

            for val in range(0,self.mapSize):
                availableColors.append(True)



            for val in coloredVertices:
                currentVertex = val
                # checkAdjacent,checkNone = self.areAdjacent(vertices,currentVertex)

                if self.areAdjacent(vertices,currentVertex):
                    color = resultingColor[currentVertex]
                    availableColors[color] = False

            for j in range(0,len(availableColors)):
                if availableColors[j]:
                    lastColor = j
                    break

            resultingColor[vertices]=lastColor
            notColored.remove(vertices)
            coloredVertices.append(vertices)
            coloring[vertices] = lastColor

        maxColor = 0
        for key,value in resultingColor.items():
            if maxColor < value:
                maxColor = value
                
        return resultingColor,maxColor + used_color
        