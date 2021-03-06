from collections import deque, defaultdict
import random
import numpy as np
import csv

def reading_file():

    file = open("T:\WorkSpace\Level_3_Term_2\AI\Offline 2\yor-f-83.stu", "r")

    Lines = file.readlines()

    studentList = defaultdict(list)

    enrolledSubjectList = {}

    count = 0

    for line in Lines:

        subjectList = line.split()

        key,values = count,subjectList[:len(subjectList)]

        enrolledSubjectList[key] = [int(val)-1 for val in values]

        for val in values:
            studentList[int(val)-1].append(count)

        count += 1

#     print("ENROLLED SUBJECTS")
#     print(len(studentList))
#     print("TOTAL NUMBER OF STUDENTS")
#     print(len(enrolledSubjectList))

    conflict = defaultdict(list)

    for index, studentIDList in studentList.items():
        #     print(studentList[index],index)
        for student in studentIDList:
            for i in enrolledSubjectList[student].copy():
                conflict[index].append(i)
            conflict[index] = list(set(conflict[index].copy()))
        conflict[index].remove(index)


    return conflict,studentList,enrolledSubjectList
