from collections import Counter

file = open("Day01_test.txt", "r").readlines()
# file = open("Day01.txt", "r").readlines()

part1 = sum((abs(a[0] - a[1]) for a in (list(zip(*(sorted(list(i)) for i in (list(zip(*(list(map(int, line.split())) for line in file))))))))))


print(part1)
# print(part2)
