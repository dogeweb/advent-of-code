from functools import cache


def part1(input):
    input = [line.strip() for line in input]
    towels = set(input[0].split(", "))
    max_length = max(map(len, towels))

    @cache
    def dfs(s):
        if s == "": return True
        return any(dfs(s[i:]) for i in range(1, min(max_length, len(s)) + 1) if s[:i] in towels)

    return list(map(dfs, input[2:])).count(True)

def part2(input):
    input = list(map(str.strip, input))
    towels = set(input[0].split(", "))
    max_length = max(map(len, towels))

    @cache
    def dfs(s):
        if s == "": return True
        return sum(dfs(s[i:]) for i in range(1, min(max_length, len(s)) + 1) if s[:i] in towels)

    return sum((map(dfs, input[2:])))

testInput = open("Day19_test.txt", "r").readlines()
input = open("Day19.txt", "r").readlines()

print(part1(testInput))
print(part2(testInput))

print(part1(input))
print(part2(input))