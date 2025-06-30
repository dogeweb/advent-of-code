from functools import cache

def dfs(op, towels, max_length):
    fun = cache(lambda s: 1 if s == "" else op(fun(s[i:]) for i in range(1, min(max_length, len(s)) + 1) if s[:i] in towels))
    return fun

def parse_input(input):
    input = list(map(str.strip, input))
    towels = set(input[0].split(", "))
    return input, towels, max(map(len, towels))

def solve(input, op):
    input, towels, max_length = parse_input(input)
    return sum(map(dfs(op, towels, max_length), input[2:]))

def part1(input):
    return solve(input, any)

def part2(input):
    return solve(input, sum)

test_input = open("Day19_test.txt", "r").readlines()
input = open("Day19.txt", "r").readlines()

p1 = part1(test_input)
p2 = part2(test_input)
assert p1 == 6
print(p1)
assert p2 == 16
print(p2)

print(part1(input))
print(part2(input))